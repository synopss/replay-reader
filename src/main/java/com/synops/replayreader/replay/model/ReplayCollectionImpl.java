package com.synops.replayreader.replay.model;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.player.model.PlayerIdMapping;
import com.synops.replayreader.replay.util.ReplayCollectionUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.jspecify.annotations.NonNull;

public class ReplayCollectionImpl implements ReplayCollection {

  private final List<Replay> replays;
  private final LoadingCache<Triple<String, String, ReplayFilter>, List<Replay>> filteredReplaysLoadingCache;
  private ReplayFilter replayFilter;

  public ReplayCollectionImpl(List<Replay> replays) {
    this(replays, ReplayFilter.createDefault());
  }

  public ReplayCollectionImpl(List<Replay> replays, ReplayFilter replayFilter) {
    this.filteredReplaysLoadingCache = CacheBuilder.newBuilder().build(new CacheLoader<>() {
      @Override
      @NonNull
      public List<Replay> load(@NonNull Triple<String, String, ReplayFilter> key) {
        String player = key.getLeft();
        String vehicle = key.getMiddle();
        ReplayFilter filter = key.getRight();
        return ReplayCollectionImpl.this.replays.parallelStream().filter(
                (r) -> BattleType.ANY.equals(filter.getBattleType()) || filter.getBattleType().getId()
                    .equals(r.getBattleType())).filter((r) -> r.existPlayer(player)).filter(
                (r) -> StringUtils.isEmpty(vehicle) || vehicle.equals(
                    r.getPlayerIdMappingByPlayerName(player).getVehicleType()))
            .collect(Collectors.toList());
      }
    });
    this.replayFilter = replayFilter;
    this.replays = this.filterUnique(replays);
  }

  private List<Replay> filterUnique(List<Replay> replays) {
    return replays.parallelStream()
        .filter(ReplayCollectionUtil.distinctByKey(Replay::getArenaUniqueID))
        .collect(Collectors.toList());
  }

  private List<Replay> filteredReplays(String player, String vehicle) {
    Triple<String, String, ReplayFilter> pvf = Triple.of(player, vehicle, replayFilter);

    try {
      return filteredReplaysLoadingCache.get(pvf);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public Set<String> getUniquePlayers() {
    return replays.parallelStream().flatMap((r) -> r.getPlayers().stream()).map(Player::getName)
        .collect(Collectors.toSet());
  }

  public Set<String> getUniqueClans() {
    Set<String> result = Collections.synchronizedSet(new HashSet<>());
    result.addAll(replays.parallelStream().map(Replay::getPlayers).flatMap(Collection::stream)
        .map(Player::getClanAbbrev).collect(Collectors.toSet()));
    return result;
  }

  public int getNumberOfClanPlayers(String clan) {
    return getUniquePlayersClan(clan).size();
  }

  public List<String> getUniquePlayersClan(String clanAbbrev) {
    Set<String> playerNames = replays.parallelStream().map(Replay::getPlayers)
        .flatMap(Collection::stream).filter(p -> clanAbbrev.equals(p.getClanAbbrev()))
        .map(Player::getName).collect(Collectors.toSet());

    return new ArrayList<>(Collections.synchronizedSet(playerNames));
  }

  public int getNumberOfGames(String player, String vehicle) {
    return filteredReplays(player, vehicle).size();
  }

  public List<String> getVehiclesForPlayer(String player) {
    var vehicleGroups = replays.parallelStream().filter(r -> r.existPlayer(player))
        .map(Replay::getPlayerIdMapping).flatMap(Collection::stream)
        .filter(pim -> player.equals(pim.getPlayerName()))
        .collect(Collectors.groupingByConcurrent(PlayerIdMapping::getVehicleType));

    var vehicles = vehicleGroups.keySet();
    var result = new ArrayList<>(vehicles);
    result.sort(
        (v1, v2) -> Long.compare(getNumberOfGames(player, v2), getNumberOfGames(player, v1)));

    return result;
  }

  public Player getPlayerInfo(String player) {
    return replays.parallelStream().map(Replay::getPlayers).flatMap(Collection::stream)
        .filter((p) -> player.equals(p.getName())).findFirst().orElseThrow(
            () -> new IllegalArgumentException(String.format("player <%s> not found", player)));
  }

  public List<String> getMaps(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().map(Replay::getMapName).distinct()
        .collect(Collectors.toList());
  }

  public int getNumberOfMapsPlayed(String player, String vehicle, String map) {
    return (int) filteredReplays(player, vehicle).parallelStream()
        .filter((r) -> map.equals(r.getMapName())).count();
  }

  public double getWinRate(String player, String vehicle) {
    List<Replay> filteredReplays = filteredReplays(player, vehicle);
    long wins = filteredReplays.parallelStream().filter(
            (r) -> Integer.valueOf(r.getWinnerTeam()).equals(r.getPlayerByName(player).getTeam()))
        .count();
    return wins == 0 ? (double) 0 : (double) wins / (double) filteredReplays.size();
  }

  public int getAvgDamageDealt(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getDamageDealt()))
        .intValue();
  }

