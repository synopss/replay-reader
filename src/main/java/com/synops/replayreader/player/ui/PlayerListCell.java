package com.synops.replayreader.player.ui;

import com.synops.replayreader.common.comparator.PlayerAndVehicleToInt;
import com.synops.replayreader.player.model.Player;
import java.util.function.Function;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class PlayerListCell extends ListCell<String> {

  private static final double CELL_HEIGHT = 20.0;

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
      var clanText = playerInfoFunction.apply(item).getClanAbbrev();

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
      textFlow.setPrefHeight(CELL_HEIGHT);
      textFlow.setMinHeight(CELL_HEIGHT);
      textFlow.setMaxHeight(CELL_HEIGHT);
      setGraphic(textFlow);
      setTooltip(new Tooltip(createTooltippText(playerText, clanText, overallBattleCount)));
    } else {
      setGraphic(null);
    }

    setText(null);
  }

  private String createTooltippText(String playerText, String clan, int overallBattleCount) {
    return String.format("%s%s(%d)", playerText, clan, overallBattleCount);
  }
}
