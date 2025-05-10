package com.synops.replayreader.ui.util;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DragDropSupport {
  private static final Logger LOGGER = LoggerFactory.getLogger(DragDropSupport.class);

  private Consumer<List<File>> loader;

  public DragDropSupport() {
  }

  public void configure(Consumer<List<File>> loader) {
    this.loader = loader;
  }

  public void onDragOver(DragEvent event) {
    var db = event.getDragboard();
    if (db.hasFiles()) {
      event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
    } else {
      event.consume();
    }
  }

  public void onDragDropped(DragEvent event) {
    var db = event.getDragboard();
    boolean success = false;
    if (db.hasFiles()) {
      success = true;
      loader.accept(db.getFiles());
    }

    LOGGER.debug("Drag dropped: {}", success);
    event.setDropCompleted(success);
    event.consume();
  }
}
