package com.synops.replayreader.core;

import com.synops.replayreader.core.event.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Startup implements ApplicationRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

  private final ApplicationEventPublisher publisher;

  public Startup(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    publisher.publishEvent(new StartupEvent());
  }

  @EventListener
  public void onApplicationEvent(ContextClosedEvent ignored) {
    LOGGER.info("Application is shutting down");
  }
}
