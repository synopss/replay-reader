package com.synops.replayreader.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ReplayFilter {

  private BattleType battleType;

  private ReplayFilter() {
    this.battleType = BattleType.ANY;
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

  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }
}
