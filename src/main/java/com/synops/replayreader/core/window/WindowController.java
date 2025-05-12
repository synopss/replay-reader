package com.synops.replayreader.core.window;

public interface WindowController extends Controller {

  default void onShowing() {
  }

  default void onShown() {
  }

  default void onHiding() {
  }

  default void onHidden() {
  }
}
