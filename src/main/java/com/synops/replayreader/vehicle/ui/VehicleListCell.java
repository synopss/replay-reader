package com.synops.replayreader.vehicle.ui;

import static com.synops.replayreader.common.util.Constants.OVERALL;

import com.synops.replayreader.common.comparator.PlayerAndVehicleToInt;
import com.synops.replayreader.vehicle.util.TanksUtil;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VehicleListCell extends ListCell<String> {

  private static final double CELL_HEIGHT = 20.0;

  private final PlayerAndVehicleToInt function;
  private final StringProperty selectedPlayer;

  public VehicleListCell(StringProperty selectedPlayer, PlayerAndVehicleToInt function) {
    this.function = function;
    this.selectedPlayer = selectedPlayer;
  }

  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    var textVehicle = new Text();
    var textMatches = new Text();
    if (!empty && item != null) {
      if (OVERALL.equals(item)) {
        textVehicle.setText(item + " ");
        textMatches.setText(String.valueOf(function.apply(selectedPlayer.get(), null)));
      } else {
        textVehicle.setText(TanksUtil.getTankName(item) + " ");
        textMatches.setText(String.valueOf(function.apply(selectedPlayer.get(), item)));
      }

      var textFlow = new TextFlow();
      textMatches.setFill(Color.ORANGE);
      textFlow.getChildren().addAll(textVehicle, textMatches);
      textFlow.setPrefHeight(CELL_HEIGHT);
      textFlow.setMinHeight(CELL_HEIGHT);
      textFlow.setMaxHeight(CELL_HEIGHT);
      setGraphic(textFlow);
    } else {
      setGraphic(null);
      setTooltip(null);
    }
    setText(null);
  }
}
