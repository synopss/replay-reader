package com.synops.replayreader.core.configuration;

import com.synops.replayreader.clan.comparator.ClanListComparator;
import com.synops.replayreader.clan.util.ClanStringConverter;
import com.synops.replayreader.common.comparator.SortingComparators;
import com.synops.replayreader.core.service.DialogService;
import com.synops.replayreader.core.service.DialogServiceImpl;
import com.synops.replayreader.core.service.NotificationService;
import com.synops.replayreader.core.service.NotificationServiceImpl;
import com.synops.replayreader.maps.comparator.MapsComparator;
import com.synops.replayreader.replay.controller.ReplayController;
import com.synops.replayreader.replay.service.ReplayModelBuilder;
import com.synops.replayreader.replay.service.ReplayReader;
import com.synops.replayreader.replay.service.ReplayService;
import com.synops.replayreader.replay.service.ReplayServiceImpl;
import com.synops.replayreader.ui.util.DragDropSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplayReaderConfiguration {

  @Bean
  public ReplayService replayService(ReplayController replayController) {
    return new ReplayServiceImpl(replayController);
  }

  @Bean
  public DialogService dialogService() {
    return new DialogServiceImpl();
  }

  @Bean
  public NotificationService notificationService() {
    return new NotificationServiceImpl();
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
