package com.synops.replayreader.core.service;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;

public interface NotificationService {

  void notify(String message, AlertType alertType, StackPane stackPane);

  void alert(String message, StackPane stackPane);

  void information(String message, StackPane stackPane);

  void warning(String message, StackPane stackPane);

  void confirmation(String message, StackPane stackPane);
}
