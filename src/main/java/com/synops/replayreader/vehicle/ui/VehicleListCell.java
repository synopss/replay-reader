package com.synops.replayreader.vehicle.ui;

import static com.synops.replayreader.common.util.Constants.OVERALL;

import com.synops.replayreader.common.comparator.PlayerAndVehicleToInt;
import com.synops.replayreader.ui.control.BaseListCell;
import com.synops.replayreader.vehicle.util.TanksUtil;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VehicleListCell extends BaseListCell<String> {

  private final PlayerAndVehicleToInt function;
  private final StringProperty selectedPlayer;

  public VehicleListCell(StringProperty selectedPlayer, PlayerAndVehicleToInt function) {
    this.function = function;
    this.selectedPlayer = selectedPlayer;
  }

  @Override
  protected TextFlow createTextFlow(String item) {
    var textFlow = new TextFlow();
    Text textVehicle;
    int matchCount;

    if (OVERALL.equals(item)) {
      textVehicle = createRegularText(item + " ");
      matchCount = function.apply(selectedPlayer.get(), null);
    } else {
      textVehicle = createRegularText(TanksUtil.getTankName(item) + " ");
      matchCount = function.apply(selectedPlayer.get(), item);
    }

    var textMatches = createCountText(matchCount);
    textFlow.getChildren().addAll(textVehicle, textMatches);

    return textFlow;
  }
}
