package com.synops.replayreader.core;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {

  public StageReadyEvent(Object source) {
    super(source);
  }

  public Stage getStage() {
    return (Stage) getSource();
  }
}
