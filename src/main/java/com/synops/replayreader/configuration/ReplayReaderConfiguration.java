package com.synops.replayreader.configuration;

import com.synops.replayreader.service.ReplayService;
import com.synops.replayreader.service.ReplayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplayReaderConfiguration {

  @Bean
  public ReplayService replayService() {
    return new ReplayServiceImpl();
  }
}
