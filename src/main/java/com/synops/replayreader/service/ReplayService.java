package com.synops.replayreader.service;

import com.synops.replayreader.model.Player;
import javafx.collections.ObservableList;

public interface ReplayService {

  ObservableList<String> getPlayers();

  Player getPlayerInfo(String player);

  ObservableList<String> getVehicles(String player);

  ObservableList<String> getMaps(String player, String vehicle);

  int getNumberOfMapsPlayed(String player, String vehicle, String map);

  int getNumberOfGames(String player, String vehicle);

  int getDamageDealt(String player, String vehicle);

  double getAvgKills(String player, String vehicle);

  double getPenRate(String player, String vehicle);

  int getAvgTdamageDealt(String player, String vehicle);

  int getAvgSpotAssist(String player, String vehicle);

  int getAvgTrackAssist(String player, String vehicle);

  int getAvgBlocked(String player, String vehicle);

  int getAvgXp(String player, String vehicle);

  int getAvgSniperDamageDealt(String player, String vehicle);

  double getHitRate(String player, String vehicle);

  double getAvgSpots(String player, String vehicle);

  int getAvgLifeTime(String String, String vehicle);

  int getAvgHitsReceived(String player, String vehicle);

  int getAvgDamageReceived(String player, String vehicle);

  int getAvgDamageReceivedFromInvisibles(String player, String vehicle);

  double getAvgTKills(String player, String vehicle);

  double getAvgShots(String player, String vehicle);

  int getAvgCredits(String player, String vehicle);

  int getAvgMileage(String player, String vehicle);

  double getWinrate(String player, String vehicle);

  int getAvgCap(String player, String vehicle);

  int getAvgCapReset(String player, String vehicle);

  double getAvgXPRank(String player, String vehicle);

  double getAvgDamageRank(String player, String vehicle);
}
