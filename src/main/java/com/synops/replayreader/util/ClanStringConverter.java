package com.synops.replayreader.util;

import java.util.function.Function;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Value;

public class ClanStringConverter extends StringConverter<String> {
  @Value("${replay-reader.config.clan-all}")
  private String CLAN_ALL;
  @Value("${replay-reader.config.clan-less}")
  private String CLAN_LESS;
  private final Function<String, Integer> function;
  private final int allPlayersCount;

  public ClanStringConverter(Function<String, Integer> function, int allPlayersCount) {
    this.function = function;
    this.allPlayersCount = allPlayersCount;
  }

  @Override
  public String toString(String clanString) {
    int playerCount = function.apply(clanString);
    String clan;
    if ("".equals(clanString)) {
      clan = CLAN_LESS;
    } else if (CLAN_ALL.equals(clanString)) {
      clan = clanString;
      playerCount = allPlayersCount;
    } else {
      clan = String.format("[%s]", clanString);
    }

    return String.format("%s (%d)", clan, playerCount);
  }

  @Override
  public String fromString(String clanString) {
    return clanString;
  }
}
