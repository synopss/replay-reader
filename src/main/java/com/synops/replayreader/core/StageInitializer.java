package com.synops.replayreader.core;

import com.synops.replayreader.common.i18n.I18nUtils;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

  private final ApplicationContext applicationContext;
  private final ResourceBundle resourceBundle;

  @Value("${replay-reader.ui.height}")
  private int applicationHeight;

  @Value("${replay-reader.ui.width}")
  private int applicationWidth;

  @Value("classpath:/views/main.fxml")
  private Resource mainResource;

  public StageInitializer(ApplicationContext applicationContext, ResourceBundle resourceBundle) {
    this.applicationContext = applicationContext;
    this.resourceBundle = resourceBundle;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    try {
      var bundle = I18nUtils.getBundle();
      var fxmlLoader = new FXMLLoader(mainResource.getURL(), bundle);
      fxmlLoader.setControllerFactory(applicationContext::getBean);
      Parent parent = fxmlLoader.load();
      var stage = event.getStage();
      stage.setScene(new Scene(parent, applicationWidth, applicationHeight));
      stage.setResizable(false);
      stage.setTitle(resourceBundle.getString("main.title"));
      stage.getIcons().add(new Image(Objects.requireNonNull(stage.getClass().getResourceAsStream("/image/icon.png"))));
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
