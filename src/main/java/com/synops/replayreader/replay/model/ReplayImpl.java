package com.synops.replayreader.replay.model;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.player.model.PlayerIdMapping;
import com.synops.replayreader.player.model.PlayerVehicleMapping;
import com.synops.replayreader.vehicle.model.VehicleStats;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.tuple.Pair;
import org.jspecify.annotations.NonNull;

public class ReplayImpl implements Replay {

  private String playerVehicle;
  private String clientVersionFromExe;
  private String dateTime;
  private boolean hasMods;
  private String mapName;
  private String regionCode;
  private String battleType;
  private String arenaUniqueID;
  private String arenaCreateTime;
  private String duration;
  private String arenaTypeID;
  private String winnerTeam;
  private List<PlayerVehicleMapping> playerVehicleMappings;
  private List<Player> players;
  private List<VehicleStats> vehicleStats;
  private final LoadingCache<Pair<String, String>, VehicleStats> vehicleStatsLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public VehicleStats load(@NonNull Pair<String, String> key) {
          String avatarSessionID = key.getRight();
          return ReplayImpl.this.getVehicleStats().stream()
              .filter((v) -> avatarSessionID.equals(v.getAvatarSessionID())).findFirst()
              .orElseThrow();
        }
      });
  private List<PlayerIdMapping> playerIdMapping;
  private final LoadingCache<String, PlayerIdMapping> pimLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public PlayerIdMapping load(@NonNull String key) {
          return ReplayImpl.this.getPlayerIdMapping().parallelStream().filter(
                  (pim) -> key.equals(getPlayerNameFromAvatarSessionId(pim.getAvatarSessionId())))
              .findFirst().orElseThrow();
        }
      });
  private final LoadingCache<String, PlayerVehicleMapping> pvmLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public PlayerVehicleMapping load(@NonNull String key) {
          return ReplayImpl.this.getPlayerVehicleMappings().parallelStream().filter(
                  (pvm) -> key.equals(getPlayerNameFromAvatarSessionId(pvm.getAvatarSessionId())))
              .findFirst().orElseThrow();
        }
      });
  private final LoadingCache<String, Boolean> playerLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public Boolean load(@NonNull String key) {
          return ReplayImpl.this.getPlayerIdMapping().parallelStream().anyMatch(
              (pim) -> key.equals(getPlayerNameFromAvatarSessionId(pim.getAvatarSessionId())));
        }
      });

  public String getRegionCode() {
    return this.regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getPlayerVehicle() {
    return this.playerVehicle;
  }

  public void setPlayerVehicle(String playerVehicle) {
    this.playerVehicle = playerVehicle;
  }

  public String getClientVersionFromExe() {
    return this.clientVersionFromExe;
  }

  public void setClientVersionFromExe(String clientVersionFromExe) {
    this.clientVersionFromExe = clientVersionFromExe;
  }

  public List<PlayerIdMapping> getPlayerIdMapping() {
    return this.playerIdMapping;
  }

  public void setPlayerIdMapping(List<PlayerIdMapping> playerIdMapping) {
    this.playerIdMapping = playerIdMapping;
  }

  public List<VehicleStats> getVehicleStats() {
    return this.vehicleStats;
  }

  public void setVehicleStats(List<VehicleStats> vehicleStats) {
    this.vehicleStats = vehicleStats;
  }

  public List<PlayerVehicleMapping> getPlayerVehicleMappings() {
    return playerVehicleMappings;
  }

  public void setPlayerVehicleMappings(List<PlayerVehicleMapping> playerVehicleMappings) {
    this.playerVehicleMappings = playerVehicleMappings;
  }

  public List<Player> getPlayers() {
    return this.players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public String getMapName() {
    return this.mapName;
  }

  public void setMapName(String mapName) {
    this.mapName = mapName;
  }

  public String getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public boolean getHasMods() {
    return this.hasMods;
  }

  public void setHasMods(boolean hasMods) {
    this.hasMods = hasMods;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public String getArenaUniqueID() {
    return this.arenaUniqueID;
  }

  public void setArenaUniqueID(String arenaUniqueID) {
    this.arenaUniqueID = arenaUniqueID;
  }

  public String getArenaCreateTime() {
    return this.arenaCreateTime;
  }

  public void setArenaCreateTime(String arenaCreateTime) {
    this.arenaCreateTime = arenaCreateTime;
  }

  public String getBattleType() {
    return this.battleType;
  }

  public void setBattleType(String battleType) {
    this.battleType = battleType;
  }

  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getArenaTypeID() {
    return this.arenaTypeID;
  }

  public void setArenaTypeID(String arenaTypeID) {
    this.arenaTypeID = arenaTypeID;
  }

  public String getWinnerTeam() {
    return this.winnerTeam;
  }

  public void setWinnerTeam(String winnerTeam) {
    this.winnerTeam = winnerTeam;
  }

  public Player getPlayerByName(String playerName) {
    var playerId = getPlayerIdMappingByPlayerName(playerName).getPlayerId();
    return getPlayers().stream().filter((p) -> playerId.equals(p.getPlayerId())).findFirst()
        .orElseThrow();
  }

  public String getPlayerNameFromAvatarSessionId(String avatarSessionId) {
    var playerId = getPlayerIdMapping().stream()
        .filter(pim -> avatarSessionId.equals(pim.getAvatarSessionId())).findFirst().orElseThrow()
        .getPlayerId();
    return getPlayers().stream().filter(p -> p.getPlayerId().equals(playerId)).findFirst()
        .orElseThrow().getName();
  }

  public PlayerIdMapping getPlayerIdMappingByPlayerName(String playerName) {
    try {
      return pimLoadingCache.get(playerName);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public PlayerVehicleMapping getPlayerVehicleMappingByPlayerName(String playerName) {
    try {
      return pvmLoadingCache.get(playerName);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public VehicleStats getPlayerStatsByPlayerName(String playerName) {
    String avatarSessionId = getPlayerIdMappingByPlayerName(playerName).getAvatarSessionId();

    try {
      return vehicleStatsLoadingCache.get(Pair.of(playerName, avatarSessionId));
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public boolean existPlayer(String playerName) {
    try {
      return playerLoadingCache.get(playerName);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }
}
