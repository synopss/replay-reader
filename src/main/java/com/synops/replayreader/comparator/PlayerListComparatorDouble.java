package com.synops.replayreader.comparator;

import java.util.Comparator;

public class PlayerListComparatorDouble implements Comparator<String> {

  private final PlayerAndVehicleToDouble function;

  public PlayerListComparatorDouble(PlayerAndVehicleToDouble function) {
    this.function = function;
  }

  @Override
  public int compare(String player1, String player2) {
    var player2Value = function.apply(player2, null);
    var player1Value = function.apply(player1, null);
    if (player1Value.isNaN()) {
      player1Value = (double) 0;
    }

    if (player2Value.isNaN()) {
      player2Value = (double) 0;
    }

    return Double.compare(player2Value, player1Value);
  }
}
