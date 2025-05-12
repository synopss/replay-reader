package com.synops.replayreader.core.window;

import com.synops.replayreader.about.AboutWindowController;
import com.synops.replayreader.ui.controller.MainWindowController;
import com.synops.replayreader.about.SoftwareUsedWindowController;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.info.BuildProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class WindowManager {

  public static FxWeaver fxWeaver;
  private final ResourceBundle resourceBundle;
  private final BuildProperties buildProperties;
  private final HostServices hostServices;
  private ApplicationWindow mainWindow;
  private Window rootWindow;

  public WindowManager(ResourceBundle resourceBundle, FxWeaver fxWeaver,
      BuildProperties buildProperties, @Nullable HostServices hostServices) {
    this.resourceBundle = resourceBundle;
    WindowManager.fxWeaver = fxWeaver;
    this.buildProperties = buildProperties;
    this.hostServices = hostServices;
  }

  public void openMain(Stage stage) {
    Platform.runLater(() -> {
      if (mainWindow == null) {
        mainWindow = ApplicationWindow.builder(MainWindowController.class).setStage(stage)
            .setResizeable(false).setTitle(buildProperties.getName()).build();
      }
      mainWindow.show();
    });
  }

  public void openAbout() {
    Platform.runLater(
        () -> ApplicationWindow.builder(AboutWindowController.class).setParent(rootWindow)
            .setResizeable(false).setTitle(
                MessageFormat.format(resourceBundle.getString("about.title"),
                    buildProperties.getName())).build().show());
  }

  public void openSoftwareUsed(Window aboutWindow) {
    Platform.runLater(
        () -> ApplicationWindow.builder(SoftwareUsedWindowController.class).setParent(aboutWindow)
            .setResizeable(false).setTitle(
                MessageFormat.format(resourceBundle.getString("software-used.title"),
                    buildProperties.getName())).build().show());
  }

  public void setRootWindow(Window window) {
    rootWindow = window;
  }

  public void openUrl(String url) {
    if (hostServices == null) {
      return;
    }
    hostServices.showDocument(url);
  }
}
