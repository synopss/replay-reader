package com.synops.replayreader.ui.controller;

import com.synops.replayreader.core.window.WindowController;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@FxmlView(value = "/views/about/aboutview.fxml")
public class AboutWindowController implements WindowController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AboutWindowController.class);

  @Override
  public void initialize() {
    LOGGER.debug("Initializing AboutWindowController");
  }
}
