package com.synops.replayreader.replay.model;

import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.player.model.PlayerIdMapping;
import com.synops.replayreader.vehicle.model.VehicleStats;
import java.util.List;

public interface Replay {

  String getPlayerVehicle();

  List<PlayerIdMapping> getPlayerIdMapping();

  List<VehicleStats> getVehicleStats();

  List<Player> getPlayers();

  String getMapName();

  String getDateTime();

  boolean getHasMods();

  String getClientVersionFromExe();

  String getRegionCode();

  String getArenaTypeID();

  String getDuration();

  String getArenaCreateTime();

  String getArenaUniqueID();

  String getWinnerTeam();

  String getBattleType();

  default Player getPlayerByName(String playerName) {
    String playerId = this.getPlayerIdMappingByPlayerName(playerName).getPlayerId();
    return this.getPlayers().stream().filter((p) -> playerId.equals(p.getPlayerId()))
        .findFirst().get();
  }

  default PlayerIdMapping getPlayerIdMappingByPlayerName(String playerName) {
    return this.getPlayerIdMapping().parallelStream()
        .filter((pim) -> playerName.equals(pim.getPlayerName())).findFirst().get();
  }

  default VehicleStats getPlayerStatsByPlayerName(String playerName) {
    String vehicleId = this.getPlayerIdMappingByPlayerName(playerName).getVehicleId();
    return this.getVehicleStats().parallelStream()
        .filter((v) -> vehicleId.equals(v.getVehicleId())).findFirst().get();
  }

  default boolean existPlayer(String playerName) {
    return this.getPlayerIdMapping().parallelStream()
        .anyMatch((pim) -> playerName.equals(pim.getPlayerName()));
  }
}
