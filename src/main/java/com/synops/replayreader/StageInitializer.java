package com.synops.replayreader;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

  private final ApplicationContext applicationContext;

  @Value("${replay-reader.application.ui.title}")
  private String applicationTitle;

  @Value("${replay-reader.application.ui.height}")
  private int applicationHeight;

  @Value("${replay-reader.application.ui.width}")
  private int applicationWidth;

  @Value("classpath:/views/main.fxml")
  private Resource mainResource;

  public StageInitializer(ApplicationContext applicationCOntext) {
    this.applicationContext = applicationCOntext;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    try {
      var fxmlLoader = new FXMLLoader(mainResource.getURL());
      fxmlLoader.setControllerFactory(applicationContext::getBean);
      Parent parent = fxmlLoader.load();
      var stage = event.getStage();
      stage.setScene(new Scene(parent, applicationWidth, applicationHeight));
      stage.setResizable(false);
      stage.setTitle(applicationTitle);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
