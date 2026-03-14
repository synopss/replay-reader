package com.synops.replayreader.maps.util;

import static com.synops.replayreader.common.util.Constants.MAP_ALL;

import com.synops.replayreader.common.i18n.I18nUtils;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.util.StringConverter;
import org.springframework.stereotype.Component;

@Component
public class MapStringConverter extends StringConverter<String> {

  private final ResourceBundle resourceBundle = I18nUtils.getBundle();
  private Function<String, Integer> function;
  private int totalGamesCount;

  public MapStringConverter() {
  }

  public void configure(Function<String, Integer> function, int totalGamesCount) {
    this.function = function;
    this.totalGamesCount = totalGamesCount;
  }

  @Override
  public String toString(String mapString) {
    if (mapString == null) {
      return null;
    }

    if (MAP_ALL.equals(mapString)) {
      return String.format("%s (%d)", mapString, totalGamesCount);
    }

    int gamesCount = function.apply(mapString);
    String mapName = getMapName(mapString);

    return String.format("%s (%d)", mapName, gamesCount);
  }

  @Override
  public String fromString(String mapString) {
    return mapString;
  }

  private String getMapName(String map) {
    try {
      return resourceBundle.getString(map);
    } catch (MissingResourceException exception) {
      return map;
    }
  }
}