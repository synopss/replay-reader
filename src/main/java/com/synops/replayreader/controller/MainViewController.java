package com.synops.replayreader.controller;

import com.synops.replayreader.service.ReplayReaderService;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class MainViewController {

  private final ReplayReaderService replayReaderService;

  public MainViewController(ReplayReaderService replayReaderService) {
    this.replayReaderService = replayReaderService;
  }

  @FXML
  public void initialize() {
    System.out.println(replayReaderService.getReplayFileName());
  }
}
