package com.synops.replayreader.util;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class DragDropSupport {

  private final Consumer<List<File>> loader;

  public DragDropSupport(Consumer<List<File>> loader) {
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

    System.out.println("Drag dropped: " + success);
    event.setDropCompleted(success);
    event.consume();
  }
}
