package com.synops.replayreader.player.comparator;

import com.synops.replayreader.common.comparator.PlayerAndVehicleToInt;
import java.util.Comparator;

public class PlayerListComparatorInt implements Comparator<String> {

  private final PlayerAndVehicleToInt function;

  public PlayerListComparatorInt(PlayerAndVehicleToInt function) {
    this.function = function;
  }

  public int compare(String player1, String player2) {
    return Integer.compare(this.function.apply(player2, null),
        this.function.apply(player1, null));
  }
}
