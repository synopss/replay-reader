package com.synops.replayreader.service;

import com.synops.replayreader.model.Player;
import com.synops.replayreader.model.ReplayProgressEvent;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

@Service
public class ReplayServiceImpl implements ReplayService {

  @Override
  public void load(List<File> files, Consumer<ReplayProgressEvent> progress) {

  }

  public ObservableList<String> getPlayers() {
    return null;
  }

  @Override
  public ObservableList<String> getVehicles(String player) {
    return null;
  }

  @Override
  public ObservableList<String> getClans() {
    return null;
  }

  @Override
  public ObservableList<String> getMaps(String player, String vehicle) {
    return null;
  }

  @Override
  public int getNumberOfMapsPlayed(String player, String vehicle, String map) {
    return 0;
  }

  @Override
  public int getNumberOFClanPlayers(String clan) {
    return 0;
  }

  @Override
  public int getNumberOfGames(String player, String vehicle) {
    return 0;
  }

  @Override
  public Player getPlayerInfo(String player) {
    return null;
  }

  @Override
  public int getDamageDealt(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getAvgKills(String player, String vehicle) {
    return 0.0;
  }

  @Override
  public double getPenRate(String player, String vehicle) {
    return 0.0;
  }

  @Override
  public int getAvgTdamageDealt(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgSpotAssist(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgTrackAssist(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgBlocked(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgXp(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgSniperDamageDealt(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getHitRate(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getAvgSpots(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgLifeTime(String String, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgHitsReceived(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgDamageReceived(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgDamageReceivedFromInvisibles(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getAvgTKills(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getAvgShots(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgCredits(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgMileage(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getWinrate(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgCap(String player, String vehicle) {
    return 0;
  }

  @Override
  public int getAvgCapReset(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getAvgXPRank(String player, String vehicle) {
    return 0;
  }

  @Override
  public double getAvgDamageRank(String player, String vehicle) {
    return 0;
  }
}
