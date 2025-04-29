package com.synops.replayreader.service;

import com.synops.replayreader.model.Player;
import javafx.collections.ObservableList;

public interface ReplayService {

  ObservableList<String> getPlayers();

  int getNumberOfGames(String player, String vehicle);

  Player getPlayerInfo(String player);

  int getDamageDealt(String player, String vehicle);

  Double getAvgKills(String player, String vehicle);

  Double getPenRate(String player, String vehicle);

  int getAvgTdamageDealt(String player, String vehicle);

  int getAvgSpotAssist(String player, String vehicle);

  int getAvgTrackAssist(String player, String vehicle);

  int getAvgBlocked(String player, String vehicle);

  int getAvgXp(String player, String vehicle);
}
