package com.synops.replayreader;

import com.synops.replayreader.core.StageReadyEvent;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavaFxApplication extends Application {

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(ReplayReaderApplication.class)
        .initializers(getInitializer())
        .run();
  }

  @Override
  public void start(Stage stage) {
    Objects.requireNonNull(applicationContext);
    applicationContext.publishEvent(new StageReadyEvent(stage));
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }

  private ApplicationContextInitializer<GenericApplicationContext> getInitializer() {
    return applicationContext -> applicationContext.registerBean(HostServices.class, this::getHostServices);
  }
}
