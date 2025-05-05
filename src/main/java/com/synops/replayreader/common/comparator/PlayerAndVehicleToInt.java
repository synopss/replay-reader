package com.synops.replayreader.common.comparator;

@FunctionalInterface
public interface PlayerAndVehicleToInt {

  int apply(String player1, String player2);
}
