package com.synops.replayreader.core.service;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationServiceImpl implements NotificationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

  public void notify(String message, AlertType alertType, StackPane stackPane) {
    var fontIcon = switch (alertType) {
      case NONE -> null;
      case INFORMATION -> FluentUiRegularAL.INFO_16;
      case WARNING -> FluentUiRegularMZ.WARNING_16;
      case CONFIRMATION -> FluentUiRegularAL.CHECKMARK_CIRCLE_16;
      case ERROR -> FluentUiRegularAL.ERROR_CIRCLE_16;
    };

    var notification = new Notification(message, fontIcon != null ? new FontIcon(fontIcon) : null);
    notification.getStyleClass().add(Styles.ELEVATED_1);
    notification.setPrefHeight(Region.USE_PREF_SIZE);
    notification.setMaxHeight(Region.USE_PREF_SIZE);

    StackPane.setAlignment(notification, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(notification, new Insets(0, 10, 40, 0));

    var style = switch (alertType) {
      case NONE -> null;
      case INFORMATION -> Styles.ACCENT;
      case WARNING -> Styles.WARNING;
      case CONFIRMATION -> Styles.SUCCESS;
      case ERROR -> Styles.DANGER;
    };

    if (style != null) {
      notification.getStyleClass().add(style);
    }

    notification.setOnClose(_ -> {
      var out = Animations.slideOutDown(notification, Duration.millis(250));
      out.setOnFinished(_ -> stackPane.getChildren().remove(notification));
      out.playFromStart();
    });
    var in = Animations.slideInDown(notification, Duration.millis(250));
    stackPane.getChildren().add(notification);
    in.playFromStart();
    LOGGER.debug("Notification: {}", message);
  }

  public void alert(String message, StackPane stackPane) {
    notify(message, AlertType.ERROR, stackPane);
  }

  public void information(String message, StackPane stackPane) {
    notify(message, AlertType.INFORMATION, stackPane);
  }

  public void warning(String message, StackPane stackPane) {
    notify(message, AlertType.WARNING, stackPane);
  }

  public void confirmation(String message, StackPane stackPane) {
    notify(message, AlertType.CONFIRMATION, stackPane);
  }
}
