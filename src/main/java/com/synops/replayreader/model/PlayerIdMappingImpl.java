package com.synops.replayreader.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlayerIdMappingImpl implements PlayerIdMapping {

  private String vehicleId;
  private String vehicleType;
  private String playerId;
  private String playerName;

  public String getVehicleId() {
    return this.vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public String getVehicleType() {
    return this.vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getPlayerId() {
    return this.playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String getPlayerName() {
    return this.playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
