package com.synops.replayreader.control;

import com.synops.replayreader.comparator.PlayerAndVehicleToInt;
import com.synops.replayreader.model.Player;
import java.util.function.Function;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PlayerListCell extends ListCell<String> {

  private final PlayerAndVehicleToInt battleCountfunction;
  private final Function<String, Player> playerInfoFunction;

  public PlayerListCell(PlayerAndVehicleToInt function,
      Function<String, Player> playerInfoFunction) {
    this.battleCountfunction = function;
    this.playerInfoFunction = playerInfoFunction;
  }

  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (!empty && item != null) {
      var playerText = item + " ";
      var textPlayer = new Text(playerText);
      textPlayer.setFill(Color.BLACK);
      var clanText = playerInfoFunction.apply(item).getClanAbbreviation();

      if (!clanText.isEmpty()) {
        clanText = "[" + clanText + "]";
      } else {
        clanText = " ";
      }

      var textClan = new Text(clanText);
      textClan.setFill(Color.GRAY);
      var overallBattleCount = battleCountfunction.apply(item, null);
      var textMatches = new Text(String.valueOf(overallBattleCount));
      textMatches.setFill(Color.ORANGE);
      var textFlow = new TextFlow();
      textFlow.getChildren().addAll(textPlayer, textClan, textMatches);
      setGraphic(textFlow);
      setTooltip(new Tooltip(String.format("%s%s(%d)", playerText, clanText, overallBattleCount)));
    } else {
      setGraphic(null);
    }

    setText(null);
  }
}
