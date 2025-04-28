package com.synops.replayreader.service;

import org.springframework.stereotype.Service;

@Service
public class ReplayService {

  private String replayFileName;

  public String getReplayFileName() {
    return replayFileName;
  }

  public void setReplayFileName(String replayFileName) {
    this.replayFileName = replayFileName;
  }
}
