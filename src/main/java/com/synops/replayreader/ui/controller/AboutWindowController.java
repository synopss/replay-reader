package com.synops.replayreader.ui.controller;

import com.synops.replayreader.core.window.WindowController;
import com.synops.replayreader.ui.util.UiUtil;
import javafx.application.HostServices;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@FxmlView(value = "/views/about/aboutview.fxml")
public class AboutWindowController implements WindowController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AboutWindowController.class);
  private static final String GITHUB_REPOSITORY = "https://github.com/synopss/replay-reader";
  private static final String AUTHOR_GITHUB_PROFILE = "https://github.com/synopss";
  private final HostServices hostServices;
  private final BuildProperties buildProperties;
  public Text titleText;
  public Hyperlink repositoryLink;
  public Hyperlink openSourceLink;
  public Hyperlink authorLink;
  public Button closeButton;

  public AboutWindowController(@Nullable HostServices hostServices,
      BuildProperties buildProperties) {
    this.hostServices = hostServices;
    this.buildProperties = buildProperties;
  }

  @Override
  public void initialize() {
    titleText.setText(buildProperties.getName() + " " + buildProperties.getVersion());
    repositoryLink.setOnAction(_ -> openUrl(GITHUB_REPOSITORY));
    openSourceLink.setOnAction(_ -> LOGGER.info("Open Source Link"));
    authorLink.setOnAction(_ -> openUrl(AUTHOR_GITHUB_PROFILE));
    closeButton.setOnAction(UiUtil::closeWindow);
    LOGGER.debug("Initializing AboutWindowController");
  }

  private void openUrl(String url) {
    if (hostServices == null) {
      return;
    }
    hostServices.showDocument(url);
  }
}
