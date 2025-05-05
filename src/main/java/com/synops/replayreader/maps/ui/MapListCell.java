package com.synops.replayreader.maps.ui;

import static com.synops.replayreader.common.util.Constants.OVERALL;

import com.synops.replayreader.common.i18n.I18nUtils;
import com.synops.replayreader.common.comparator.PlayerAndVehicleAndMapToInt;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MapListCell extends ListCell<String> {

  private final ResourceBundle resourceBundle = I18nUtils.getBundle();
  private final PlayerAndVehicleAndMapToInt function;
  private final StringProperty player;
  private final StringProperty vehicle;

  public MapListCell(StringProperty player, StringProperty vehicle,
      PlayerAndVehicleAndMapToInt function) {
    this.function = function;
    this.player = player;
    this.vehicle = vehicle;
  }

  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (!empty && item != null) {
      var vehicleValue = vehicle.get();
      if (OVERALL.equals(vehicleValue)) {
        vehicleValue = null;
      }

      var textFlow = new TextFlow();
      var textMap = new Text();
      textMap.setText(getMapName(item) + " ");
      var textCount = new Text();
      textCount.setText(String.valueOf(function.apply(player.get(), vehicleValue, item)));
      textCount.setFill(Color.ORANGE);
      textFlow.getChildren().addAll(textMap, textCount);
      this.setGraphic(textFlow);
      this.setTooltip(new Tooltip(this.createTooltippText(item, vehicleValue)));
    } else {
      this.setGraphic(null);
    }

    this.setText(null);
  }

  private String createTooltippText(String map, String vehicle) {
    return getMapName(map) + " (" + function.apply(player.get(), vehicle, map) + ")";
  }

  private String getMapName(String map) {
    try {
      return resourceBundle.getString(map);
    } catch (MissingResourceException exception) {
      return map;
    }
  }
}
