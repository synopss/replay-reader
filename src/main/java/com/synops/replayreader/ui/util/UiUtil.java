package com.synops.replayreader.ui.util;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public final class UiUtil {

  public static void setDefaultIcon(Stage stage) {
    stage.getIcons().add(
        new Image(Objects.requireNonNull(stage.getClass().getResourceAsStream("/image/icon.png"))));
  }

  public static void setDefaultStyle(Scene scene) {
    scene.getStylesheets().add("/css/theme.css");
  }

  public static Window getWindow(Node node) {
    return node.getScene().getWindow();
  }

  public static void closeWindow(ActionEvent event) {
    closeWindow((Node) event.getSource());
  }

  public static void closeWindow(Node node) {
    var stage = (Stage) node.getScene().getWindow();
    stage.close();
  }
}
