package com.synops.replayreader.controller;

import javafx.scene.control.Alert;

public class AboutWindowController extends Alert {

  public AboutWindowController() {
    super(AlertType.INFORMATION);
    setTitle("About");
    setHeaderText("Replay Reader was created by Synops: https://github.com/synopss/replay-reader");
  }
}
