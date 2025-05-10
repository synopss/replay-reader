package com.synops.replayreader.ui.control;

import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public abstract class BaseListCell<T> extends ListCell<T> {

  protected static final double CELL_HEIGHT = 20.0;
  protected static Color COUNT_COLOR = Color.ORANGE;

  @Override
  protected void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);

    if (empty || item == null) {
      setGraphic(null);
      setTooltip(null);
      setText(null);
      return;
    }

    var textFlow = createTextFlow(item);
    textFlow.setPrefHeight(CELL_HEIGHT);
    textFlow.setMinHeight(CELL_HEIGHT);
    textFlow.setMaxHeight(CELL_HEIGHT);
    setGraphic(textFlow);

    var tooltipText = createToolTipText(item);
    if (tooltipText != null && !tooltipText.isEmpty()) {
      setTooltip(new Tooltip(tooltipText));
    } else {
      setTooltip(null);
    }

    setText(null);
  }

  /**
   * Creates the TextFlow component to display the item.
   *
   * @param item The item to display
   * @return TextFlow with formatted content
   */
  protected abstract TextFlow createTextFlow(T item);

  /**
   * Creates the tooltip text for the item.
   *
   * @param item The item to create a tooltip for
   * @return Tooltip text or null if no tooltip should be shown
   */
  protected String createToolTipText(T item) {
    return null;
  }

  /**
   * Helper method to create a count text with standard formatting.
   *
   * @param count The count value to display
   * @return Formatted Text component
   */
  protected Text createCountText(int count) {
    var countText = new Text(String.valueOf(count));
    countText.setFill(COUNT_COLOR);
    return countText;
  }

  /**
   * Helper method to create a regular text with a default color.
   *
   * @param content The text content
   * @return Formatted Text component
   */
  protected Text createRegularText(String content) {
    return new Text(content);
  }
}
