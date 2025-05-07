package com.synops.replayreader.player.model;

public class PlayerVehicleMappingImpl implements PlayerVehicleMapping {

  private String vehicleType;
  private String avatarSessionId;

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getAvatarSessionId() {
    return avatarSessionId;
  }

  public void setAvatarSessionId(String avatarSessionId) {
    this.avatarSessionId = avatarSessionId;
  }
}
