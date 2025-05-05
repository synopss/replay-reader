package com.synops.replayreader.comparator;

import static com.synops.replayreader.util.Constants.OVERALL;

import java.util.Comparator;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public class MapsComparator implements Comparator<String> {

  private StringProperty selectedPlayer;
  private StringProperty selectedVehicle;
  private PlayerAndVehicleAndMapToInt function;

  public MapsComparator() {
  }

  public void configure(StringProperty selectedPlayer, StringProperty selectedVehicle,
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
