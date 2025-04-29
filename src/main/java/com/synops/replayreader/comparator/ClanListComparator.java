package com.synops.replayreader.comparator;

import java.util.Comparator;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;

public class ClanListComparator implements Comparator<String> {

  private final Function<String, Integer> function;
  @Value("${replay-reader.config.clan-all}")
  private String CLAN_ALL;

  public ClanListComparator(Function<String, Integer> function) {
    this.function = function;
  }

  @Override
  public int compare(String clan1, String clan2) {
    int valueClan1 = function.apply(clan1);
    int valueClan2 = function.apply(clan2);
    if (CLAN_ALL.equals(clan1)) {
      valueClan1 = Integer.MAX_VALUE;
    }

    return Integer.compare(valueClan2, valueClan1);
  }
}
