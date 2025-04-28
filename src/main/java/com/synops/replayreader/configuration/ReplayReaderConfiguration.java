package com.synops.replayreader.configuration;

import com.synops.replayreader.service.ReplayReaderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplayReaderConfiguration {

  @Bean
  public ReplayReaderService replayReaderService() {
    return new ReplayReaderService();
  }
}
