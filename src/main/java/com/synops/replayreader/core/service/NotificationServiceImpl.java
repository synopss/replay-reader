package com.synops.replayreader.core.service;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
  private static final int NOTIFICATION_SPACING = 10;
  private static final int DEFAULT_BOTTOM_MARGIN = 40;

  private final Map<StackPane, ObservableList<Notification>> activeNotifications = new HashMap<>();

  public void alert(String message, StackPane stackPane, Button... buttons) {
    notify(message, AlertType.ERROR, stackPane, buttons);
  }

  public void information(String message, StackPane stackPane, Button... buttons) {
    notify(message, AlertType.INFORMATION, stackPane, buttons);
  }

  public void warning(String message, StackPane stackPane, Button... buttons) {
    notify(message, AlertType.WARNING, stackPane, buttons);
  }

  public void confirmation(String message, StackPane stackPane, Button... buttons) {
    notify(message, AlertType.CONFIRMATION, stackPane, buttons);
  }

  public void notify(String message, AlertType alertType, StackPane stackPane, Button... buttons) {
    var notificationList = activeNotifications.computeIfAbsent(stackPane,
        _ -> FXCollections.observableArrayList());

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

    int notificationCount = notificationList.size();
    int bottomMargin = (int) (DEFAULT_BOTTOM_MARGIN + (notificationCount * (
        notification.prefHeight(-1) + NOTIFICATION_SPACING)));

    StackPane.setMargin(notification, new Insets(0, 10, bottomMargin, 0));

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

    if (buttons.length > 0) {
      notification.setPrimaryActions(buttons);
    }

    notificationList.add(notification);
    notification.setOnClose(_ -> {
      removeNotification(notification, stackPane);
    });
    var in = Animations.slideInDown(notification, Duration.millis(250));
    stackPane.getChildren().add(notification);
    in.playFromStart();
    LOGGER.debug("Notification: {}", message);

    scheduleAutoClose(notification, stackPane);
  }

  private void removeNotification(Notification notification, StackPane stackPane) {
    var notificationList = activeNotifications.get(stackPane);
    if (notificationList == null) {
      return;
    }

    var out = Animations.slideOutDown(notification, Duration.millis(250));
    out.setOnFinished(_ -> {
      stackPane.getChildren().remove(notification);
      notificationList.remove(notification);
      repositionNotifications(notificationList);
    });
    out.playFromStart();
  }

  private void repositionNotifications(ObservableList<Notification> notificationList) {
    for (int i = 0; i < notificationList.size(); i++) {
      var notification = notificationList.get(i);
      int bottomMargin = (int) (DEFAULT_BOTTOM_MARGIN + (i * (notification.prefHeight(-1)
          + NOTIFICATION_SPACING)));

      var moveUp = Animations.fadeIn(notification, Duration.millis(150));
      StackPane.setMargin(notification, new Insets(0, 10, bottomMargin, 0));
      moveUp.playFromStart();
    }
  }

  private void scheduleAutoClose(Notification notification, StackPane stackPane) {
    var delay = new PauseTransition(Duration.seconds(5));
    delay.setOnFinished(_ -> removeNotification(notification, stackPane));
    delay.play();
  }
}
