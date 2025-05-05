package com.synops.replayreader.core.event;

public class ReplayProgressEvent {

  private int count = 0;
  private int total = 0;
  private String replay = "";
  private int countCorrupt = 0;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getReplay() {
    return replay;
  }

  public void setReplay(String replay) {
    this.replay = replay;
  }

  public int getCountCorrupt() {
    return countCorrupt;
  }

  public void setCountCorrupt(int countCorrupt) {
    this.countCorrupt = countCorrupt;
  }
}
