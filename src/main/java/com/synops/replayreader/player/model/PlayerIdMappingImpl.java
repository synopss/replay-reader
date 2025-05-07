package com.synops.replayreader.player.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlayerIdMappingImpl implements PlayerIdMapping {

  private String avatarSessionId;
  private String playerId;

  public String getAvatarSessionId() {
    return this.avatarSessionId;
  }

  public void setAvatarSessionId(String avatarSessionId) {
    this.avatarSessionId = avatarSessionId;
  }

  public String getPlayerId() {
    return this.playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
