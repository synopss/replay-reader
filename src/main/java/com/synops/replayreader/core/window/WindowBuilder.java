package com.synops.replayreader.core.window;

import com.synops.replayreader.common.util.Constants;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowBuilder {

  private final Parent root;
  private final WindowController controller;
  private Window parent;
  private Stage stage;
  private String title = Constants.APP_NAME;
  private boolean resizeable = true;

  public WindowBuilder(Parent root, WindowController controller) {
    this.root = root;
    this.controller = controller;
  }

  /**
   * Sets a parent for the window, hence making it a modal window.
   *
   * @param parent the parent
   * @return the builder
   */
  public WindowBuilder setParent(Window parent) {
    this.parent = parent;
    return this;
  }

  /**
   * Sets a stage for the window. If not provided, a default stage will be created.
   *
   * @param stage the stage
   * @return the builder
   */
  public WindowBuilder setStage(Stage stage) {
    this.stage = stage;
    return this;
  }

  /**
   * Sets a title for the window that will be shown in the title bar.
   *
   * @param title the window title
   * @return the builder
   */
  public WindowBuilder setTitle(String title) {
    this.title = title;
    return this;
  }

  /**
   * Allows the window to be resized.
   *
   * @param resizeable true if resizeable, false if fixed (defaults to true)
   * @return the builder
   */
  public WindowBuilder setResizeable(boolean resizeable) {
    this.resizeable = resizeable;
    return this;
  }

  /**
   * Builds the ApplicationWindow.
   *
   * @return the ApplicationWindow
   */
  public ApplicationWindow build() {
    return new ApplicationWindow(this);
  }

  public Parent getRoot() {
    return root;
  }

  public WindowController getController() {
    return controller;
  }

  public Window getParent() {
    return parent;
  }

  public Stage getStage() {
    return stage;
  }

  public String getTitle() {
    return title;
  }

  public boolean isResizeable() {
    return resizeable;
  }
}
