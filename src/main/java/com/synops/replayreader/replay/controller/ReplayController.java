package com.synops.replayreader.replay.controller;

import com.synops.replayreader.replay.service.ReplayModelBuilder;
import com.synops.replayreader.replay.service.ReplayReader;
import com.synops.replayreader.replay.model.BattleType;
import com.synops.replayreader.replay.model.Replay;
import com.synops.replayreader.replay.model.ReplayCollection;
import com.synops.replayreader.replay.model.ReplayCollectionImpl;
import com.synops.replayreader.replay.model.ReplayFilter;
import com.synops.replayreader.core.event.ReplayProgressEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class ReplayController {

  private final ReplayReader replayReader;
  private final ReplayModelBuilder replayModelBuilder;

  public ReplayController(ReplayReader replayReader, ReplayModelBuilder replayModelBuilder) {
    this.replayReader = replayReader;
    this.replayModelBuilder = replayModelBuilder;
  }

  public Replay readReplay(File file) {
    replayReader.configure(file);
    List<String> jsonList = replayReader.read();
    replayModelBuilder.configure(jsonList);
    return replayModelBuilder.create();
  }

  public ReplayCollection readReplays(List<File> files, BattleType battleType, Consumer<ReplayProgressEvent> progressListener) {
    var replays = new ArrayList<Replay>();
    var count = new Count();
    var countCorrupt = new Count();

    files.forEach((f) -> {
      try {
        count.inc();
        replays.add(readReplay(f));
      } catch (Exception ex) {
        countCorrupt.inc();
      } finally {
        ReplayProgressEvent event = new ReplayProgressEvent();
        event.setCount(count.getCount());
        event.setCountCorrupt(countCorrupt.getCount());
        event.setTotal(files.size());
        event.setReplay(f.getName());
        progressListener.accept(event);
      }

    });
    System.out.printf("%nfinished reading %s replays%n", replays.size());
    System.out.printf("battle type = %s%n", battleType.name());
    ReplayFilter replayFilter = ReplayFilter.createDefault().setBattleType(battleType);
    return new ReplayCollectionImpl(replays, replayFilter);
  }

  private static class Count {
    public int c;

    private Count() {
      this.c = 0;
    }

    public void inc() {
      ++this.c;
    }

    public int getCount() {
      return this.c;
    }
  }
}
