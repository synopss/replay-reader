package com.synops.replayreader.controller;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;

public class AboutWindowController extends Alert {

  public AboutWindowController(ResourceBundle resourceBundle) {
    super(AlertType.INFORMATION);
    setTitle(resourceBundle.getString("main.menu.help.about"));
    setHeaderText(resourceBundle.getString("main.menu.help.about.text")
        + ": https://github.com/synopss/replay-reader");
  }
}
