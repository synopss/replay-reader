package com.synops.replayreader.replay.model;

import com.synops.replayreader.player.model.Player;
import java.util.List;
import java.util.Set;

public interface ReplayCollection {
  Set<String> getUniquePlayers();

  List<String> getUniquePlayersClan(String clanAbbrev);

  List<String> getVehiclesForPlayer(String player);

  Set<String> getUniqueClans();

  Player getPlayerInfo(String player);

  void updateReplayFilter(ReplayFilter replayFilter);

  int getNumberOfClanPlayers(String clan);

  int getNumberOfGames(String player, String vehicle);

  int getAvgDamageDealt(String player, String vehicle);

  List<String> getMaps(String player, String vehicle);

  int getNumberOfMapsPlayed(String player, String vehicle, String map);

  double getWinRate(String player, String vehicle);

  double getAvgKills(String player, String vehicle);

  double getPenRate(String player, String vehicle);

  double getHitRate(String player, String vehicle);

  int getAvgSpotAssist(String player, String vehicle);

  int getAvgTrackAssist(String player, String vehicle);

  double getAvgSpots(String player, String vehicle);

  int getAvgBlocked(String player, String vehicle);

  int getAvgLifeTime(String player, String vehicle);

  int getAvgHitsReceived(String player, String vehicle);

  int getAvgDamageReceived(String player, String vehicle);

  int getAvgDamageReceivedFromInvisibles(String player, String vehicle);

  double getAvgShots(String player, String vehicle);

  int getAvgTDamageDealt(String player, String vehicle);

  double getavgTKills(String player, String vehicle);

  int getAvgCredits(String player, String vehicle);

  int getAvgXp(String player, String vehicle);

  int getAvgMileage(String player, String vehicle);

  int getAvgSniperDamageDealt(String player, String vehicle);

  int getAvgCap(String player, String vehicle);

  int getAvgCapReset(String player, String vehicle);

  double getAvgXPRank(String player, String vehicle);

  double getAvgDamageRank(String player, String vehicle);
}
