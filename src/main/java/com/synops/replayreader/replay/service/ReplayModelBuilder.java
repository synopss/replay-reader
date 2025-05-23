package com.synops.replayreader.replay.service;

import static com.synops.replayreader.common.util.Constants.JSON_DEPTH;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.player.model.PlayerIdMapping;
import com.synops.replayreader.player.model.PlayerIdMappingImpl;
import com.synops.replayreader.player.model.PlayerImpl;
import com.synops.replayreader.player.model.PlayerVehicleMapping;
import com.synops.replayreader.player.model.PlayerVehicleMappingImpl;
import com.synops.replayreader.replay.dto.ExtraDataPOJO;
import com.synops.replayreader.replay.dto.ReplayPOJO;
import com.synops.replayreader.replay.dto.StatsCommonPOJO;
import com.synops.replayreader.replay.dto.StatsPlayerPOJO;
import com.synops.replayreader.replay.dto.StatsVehiclesPOJO;
import com.synops.replayreader.replay.model.Replay;
import com.synops.replayreader.replay.model.ReplayImpl;
import com.synops.replayreader.vehicle.model.VehicleStats;
import com.synops.replayreader.vehicle.model.VehicleStatsImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReplayModelBuilder {

  private Gson gson;
  private JsonObject replayJson;
  private JsonObject statsJson;
  private JsonObject extraDataJson;

  public ReplayModelBuilder() {
  }

  public void configure(List<String> jsonString) {
    if (jsonString.size() != JSON_DEPTH) {
      throw new IllegalArgumentException("Illegal amount of json string");
    } else {
      this.gson = (new GsonBuilder()).create();
      this.replayJson = gson.fromJson(jsonString.getFirst(), JsonObject.class);
      this.statsJson = gson.fromJson(jsonString.get(1), JsonObject.class);
      this.extraDataJson = gson.fromJson(jsonString.get(2), JsonObject.class);
    }
  }

  public Replay create() {
    var players = createPlayers();
    var playerIdMapping = createPlayerIdMapping();
    var vehicleStats = createVehicleStats();
    var playerVehicleMapping = createPlayerVehicleMapping();
    var replayPOJO = gson.fromJson(replayJson, ReplayPOJO.class);
    var replay = new ReplayImpl();
    replay.setRegionCode(replayPOJO.regionCode);
    replay.setPlayerVehicle(replayPOJO.playerVehicle);
    replay.setClientVersionFromExe(replayPOJO.clientVersionFromExe);
    replay.setDateTime(replayPOJO.dateTime);
    replay.setHasMods(replayPOJO.hasMods);
    replay.setMapName(replayPOJO.mapName);
    replay.setPlayers(players);
    replay.setPlayerVehicleMappings(playerVehicleMapping);
    replay.setPlayerIdMapping(playerIdMapping);
    replay.setVehicleStats(vehicleStats);
    replay.setBattleType(replayPOJO.battleType);
    replay.setArenaUniqueID(statsJson.get("arenaUniqueID").getAsString());
    var statsCommonPOJO = gson.fromJson(statsJson.getAsJsonObject("common"), StatsCommonPOJO.class);
    replay.setArenaCreateTime(statsCommonPOJO.arenaCreateTime);
    replay.setArenaTypeID(statsCommonPOJO.arenaTypeID);
    replay.setWinnerTeam(statsCommonPOJO.winnerTeam);
    replay.setDuration(statsCommonPOJO.duration);
    return replay;
  }

  private List<Player> createPlayers() {
    var result = new ArrayList<Player>();

    for (var playerEntry : statsJson.getAsJsonObject("players").entrySet()) {
      var playerPOJO = gson.fromJson(playerEntry.getValue(), StatsPlayerPOJO.class);
      var player = new PlayerImpl();
      player.setPlayerId(playerEntry.getKey());
      player.setName(!playerPOJO.realName.isEmpty() ? playerPOJO.realName : playerPOJO.name);
      player.setTeam(playerPOJO.team);
      player.setClanAbbrev(playerPOJO.clanAbbrev);
      player.setClanDBID(playerPOJO.clanDBID);
      result.add(player);
    }

    return result;
  }

  private List<PlayerIdMapping> createPlayerIdMapping() {
    var result = new ArrayList<PlayerIdMapping>();

    for (var statsEntry : statsJson.getAsJsonObject("vehicles").entrySet()) {
      var vehicleArray = gson.fromJson(statsEntry.getValue(), JsonArray.class);
      if (vehicleArray.size() != 1) {
        throw new RuntimeException("Vehicle stats array size should be 1");
      }
      var vehicleStatsPOJO = gson.fromJson(vehicleArray.get(0), StatsVehiclesPOJO.class);
      var playerIdMapping = new PlayerIdMappingImpl();
      playerIdMapping.setPlayerId(vehicleStatsPOJO.accountDBID);
      playerIdMapping.setAvatarSessionId(statsEntry.getKey());
      result.add(playerIdMapping);
    }

    return result;
  }

  private List<PlayerVehicleMapping> createPlayerVehicleMapping() {
    var result = new ArrayList<PlayerVehicleMapping>();

    for (var playerEntry : extraDataJson.entrySet()) {
      var extraDataPOJO = gson.fromJson(playerEntry.getValue(), ExtraDataPOJO.class);
      var playerVehicleMapping = new PlayerVehicleMappingImpl();
      playerVehicleMapping.setAvatarSessionId(playerEntry.getKey());
      playerVehicleMapping.setVehicleType(extraDataPOJO.vehicleType);
      result.add(playerVehicleMapping);
    }

    return result;
  }

  private List<VehicleStats> createVehicleStats() {
    var result = new ArrayList<VehicleStats>();

    for (var statsEntry : statsJson.getAsJsonObject("vehicles").entrySet()) {
      var vehicleArray = gson.fromJson(statsEntry.getValue(), JsonArray.class);
      if (vehicleArray.size() != 1) {
        throw new RuntimeException("Vehicle stats array size should be 1");
      }

      var vehiclePOJO = gson.fromJson(vehicleArray.get(0), StatsVehiclesPOJO.class);
      var vehicleStats = new VehicleStatsImpl();
      vehicleStats.setAvatarSessionID(statsEntry.getKey());
      vehicleStats.setCapturePoints(vehiclePOJO.capturePoints);
      vehicleStats.setCredits(vehiclePOJO.credits);
      vehicleStats.setDamageAssistedRadio(vehiclePOJO.damageAssistedRadio);
      vehicleStats.setDamageBlockedByArmor(vehiclePOJO.damageBlockedByArmor);
      vehicleStats.setDamageAssistedTrack(vehiclePOJO.damageAssistedTrack);
      vehicleStats.setDamaged(vehiclePOJO.damaged);
      vehicleStats.setDamageDealt(vehiclePOJO.damageDealt);
      vehicleStats.setDamageReceived(vehiclePOJO.damageReceived);
      vehicleStats.setDamageReceivedFromInvisibles(vehiclePOJO.damageReceivedFromInvisibles);
      vehicleStats.setDirectHitsReceived(vehiclePOJO.directHitsReceived);
      vehicleStats.setHealth(vehiclePOJO.health);
      vehicleStats.setKillerID(vehiclePOJO.killerID);
      vehicleStats.setKills(vehiclePOJO.kills);
      vehicleStats.setLifeTime(vehiclePOJO.lifeTime);
      vehicleStats.setMileage(vehiclePOJO.mileage);
      vehicleStats.setPiercings(vehiclePOJO.piercings);
      vehicleStats.setShots(vehiclePOJO.shots);
      vehicleStats.setSniperDamageDealt(vehiclePOJO.sniperDamageDealt);
      vehicleStats.setSpotted(vehiclePOJO.spotted);
      vehicleStats.setTdamageDealt(vehiclePOJO.tdamageDealt);
      vehicleStats.setTeam(vehiclePOJO.team);
      vehicleStats.setXp(vehiclePOJO.xp);
      vehicleStats.setDirectHits(vehiclePOJO.directHits);
      vehicleStats.setDroppedCapturePoints(vehiclePOJO.droppedCapturePoints);
      vehicleStats.setFlagCapture(vehiclePOJO.flagCapture);
      vehicleStats.setStunDuration(vehiclePOJO.stunDuration);
      vehicleStats.setDeathCount(vehiclePOJO.deathCount);
      vehicleStats.setStunned(vehiclePOJO.stunned);
      vehicleStats.setDamageAssistedStun(vehiclePOJO.damageAssistedStun);
      vehicleStats.setTKills(vehiclePOJO.tkills);
      vehicleStats.setStunNum(vehiclePOJO.stunNum);
      result.add(vehicleStats);
    }

    return result;
  }
}
