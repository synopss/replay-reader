package com.synops.replayreader.core.window;

import com.synops.replayreader.ui.controller.AboutWindowController;
import com.synops.replayreader.ui.controller.MainWindowController;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class WindowManager {

  public static FxWeaver fxWeaver;
  private final ResourceBundle resourceBundle;
  private ApplicationWindow mainWindow;
  private Window rootWindow;

  public WindowManager(ResourceBundle resourceBundle, FxWeaver fxWeaver) {
    this.resourceBundle = resourceBundle;
    WindowManager.fxWeaver = fxWeaver;
  }

  public void openMain(Stage stage) {
    Platform.runLater(() -> {
      if (mainWindow == null) {
        mainWindow = ApplicationWindow.builder(MainWindowController.class).setStage(stage)
            .setResizeable(false).setTitle(resourceBundle.getString("main.title")).build();
      }
      mainWindow.show();
    });
  }

  public void openAbout() {
    Platform.runLater(
        () -> ApplicationWindow.builder(AboutWindowController.class).setParent(rootWindow)
            .setResizeable(false).setTitle(
                MessageFormat.format(resourceBundle.getString("about.title"),
                    resourceBundle.getString("main.title"))).build().show());
  }

  public void setRootWindow(Window window) {
    rootWindow = window;
  }
}
