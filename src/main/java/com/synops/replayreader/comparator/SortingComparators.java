package com.synops.replayreader.comparator;

import com.synops.replayreader.model.PlayerSort;
import com.synops.replayreader.service.ReplayService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortingComparators {

  private final ReplayService replayService;
  private final Map<PlayerSort, SortingHolder> map = new HashMap<>();

  public SortingComparators(ReplayService replayService) {
    this.replayService = replayService;
    initMap();
  }

  private void initMap() {
    this.map.put(PlayerSort.GAMES,
        new SortingHolder(new PlayerListComparatorLong(replayService::getNumberOfGames)));
    this.map.put(PlayerSort.DMG,
        new SortingHolder(new PlayerListComparatorLong(replayService::getDamageDealt)));
    this.map.put(PlayerSort.KILLS,
        new SortingHolder(new PlayerListComparatorDouble(replayService::getAvgKills)));
    this.map.put(PlayerSort.PENRATE,
        new SortingHolder(new PlayerListComparatorDouble(replayService::getPenRate)));
    this.map.put(PlayerSort.TEAMDMG,
        new SortingHolder(new PlayerListComparatorInt(replayService::getAvgTdamageDealt)));
    PlayerAndVehicleToInt spot = replayService::getAvgSpotAssist;
    PlayerAndVehicleToInt track = replayService::getAvgTrackAssist;
    this.map.put(PlayerSort.ASSIST, new SortingHolder(
        new PlayerListComparatorInt((p, v) -> spot.apply(p, v) + track.apply(p, v))));
    this.map.put(PlayerSort.BLOCKED,
        new SortingHolder(new PlayerListComparatorInt(replayService::getAvgBlocked)));
    this.map.put(PlayerSort.XP,
        new SortingHolder(new PlayerListComparatorInt(replayService::getAvgXp)));
  }

  public Comparator<String> getComparator(PlayerSort sort) {
    SortingHolder sortingHolder = map.get(sort);
    if (sortingHolder == null) {
      throw new IllegalArgumentException(String.format("Could not find sorting for <%s>", sort));
    } else {
      return sortingHolder.comparator();
    }
  }
}
