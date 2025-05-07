package com.synops.replayreader.replay.model;

import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.player.model.PlayerIdMapping;
import com.synops.replayreader.player.model.PlayerVehicleMapping;
import com.synops.replayreader.vehicle.model.VehicleStats;
import java.util.List;

public interface Replay {

  String getPlayerVehicle();

  List<PlayerIdMapping> getPlayerIdMapping();

  List<VehicleStats> getVehicleStats();

  List<PlayerVehicleMapping> getPlayerVehicleMappings();

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

  String getPlayerNameFromAvatarSessionId(String avatarSessionId);

  Player getPlayerByName(String playerName);

  default PlayerIdMapping getPlayerIdMappingByPlayerName(String playerName) {
    return getPlayerIdMapping().parallelStream().filter(
            (pim) -> playerName.equals(getPlayerNameFromAvatarSessionId(pim.getAvatarSessionId())))
        .findFirst().orElseThrow();
  }

  default PlayerVehicleMapping getPlayerVehicleMappingByPlayerName(String playerName) {
    return getPlayerVehicleMappings().parallelStream().filter(
            (pvm) -> playerName.equals(getPlayerNameFromAvatarSessionId(pvm.getAvatarSessionId())))
        .findFirst().orElseThrow();
  }

  default VehicleStats getPlayerStatsByPlayerName(String playerName) {
    String avatarSessionId = getPlayerIdMappingByPlayerName(playerName).getAvatarSessionId();
    return getVehicleStats().parallelStream()
        .filter((v) -> avatarSessionId.equals(v.getAvatarSessionID())).findFirst().orElseThrow();
  }

  default boolean existPlayer(String playerName) {
    return getPlayerIdMapping().parallelStream().anyMatch(
        (pim) -> playerName.equals(getPlayerNameFromAvatarSessionId(pim.getAvatarSessionId())));
  }
}
