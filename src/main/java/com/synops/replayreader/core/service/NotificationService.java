package com.synops.replayreader.core.service;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public interface NotificationService {

  void alert(String message, StackPane stackPane, Button... buttons);

  void information(String message, StackPane stackPane, Button... buttons);

  void warning(String message, StackPane stackPane, Button... buttons);

  void confirmation(String message, StackPane stackPane, Button... buttons);

  void notify(String message, AlertType alertType, StackPane stackPane, Button... buttons);
}
