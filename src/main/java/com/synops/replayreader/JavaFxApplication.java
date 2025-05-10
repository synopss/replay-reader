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

  private static Class<?> springApplicationClass;
  private ConfigurableApplicationContext springContext;

  static void start(String[] args) {
    JavaFxApplication.springApplicationClass = ReplayReaderApplication.class;
    Application.launch(JavaFxApplication.class, args);
  }

  @Override
  public void init() {
    springContext = new SpringApplicationBuilder(springApplicationClass).initializers(
        getInitializer()).run(getParameters().getRaw().toArray(new String[0]));
  }

  @Override
  public void start(Stage stage) {
    Objects.requireNonNull(springContext);
    springContext.publishEvent(new StageReadyEvent(stage));
  }

  @Override
  public void stop() {
    springContext.close();
    Platform.exit();
  }

  private ApplicationContextInitializer<GenericApplicationContext> getInitializer() {
    return applicationContext -> applicationContext.registerBean(HostServices.class,
        this::getHostServices);
  }
}
