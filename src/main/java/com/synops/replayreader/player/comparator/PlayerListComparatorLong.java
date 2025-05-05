package com.synops.replayreader.player.comparator;

import com.synops.replayreader.common.comparator.PlayerAndVehicleToInt;
import java.util.Comparator;

public class PlayerListComparatorLong implements Comparator<String> {

  private final PlayerAndVehicleToInt function;

  public PlayerListComparatorLong(PlayerAndVehicleToInt function) {
    this.function = function;
  }

  @Override
  public int compare(String player1, String player2) {
    return Long.compare(this.function.apply(player2, null), this.function.apply(player1, null));
  }
}
