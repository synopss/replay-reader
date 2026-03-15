package com.synops.replayreader.replay.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ReplayFilter {

  private BattleType battleType;
  private String map;

  private ReplayFilter() {
    this.battleType = BattleType.ANY;
    this.map = null;
  }

  public static ReplayFilter createDefault() {
    return new ReplayFilter();
  }

  public BattleType getBattleType() {
    return this.battleType;
  }

  public ReplayFilter setBattleType(BattleType battleType) {
    this.battleType = battleType;
    return this;
  }

  public String getMap() {
    return this.map;
  }

  public ReplayFilter setMap(String map) {
    this.map = map;
    return this;
  }

  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }
}
