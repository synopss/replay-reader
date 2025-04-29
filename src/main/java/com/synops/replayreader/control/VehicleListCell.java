package com.synops.replayreader.control;

import com.synops.replayreader.comparator.PlayerAndVehicleToInt;
import com.synops.replayreader.util.TanksUtil;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.springframework.beans.factory.annotation.Value;

public class VehicleListCell extends ListCell<String> {

  private final PlayerAndVehicleToInt function;
  private final StringProperty selectedPlayer;
  @Value("${replay-reader.config.overall}")
  private String OVERALL;

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
        textMatches.setText(String.valueOf(this.function.apply(this.selectedPlayer.get(), null)));
      } else {
        textVehicle.setText(TanksUtil.getTankName(item) + " ");
        textMatches.setText(String.valueOf(this.function.apply(this.selectedPlayer.get(), item)));
      }

      var textFlow = new TextFlow();
      textMatches.setFill(Color.ORANGE);
      textFlow.getChildren().addAll(textVehicle, textMatches);
      this.setGraphic(textFlow);
      this.setText(null);
    } else {
      this.setGraphic(null);
      this.setText(null);
      this.setTooltip(null);
    }
  }
}
