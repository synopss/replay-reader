package com.synops.replayreader.maps.comparator;

import static com.synops.replayreader.common.util.Constants.MAP_ALL;

import java.util.Comparator;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class MapListComparator implements Comparator<String> {

  private Function<String, Integer> function;

  public MapListComparator() {
  }

  public void configure(Function<String, Integer> function) {
    this.function = function;
  }

  @Override
  public int compare(String map1, String map2) {
    int valueMap1 = function.apply(map1);
    int valueMap2 = function.apply(map2);
    if (MAP_ALL.equals(map1)) {
      valueMap1 = Integer.MAX_VALUE;
    }

    return Integer.compare(valueMap2, valueMap1);
  }
}