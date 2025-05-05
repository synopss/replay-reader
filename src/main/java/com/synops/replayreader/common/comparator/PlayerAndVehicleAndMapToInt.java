package com.synops.replayreader.common.comparator;

@FunctionalInterface
public interface PlayerAndVehicleAndMapToInt {

  int apply(String player, String vehicle, String map);
}
