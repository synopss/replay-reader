package com.synops.replayreader.model;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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
  private List<Player> players;
  private List<VehicleStats> vehicleStats;
  private final LoadingCache<Pair<String, String>, VehicleStats> vehicleStatsLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public VehicleStats load(@NonNull Pair<String, String> key) {
          String vehicleId = key.getRight();
          return ReplayImpl.this.getVehicleStats().stream()
              .filter((v) -> vehicleId.equals(v.getVehicleId())).findFirst().orElseThrow();
        }
      });
  private List<PlayerIdMapping> playerIdMapping;
  private final LoadingCache<String, PlayerIdMapping> pimLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public PlayerIdMapping load(@NonNull String key) {
          return ReplayImpl.this.getPlayerIdMapping().parallelStream()
              .filter((pim) -> key.equals(pim.getPlayerName())).findFirst().orElseThrow();
        }
      });
  private final LoadingCache<String, Boolean> playerLoadingCache = CacheBuilder.newBuilder()
      .build(new CacheLoader<>() {
        @Override
        @NonNull
        public Boolean load(@NonNull String key) {
          return ReplayImpl.this.getPlayerIdMapping().parallelStream()
              .anyMatch((pim) -> key.equals(pim.getPlayerName()));
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

  public PlayerIdMapping getPlayerIdMappingByPlayerName(String playerName) {
    try {
      return this.pimLoadingCache.get(playerName);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public VehicleStats getPlayerStatsByPlayerName(String playerName) {
    String vehicleId = this.getPlayerIdMappingByPlayerName(playerName).getVehicleId();

    try {
      return this.vehicleStatsLoadingCache.get(Pair.of(playerName, vehicleId));
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  public boolean existPlayer(String playerName) {
    try {
      return this.playerLoadingCache.get(playerName);
    } catch (ExecutionException e) {
      throw new RuntimeException(e.getCause());
    }
  }
}
