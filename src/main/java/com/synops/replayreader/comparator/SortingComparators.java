package com.synops.replayreader.comparator;

import com.synops.replayreader.model.PlayerSort;
import com.synops.replayreader.service.ReplayService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SortingComparators {

  private final ReplayService replayService;
  private final Map<String, SortingHolder> map = new HashMap<>();

  public SortingComparators(ReplayService replayService) {
    this.replayService = replayService;
  }

  public void initMap() {
    this.map.put(PlayerSort.GAMES.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorLong(replayService::getNumberOfGames)));
    this.map.put(PlayerSort.DMG.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorLong(replayService::getDamageDealt)));
    this.map.put(PlayerSort.KILLS.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorDouble(replayService::getAvgKills)));
    this.map.put(PlayerSort.PENRATE.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorDouble(replayService::getPenRate)));
    this.map.put(PlayerSort.TEAMDMG.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorInt(replayService::getAvgTdamageDealt)));
    PlayerAndVehicleToInt spot = replayService::getAvgSpotAssist;
    PlayerAndVehicleToInt track = replayService::getAvgTrackAssist;
    this.map.put(PlayerSort.ASSIST.getResourceBundleName(), new SortingHolder(
        new PlayerListComparatorInt((p, v) -> spot.apply(p, v) + track.apply(p, v))));
    this.map.put(PlayerSort.BLOCKED.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorInt(replayService::getAvgBlocked)));
    this.map.put(PlayerSort.XP.getResourceBundleName(),
        new SortingHolder(new PlayerListComparatorInt(replayService::getAvgXp)));
  }

  public Comparator<String> getComparator(String sort) {
    SortingHolder sortingHolder = map.get(sort);
    if (sortingHolder == null) {
      throw new IllegalArgumentException(String.format("Could not find sorting for <%s>", sort));
    } else {
      return sortingHolder.comparator();
    }
  }
}
