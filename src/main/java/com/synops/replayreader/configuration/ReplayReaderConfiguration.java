package com.synops.replayreader.configuration;

import com.synops.replayreader.comparator.ClanListComparator;
import com.synops.replayreader.comparator.MapsComparator;
import com.synops.replayreader.comparator.SortingComparators;
import com.synops.replayreader.control.MapListCell;
import com.synops.replayreader.control.PlayerListCell;
import com.synops.replayreader.control.VehicleListCell;
import com.synops.replayreader.replay.ReplayController;
import com.synops.replayreader.replay.ReplayModelBuilder;
import com.synops.replayreader.replay.ReplayReader;
import com.synops.replayreader.service.ReplayService;
import com.synops.replayreader.service.ReplayServiceImpl;
import com.synops.replayreader.util.ClanStringConverter;
import com.synops.replayreader.util.DragDropSupport;
import java.util.ResourceBundle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplayReaderConfiguration {

  @Bean
  public ReplayService replayService(ReplayController replayController) {
    return new ReplayServiceImpl(replayController);
  }

  @Bean
  public ClanStringConverter clanStringConverter() {
    return new ClanStringConverter();
  }

  @Bean
  public ClanListComparator clanListComparator() {
    return new ClanListComparator();
  }

  @Bean
  public MapsComparator mapsComparator() {
    return new MapsComparator();
  }

  @Bean
  public SortingComparators sortingComparators(ReplayService replayService) {
    return new SortingComparators(replayService);
  }

  @Bean
  public ReplayController replayController(ReplayReader replayReader,
      ReplayModelBuilder replayModelBuilder) {
    return new ReplayController(replayReader, replayModelBuilder);
  }

  @Bean
  public DragDropSupport dragDropSupport() {
    return new DragDropSupport();
  }

  @Bean
  public ReplayReader replayReader() {
    return new ReplayReader();
  }

  @Bean
  public ReplayModelBuilder replayModelBuilder() {
    return new ReplayModelBuilder();
  }
}
