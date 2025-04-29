package com.synops.replayreader.comparator;

@FunctionalInterface
public interface PlayerAndVehicleAndMapToInt {

  int apply(String player, String vehicle, String map);
}
