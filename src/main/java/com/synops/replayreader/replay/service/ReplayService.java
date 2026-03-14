package com.synops.replayreader.replay.service;

import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.core.event.ReplayProgressEvent;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

public interface ReplayService {

  void load(List<File> files, Consumer<ReplayProgressEvent> progressListener);

  void setMapFilter(String map);

  ObservableList<String> getPlayers();

  ObservableList<String> getPlayersIgnoringMapFilter();

  Player getPlayerInfo(String player);

  ObservableList<String> getVehicles(String player);

  ObservableList<String> getClans();

  ObservableList<String> getMaps(String player, String vehicle);

  double getWinrate(String player, String vehicle);

  int getNumberOfGamesOnMap(String map);

  int getTotalGames();

  int getNumberOFClanPlayers(String clan);

  int getNumberOfGames(String player, String vehicle);

  int getDamageDealt(String player, String vehicle);

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

  int getAvgEquipmentDamageDealt(String player, String vehicle);

  int getAvgTdamageDealt(String player, String vehicle);

  double getAvgTKills(String player, String vehicle);

  double getAvgShots(String player, String vehicle);

  int getAvgXp(String player, String vehicle);

  int getAvgCredits(String player, String vehicle);

  int getAvgMileage(String player, String vehicle);

  int getAvgSniperDamageDealt(String player, String vehicle);

  int getAvgCap(String player, String vehicle);

  int getAvgCapReset(String player, String vehicle);

  double getAvgXPRank(String player, String vehicle);

  double getAvgDamageRank(String player, String vehicle);

  int getComp7PrestigePoints(String player, String vehicle);
}
