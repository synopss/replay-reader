package com.synops.replayreader.model;


import javafx.collections.ObservableList;

public class MainModel {

  private ObservableList<String> playersData;
  private ObservableList<String> clansData;

  public ObservableList<String> getPlayersData() {
    return this.playersData;
  }

  public void setPlayersData(ObservableList<String> playersData) {
    this.playersData = playersData;
  }

  public ObservableList<String> getClansData() {
    return this.clansData;
  }

  public void setClansData(ObservableList<String> clansData) {
    this.clansData = clansData;
  }
}
