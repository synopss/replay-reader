package com.synops.replayreader.player.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlayerImpl implements Player {

  private String playerId;
  private String name;
  private int team;
  private String clanAbbrev;
  private String clanDBID;

  public String getPlayerId() {
    return this.playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTeam() {
    return this.team;
  }

  public void setTeam(int team) {
    this.team = team;
  }

  public String getClanAbbrev() {
    return this.clanAbbrev;
  }

  public void setClanAbbrev(String clanAbbrev) {
    this.clanAbbrev = clanAbbrev;
  }

  public String getClanDBID() {
    return this.clanDBID;
  }

  public void setClanDBID(String clanDBID) {
    this.clanDBID = clanDBID;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
