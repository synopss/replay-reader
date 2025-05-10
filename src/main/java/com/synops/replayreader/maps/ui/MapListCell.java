package com.synops.replayreader.maps.ui;

import static com.synops.replayreader.common.util.Constants.OVERALL;

import com.synops.replayreader.common.comparator.PlayerAndVehicleAndMapToInt;
import com.synops.replayreader.common.i18n.I18nUtils;
import com.synops.replayreader.ui.control.BaseListCell;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.scene.text.TextFlow;

public class MapListCell extends BaseListCell<String> {

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
  protected TextFlow createTextFlow(String item) {
    var vehicleValue = vehicle.get();
    if (OVERALL.equals(vehicleValue)) {
      vehicleValue = null;
    }

    var textFlow = new TextFlow();
    var textMap = createRegularText(getMapName(item) + " ");
    int count = function.apply(player.get(), vehicleValue, item);
    var textCount = createCountText(count);
    textFlow.getChildren().addAll(textMap, textCount);

    return textFlow;
  }

  @Override
  protected String createToolTipText(String item) {
    var vehicleValue = vehicle.get();
    if (OVERALL.equals(vehicleValue)) {
      vehicleValue = null;
    }

    int count = function.apply(player.get(), vehicleValue, item);
    return getMapName(item) + " (" + count + ")";
  }

  private String getMapName(String map) {
    try {
      return resourceBundle.getString(map);
    } catch (MissingResourceException exception) {
      return map;
    }
  }
}