  public double getAvgKills(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getKills()));
  }

  public double getPenRate(String player, String vehicle) {
    long pens = filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.summarizingInt((r) -> r.getPlayerStatsByPlayerName(player).getPiercings()))
        .getSum();
    long hits = filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.summarizingInt((r) -> r.getPlayerStatsByPlayerName(player).getDirectHits()))
        .getSum();
    return (double) pens / (double) hits;
  }

  public double getHitRate(String player, String vehicle) {
    long shots = filteredReplays(player, vehicle).parallelStream()
        .collect(Collectors.summarizingInt((r) -> r.getPlayerStatsByPlayerName(player).getShots()))
        .getSum();
    long hits = filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.summarizingInt((r) -> r.getPlayerStatsByPlayerName(player).getDirectHits()))
        .getSum();
    return (double) hits / (double) shots;
  }

  public int getAvgSpotAssist(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getDamageAssistedRadio())).intValue();
  }

  public int getAvgTrackAssist(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getDamageAssistedTrack())).intValue();
  }

  public double getAvgSpots(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getSpotted()));
  }

  public int getAvgBlocked(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getDamageBlockedByArmor())).intValue();
  }

  public int getAvgLifeTime(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getLifeTime())).intValue();
  }

  public int getAvgHitsReceived(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getDirectHitsReceived())).intValue();
  }

  public int getAvgDamageReceived(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getDamageReceived())).intValue();
  }

  public int getAvgDamageReceivedFromInvisibles(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
            (r) -> (double) r.getPlayerStatsByPlayerName(player).getDamageReceivedFromInvisibles()))
        .intValue();
  }

  public double getAvgShots(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getShots()));
  }

  public int getAvgTDamageDealt(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getTdamageDealt()))
        .intValue();
  }

  public double getavgTKills(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingDouble(
        (r) -> (double) r.getPlayerStatsByPlayerName(player).getTKills()));
  }

  public int getAvgCredits(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream()
        .collect(Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getCredits()))
        .intValue();
  }

  public int getAvgXp(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream()
        .collect(Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getXp()))
        .intValue();
  }

  public int getAvgMileage(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream()
        .collect(Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getMileage()))
        .intValue();
  }

  public int getAvgSniperDamageDealt(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getSniperDamageDealt()))
        .intValue();
  }

  public int getAvgCap(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(
            Collectors.averagingInt((r) -> r.getPlayerStatsByPlayerName(player).getCapturePoints()))
        .intValue();
  }

  public int getAvgCapReset(String player, String vehicle) {
    return filteredReplays(player, vehicle).parallelStream().collect(Collectors.averagingInt(
        (r) -> r.getPlayerStatsByPlayerName(player).getDroppedCapturePoints())).intValue();
  }

  public void updateReplayFilter(ReplayFilter replayFilter) {
    this.replayFilter = Objects.requireNonNullElseGet(replayFilter, ReplayFilter::createDefault);
  }

  private void debugGetAllVehicles() {
    Set<String> vehicles = getUniquePlayers().stream().map(this::getVehiclesForPlayer)
        .flatMap(Collection::stream).collect(Collectors.toSet());
    String all = String.join("\n", vehicles);
    System.out.println(all);
  }

  private double getAvgTeamRank(String player, String vehicle, Comparator<Player> comparator) {
    int sumRank = 0;
    List<Replay> filteredReplays = filteredReplays(player, vehicle);

    for (Replay r : filteredReplays) {
      int team = r.getPlayerByName(player).getTeam();
      List<Player> teamPlayers = r.getPlayers().stream().filter((px) -> team == px.getTeam())
          .sorted(comparator).toList();

      for (Player p : teamPlayers) {
        if (player.equals(r.getPlayerByName(p.getName()).getName())) {
          sumRank += teamPlayers.indexOf(p) + 1;
          break;
        }
      }
    }

    return (double) sumRank / (double) filteredReplays.size();
  }

  public double getAvgXPRank(String player, String vehicle) {
    int sumRank = 0;
    List<Replay> filteredReplays = filteredReplays(player, vehicle);

    for (Replay r : filteredReplays) {
      int team = r.getPlayerByName(player).getTeam();
      List<Player> teamPlayers = r.getPlayers().stream().filter((px) -> team == px.getTeam())
          .sorted((p1, p2) -> {
            int p1xp = r.getPlayerStatsByPlayerName(p1.getName()).getXp();
            int p2xp = r.getPlayerStatsByPlayerName(p2.getName()).getXp();
            return Integer.compare(p2xp, p1xp);
          }).toList();

      for (Player p : teamPlayers) {
        if (player.equals(r.getPlayerByName(p.getName()).getName())) {
          sumRank += teamPlayers.indexOf(p) + 1;
          break;
        }
      }
    }

    return (double) sumRank / (double) filteredReplays.size();
  }

  public double getAvgDamageRank(String player, String vehicle) {
    int sumRank = 0;
    List<Replay> filteredReplays = filteredReplays(player, vehicle);

    for (Replay r : filteredReplays) {
      int team = r.getPlayerByName(player).getTeam();
      List<Player> teamPlayers = r.getPlayers().stream().filter((px) -> team == px.getTeam())
          .sorted((p1, p2) -> {
            int p1xp = r.getPlayerStatsByPlayerName(p1.getName()).getDamageDealt();
            int p2xp = r.getPlayerStatsByPlayerName(p2.getName()).getDamageDealt();
            return Integer.compare(p2xp, p1xp);
          }).toList();

      for (Player p : teamPlayers) {
        if (player.equals(r.getPlayerByName(p.getName()).getName())) {
          sumRank += teamPlayers.indexOf(p) + 1;
          break;
        }
      }
    }

    return (double) sumRank / (double) filteredReplays.size();
  }
}
