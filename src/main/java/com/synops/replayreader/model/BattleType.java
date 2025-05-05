package com.synops.replayreader.model;

public enum BattleType {
  SKIRMISH("20"), ANY("");

  private final String id;

  BattleType(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }
}
