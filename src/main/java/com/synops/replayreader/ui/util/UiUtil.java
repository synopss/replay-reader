package com.synops.replayreader.ui.util;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class UiUtil {

  public static void setDefaultIcon(Stage stage) {
    stage.getIcons().add(
        new Image(Objects.requireNonNull(stage.getClass().getResourceAsStream("/image/icon.png"))));
  }

  public static void setDefaultStyle(Stage stage) {
    stage.getScene().getStylesheets().add("/css/theme.css");
  }
}
