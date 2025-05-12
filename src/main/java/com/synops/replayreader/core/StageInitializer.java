package com.synops.replayreader.core;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.jthemedetecor.OsThemeDetector;
import com.synops.replayreader.core.window.WindowManager;
import javafx.application.Application;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

  private final WindowManager windowManager;

  public StageInitializer(WindowManager windowManager) {
    this.windowManager = windowManager;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    handleDarkMode();
    windowManager.openMain(event.getStage());
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
