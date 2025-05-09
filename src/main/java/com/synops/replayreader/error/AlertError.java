package com.synops.replayreader.error;

import static javafx.scene.control.Alert.AlertType.ERROR;

import com.synops.replayreader.ui.util.UiUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AlertError {

  private static final Logger LOGGER = LoggerFactory.getLogger(AlertError.class);

  public static void showAlertError(Throwable throwable) {
    Platform.runLater(() -> alert(ERROR, "Error",
        throwable.getClass().getSimpleName() + ": " + throwable.getMessage(),
        ExceptionUtils.getStackTrace(throwable)));
    LOGGER.error("Error {}", throwable.getMessage(), throwable);
  }

  private static void alert(AlertType alertType, String title, String message, String stackTrace) {
    var alert = buildAlert(alertType, title, message, stackTrace);
    alert.showAndWait();
  }

  private static Alert buildAlert(AlertType alertType, String title, String message,
      String stackTrace) {
    var alert = new Alert(alertType);
    var stage = (Stage) alert.getDialogPane().getScene().getWindow();
    UiUtil.setDefaultIcon(stage);

    if (title != null) {
      alert.setTitle(title);
    }
    alert.setHeaderText(null);

    var vbox = new VBox();
    var hbox = new HBox();
    hbox.setAlignment(Pos.TOP_RIGHT);

    var textArea = new TextArea();
    textArea.setWrapText(true);
    textArea.setEditable(false);
    textArea.setText(message);
    textArea.setPrefHeight(StringUtils.defaultString(message).length() < 120 ? 60 : 100);
    vbox.setPadding(new Insets(14.0));
    vbox.getChildren().addAll(hbox, textArea);
    alert.getDialogPane().setContent(vbox);

    if (stackTrace != null) {
      var stackTraceTextArea = new TextArea(stackTrace);
      stackTraceTextArea.setWrapText(false);
      stackTraceTextArea.setEditable(false);
      stackTraceTextArea.setMaxWidth(Double.MAX_VALUE);
      stackTraceTextArea.setMaxHeight(Double.MAX_VALUE);
      GridPane.setHgrow(stackTraceTextArea, Priority.ALWAYS);
      GridPane.setVgrow(stackTraceTextArea, Priority.ALWAYS);

      var content = new GridPane();
      content.setMaxWidth(Double.MAX_VALUE);
      content.add(new Label("Full stacktrace:"), 0, 0);
      content.add(stackTraceTextArea, 0, 1);

      alert.getDialogPane().setExpandableContent(content);
    }

    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

    return alert;
  }
}
