package com.synops.replayreader.model;

import com.synops.replayreader.common.i18n.I18nUtils;

public enum PlayerSort {
  GAMES("games", "textGames", "main.toolbar.sort.games"), DMG("dmg", "textDmg",
      "main.toolbar.sort.damage"), KILLS("kills", "textKills", "main.toolbar.sort.kills"), PENRATE(
      "penrate", "textPenRate", "main.toolbar.sort.pen-rate"), TEAMDMG("team dmg", "textTeamDmg",
      "main.toolbar.sort.team-damage"), ASSIST("assist", "textAssist",
      "main.toolbar.sort.assist"), BLOCKED("dmg blocked", "textBlocked",
      "main.toolbar.sort.damage-blocked"), XP("xp", "textXP", "main.toolbar.sort.damage-xp");

  private final String text;
  private final String elementId;
  private final String resourceBundleName;

  PlayerSort(String text, String elementId, String resourceBundleName) {
    this.text = text;
    this.elementId = elementId;
    this.resourceBundleName = resourceBundleName;
  }

  public static String of(String text) {
    for (PlayerSort ps : values()) {
      if (text.equals(ps.getResourceBundleName())) {
        return ps.getResourceBundleName();
      }
    }

    throw new IllegalArgumentException(String.format("sorting choice <%s> not found", text));
  }

  public String getText() {
    return text;
  }

  public String getElementId() {
    return this.elementId;
  }

  public String getResourceBundleName() {
    var bundle = I18nUtils.getBundle();
    return bundle.getString(resourceBundleName);
  }
}
