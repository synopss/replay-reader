package com.synops.replayreader.controller;

import com.synops.replayreader.service.ReplayService;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class MainViewController {

  private final ReplayService replayService;
  private final ResourceBundle resourceBundle;

  @FXML
  protected AnchorPane root;

  public MainViewController(ReplayService replayService, ResourceBundle resourceBundle) {
    this.replayService = replayService;
    this.resourceBundle = resourceBundle;
  }

  @FXML
  public void initialize() {
    System.out.println(replayService.getReplayFileName());
  }

  @FXML
  private void onMenuExit() {
    Platform.exit();
  }

  @FXML
  private void onClickAbout() {
    (new AboutWindowController(resourceBundle)).showAndWait();
  }
}
