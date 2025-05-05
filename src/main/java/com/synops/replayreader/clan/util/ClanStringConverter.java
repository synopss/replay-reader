package com.synops.replayreader.clan.util;

import static com.synops.replayreader.common.util.Constants.CLAN_ALL;
import static com.synops.replayreader.common.util.Constants.CLAN_LESS;

import java.util.function.Function;
import javafx.util.StringConverter;
import org.springframework.stereotype.Component;

@Component
public class ClanStringConverter extends StringConverter<String> {

  private Function<String, Integer> function;
  private int allPlayersCount;

  public ClanStringConverter() {
  }

  public void configure(Function<String, Integer> function, int allPlayersCount) {
    this.function = function;
    this.allPlayersCount = allPlayersCount;
  }

  @Override
  public String toString(String clanString) {
    if (clanString == null) {
      return null;
    }
    int playerCount = function.apply(clanString);
    String clan;
    if (clanString.isEmpty()) {
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
