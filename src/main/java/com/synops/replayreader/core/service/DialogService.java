package com.synops.replayreader.core.service;

import javafx.scene.control.Alert.AlertType;

public interface DialogService {

  void showAlertError(Throwable throwable);

  void alert(AlertType alertType, String message);

  void alertConfirm(String message, Runnable runnable);
}
