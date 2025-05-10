package com.synops.replayreader.core;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.jthemedetecor.OsThemeDetector;
import com.synops.replayreader.common.i18n.I18nUtils;
import com.synops.replayreader.ui.util.UiUtil;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
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
      handleDarkMode();
      stage.setScene(new Scene(parent, applicationWidth, applicationHeight));
      stage.setResizable(false);
      stage.setTitle(resourceBundle.getString("main.title"));
      UiUtil.setDefaultIcon(stage);
      UiUtil.setDefaultStyle(stage);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void handleDarkMode() {
    final OsThemeDetector detector = OsThemeDetector.getDetector();
    final boolean isDarkThemeUsed = detector.isDark();
    if (isDarkThemeUsed) {
      Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    } else {
      Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    }

    detector.registerListener(isDark -> {
      if (isDark) {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
      } else {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
      }
    });
  }
}
