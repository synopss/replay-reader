package com.synops.replayreader.about;

import com.synops.replayreader.about.model.SoftwareInfo;
import com.synops.replayreader.core.window.WindowController;
import com.synops.replayreader.core.window.WindowManager;
import com.synops.replayreader.ui.util.UiUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView(value = "/views/about/software-used.fxml")
public class SoftwareUsedWindowController implements WindowController {

  private final WindowManager windowManager;
  private final ObservableList<SoftwareInfo> softwareData = FXCollections.observableArrayList();
  @FXML
  public Button closeButton;
  @FXML
  private TableView<SoftwareInfo> softwareTableView;
  @FXML
  private TableColumn<SoftwareInfo, SoftwareInfo> softwareColumn;
  @FXML
  private TableColumn<SoftwareInfo, SoftwareInfo> licenseColumn;

  public SoftwareUsedWindowController(WindowManager windowManager) {
    this.windowManager = windowManager;
  }

  @Override
  public void initialize() {
    softwareData.addAll(new SoftwareInfo("Apache Commons Collections",
            "https://commons.apache.org/proper/commons-collections/", "2.19.0", "Apache 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0"),
        new SoftwareInfo("Apache Commons IO", "https://commons.apache.org/proper/commons-lang/",
            "2.19.0", "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0"),
        new SoftwareInfo("Apache Commons Lang", "https://commons.apache.org/proper/commons-lang/",
            "2.19.0", "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0"),
        new SoftwareInfo("AtlantaFX", "https://mkpaz.github.io/atlantafx", "2.0.1", "MIT",
            "https://github.com/mkpaz/atlantafx/blob/master/LICENSE"),
        new SoftwareInfo("Ikonli", "https://github.com/kordamp/ikonli", "12.4.0", "Apache 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0"),
        new SoftwareInfo("Java", "https://www.oracle.com/java/", "24",
            "GPLv2 + classpath exception", "https://openjdk.org/legal/gplv2+ce.html"),
        new SoftwareInfo("JavaFX", "https://openjfx.io/", "24.0.1", "GPLv2 + classpath exception",
            "https://openjdk.org/legal/gplv2+ce.html"),
        new SoftwareInfo("JavaFX-Weaver", "https://github.com/rgielen/javafx-weaver", "2.0.1",
            "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0"),
        new SoftwareInfo("jSystemThemeDetector",
            "https://github.com/Dansoftowner/jSystemThemeDetector", "3.9.1", "MIT",
            "https://github.com/microsoft/fluentui/blob/master/LICENSE"),
        new SoftwareInfo("Spring Boot", "https://spring.io/", "3.4.5", "Apache 2.0",
            "https://www.apache.org/licenses/LICENSE-2.0"));

    softwareTableView.setItems(softwareData);

    softwareColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
    softwareColumn.setCellFactory(_ -> new TableCell<>() {
      @Override
      protected void updateItem(SoftwareInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
          return;
        }

        var link = new Hyperlink(item.getSoftware());
        link.setOnAction(e -> windowManager.openUrl(item.getSoftwareUrl()));
        var version = new Label(" " + item.getVersion());

        var hbox = new HBox(link, version);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5);
        setGraphic(hbox);
        setText(null);
      }
    });
    licenseColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
    licenseColumn.setCellFactory(_ -> new TableCell<>() {
      @Override
      protected void updateItem(SoftwareInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
          return;
        }

        var link = new Hyperlink(item.getLicense());
        link.setOnAction(e -> windowManager.openUrl(item.getLicenseUrl()));

        var hbox = new HBox(link);
        hbox.setAlignment(Pos.CENTER_LEFT);
        setGraphic(hbox);
        setText(null);
      }
    });

    closeButton.setOnAction(UiUtil::closeWindow);
  }
}
