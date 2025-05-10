package com.synops.replayreader.update;

import java.util.TimerTask;

class VersionCheckTask extends TimerTask {

  private final Runnable runnable;

  public VersionCheckTask(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public void run() {
    runnable.run();
  }
}
