package com.synops.replayreader.comparator;

import java.util.Comparator;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Value;

public class MapsComparator implements Comparator<String> {

  private final StringProperty selectedPlayer;
  private final StringProperty selectedVehicle;
  private final PlayerAndVehicleAndMapToInt function;
  @Value("${replay-reader.config.overall}")
  private String OVERALL;

  public MapsComparator(StringProperty selectedPlayer, StringProperty selectedVehicle,
      PlayerAndVehicleAndMapToInt function) {
    this.selectedPlayer = selectedPlayer;
    this.selectedVehicle = selectedVehicle;
    this.function = function;
  }

  @Override
  public int compare(String map1, String map2) {
    String vehicle = this.selectedVehicle.get();
    if (OVERALL.equals(vehicle)) {
      vehicle = null;
    }

    long map1Count = this.function.apply(this.selectedPlayer.get(), vehicle, map1);
    long map2Count = this.function.apply(this.selectedPlayer.get(), vehicle, map2);
    return Long.compare(map2Count, map1Count);
  }
}
