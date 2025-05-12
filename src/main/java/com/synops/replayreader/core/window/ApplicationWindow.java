package com.synops.replayreader.core.window;

import com.synops.replayreader.common.i18n.I18nUtils;
import com.synops.replayreader.ui.util.UiUtil;
import java.util.Objects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ApplicationWindow {

  final Scene scene;
  final Stage stage;

  ApplicationWindow(WindowBuilder builder) {
    scene = new Scene(builder.getRoot());
    UiUtil.setDefaultStyle(scene);
    stage = Objects.requireNonNullElseGet(builder.getStage(), Stage::new);
    UiUtil.setDefaultIcon(stage);

    if (builder.getParent() != null) {
      stage.initOwner(builder.getParent());
      stage.initModality(Modality.WINDOW_MODAL);
    }

    stage.setMinWidth(builder.getRoot().minWidth(-1));
    stage.setMinHeight(builder.getRoot().minHeight(-1));

    stage.setTitle(builder.getTitle());
    stage.setScene(scene);

    if (!builder.isResizeable()) {
      stage.setResizable(false);
    }

    stage.setOnShowing(_ -> builder.getController().onShowing());
    stage.setOnShown(_ -> builder.getController().onShown());
    stage.setOnHiding(_ -> builder.getController().onHiding());
    stage.setOnHidden(_ -> builder.getController().onHidden());

    scene.getWindow().setUserData(builder.getController());
  }

  static WindowBuilder builder(Class<? extends WindowController> controllerClass) {
    var parent = (Parent) WindowManager.fxWeaver.loadView(controllerClass, I18nUtils.getBundle());
    parent.setId(getWindowClassName(controllerClass));
    return new WindowBuilder(parent, WindowManager.fxWeaver.getBean(controllerClass));
  }

  private static String getWindowClassName(Class<? extends WindowController> controllerClass) {
    assert controllerClass.getSimpleName().endsWith("WindowController");
    return controllerClass.getSimpleName().replace("WindowController", "");
  }

  public void show() {
    stage.show();
  }
}
