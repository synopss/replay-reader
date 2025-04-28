package com.synops.replayreader.service;

import org.springframework.stereotype.Service;

@Service
public class ReplayReaderService {

  private String replayFileName;

  public String getReplayFileName() {
    return replayFileName;
  }

  public void setReplayFileName(String replayFileName) {
    this.replayFileName = replayFileName;
  }
}
