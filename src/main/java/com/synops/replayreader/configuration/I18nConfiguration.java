package com.synops.replayreader.configuration;

import com.synops.replayreader.common.I18nUtils;
import java.util.ResourceBundle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class I18nConfiguration {

  @Bean
  public ResourceBundle bundle() {
    return I18nUtils.getBundle();
  }
}
