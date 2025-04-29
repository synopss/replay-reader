package com.synops.replayreader.service;

import com.synops.replayreader.model.Player;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

@Service
public class ReplayServiceImpl implements ReplayService {

  public ObservableList<String> getPlayers() {
    return null;
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
  public Double getAvgKills(String player, String vehicle) {
    return 0.0;
  }

  @Override
  public Double getPenRate(String player, String vehicle) {
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
}
