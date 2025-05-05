package com.synops.replayreader.common.comparator;

@FunctionalInterface
public interface PlayerAndVehicleToDouble {

  Double apply(String player1, String player2);
}
