package com.synops.replayreader.comparator;

@FunctionalInterface
public interface PlayerAndVehicleToDouble {

  Double apply(String player1, String player2);
}
