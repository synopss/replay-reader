package com.synops.replayreader.replay.service;

import com.synops.replayreader.replay.model.BattleType;
import com.synops.replayreader.player.model.Player;
import com.synops.replayreader.replay.model.ReplayCollection;
import com.synops.replayreader.core.event.ReplayProgressEvent;
import com.synops.replayreader.replay.controller.ReplayController;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

@Service
public class ReplayServiceImpl implements ReplayService {

  private final ReplayController replayController;
  private ReplayCollection replayCollection;

  public ReplayServiceImpl(ReplayController replayController) {
    this.replayController = replayController;
  }

  @Override
  public void load(List<File> files) {
    this.load(files, (eReplayProgressEvent) -> {
    });
  }

  @Override
  public void load(List<File> files, Consumer<ReplayProgressEvent> progressListener) {
    replayCollection = replayController.readReplays(files, BattleType.ANY, progressListener);
  }

  @Override
  public ObservableList<String> getPlayers() {
    return this.replayCollection == null ? FXCollections.emptyObservableList()
        : FXCollections.observableList(new ArrayList<>(this.replayCollection.getUniquePlayers()));
  }

  @Override
  public Player getPlayerInfo(String player) {
    return this.replayCollection.getPlayerInfo(player);
  }

  @Override
  public ObservableList<String> getVehicles(String player) {
    return FXCollections.observableList(this.replayCollection.getVehiclesForPlayer(player));
  }

  @Override
  public ObservableList<String> getClans() {
    var arrayList = new ArrayList<>(
        FXCollections.observableSet(this.replayCollection.getUniqueClans()));
    return FXCollections.observableList(arrayList);
  }

  @Override
  public ObservableList<String> getMaps(String player, String vehicle) {
    return FXCollections.observableList(this.replayCollection.getMaps(player, vehicle));
  }

  @Override
  public double getWinrate(String player, String vehicle) {
    return this.replayCollection.getWinRate(player, vehicle);
  }

  @Override
  public int getNumberOfMapsPlayed(String player, String vehicle, String map) {
    return this.replayCollection.getNumberOfMapsPlayed(player, vehicle, map);
  }

  @Override
  public int getNumberOFClanPlayers(String clan) {
    return this.replayCollection.getNumberOfClanPlayers(clan);
  }

  @Override
  public int getNumberOfGames(String player, String vehicle) {
    return this.replayCollection.getNumberOfGames(player, vehicle);
  }

  @Override
  public int getDamageDealt(String player, String vehicle) {
    return this.replayCollection.getAvgDamageDealt(player, vehicle);
  }

  @Override
  public double getAvgKills(String player, String vehicle) {
    return this.replayCollection.getAvgKills(player, vehicle);
  }

  @Override
  public double getPenRate(String player, String vehicle) {
    return this.replayCollection.getPenRate(player, vehicle);
  }

  @Override
  public double getHitRate(String player, String vehicle) {
    return this.replayCollection.getHitRate(player, vehicle);
  }

  @Override
  public int getAvgSpotAssist(String player, String vehicle) {
    return this.replayCollection.getAvgSpotAssist(player, vehicle);
  }

  @Override
  public int getAvgTrackAssist(String player, String vehicle) {
    return this.replayCollection.getAvgTrackAssist(player, vehicle);
  }

  @Override
  public double getAvgSpots(String player, String vehicle) {
    return this.replayCollection.getAvgSpots(player, vehicle);
  }

  @Override
  public int getAvgBlocked(String player, String vehicle) {
    return this.replayCollection.getAvgBlocked(player, vehicle);
  }

  @Override
  public int getAvgLifeTime(String player, String vehicle) {
    return this.replayCollection.getAvgLifeTime(player, vehicle);
  }

  @Override
  public int getAvgHitsReceived(String player, String vehicle) {
    return this.replayCollection.getAvgHitsReceived(player, vehicle);
  }

  @Override
  public int getAvgDamageReceived(String player, String vehicle) {
    return this.replayCollection.getAvgDamageReceived(player, vehicle);
  }

  @Override
  public int getAvgDamageReceivedFromInvisibles(String player, String vehicle) {
    return this.replayCollection.getAvgDamageReceivedFromInvisibles(player, vehicle);
  }

  @Override
  public int getAvgTdamageDealt(String player, String vehicle) {
    return this.replayCollection.getAvgTDamageDealt(player, vehicle);
  }

  @Override
  public double getAvgTKills(String player, String vehicle) {
    return this.replayCollection.getavgTKills(player, vehicle);
  }

  @Override
  public double getAvgShots(String player, String vehicle) {
    return this.replayCollection.getAvgShots(player, vehicle);
  }

  @Override
  public int getAvgXp(String player, String vehicle) {
    return this.replayCollection.getAvgXp(player, vehicle);
  }

  @Override
  public int getAvgCredits(String player, String vehicle) {
    return this.replayCollection.getAvgCredits(player, vehicle);
  }

  @Override
  public int getAvgMileage(String player, String vehicle) {
    return this.replayCollection.getAvgMileage(player, vehicle);
  }

  @Override
  public int getAvgSniperDamageDealt(String player, String vehicle) {
    return this.replayCollection.getAvgSniperDamageDealt(player, vehicle);
  }

  @Override
  public int getAvgCap(String player, String vehicle) {
    return this.replayCollection.getAvgCap(player, vehicle);
  }

  @Override
  public int getAvgCapReset(String player, String vehicle) {
    return this.replayCollection.getAvgCapReset(player, vehicle);
  }

  @Override
  public double getAvgXPRank(String player, String vehicle) {
    return this.replayCollection.getAvgXPRank(player, vehicle);
  }

  @Override
  public double getAvgDamageRank(String player, String vehicle) {
    return this.replayCollection.getAvgDamageRank(player, vehicle);
  }
}
