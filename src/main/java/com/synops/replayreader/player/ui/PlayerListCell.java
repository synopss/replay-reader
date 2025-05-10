package com.synops.replayreader.player.ui;

import com.synops.replayreader.common.comparator.PlayerAndVehicleToInt;
import com.synops.replayreader.ui.control.BaseListCell;
import com.synops.replayreader.player.model.Player;
import java.util.function.Function;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PlayerListCell extends BaseListCell<String> {

  private final PlayerAndVehicleToInt battleCountFunction;
  private final Function<String, Player> playerInfoFunction;

  public PlayerListCell(PlayerAndVehicleToInt function,
      Function<String, Player> playerInfoFunction) {
    this.battleCountFunction = function;
    this.playerInfoFunction = playerInfoFunction;
  }

  @Override
  protected TextFlow createTextFlow(String item) {
    var textFlow = new TextFlow();
    var textPlayer = createRegularText(item + " ");

    var clanText = playerInfoFunction.apply(item).getClanAbbrev();
    if (!clanText.isEmpty()) {
      clanText = "[" + clanText + "]";
    } else {
      clanText = " ";
    }

    var textClan = new Text(clanText);
    textClan.setFill(Color.GRAY);

    int overallBattleCount = battleCountFunction.apply(item, null);
    var textMatches = createCountText(overallBattleCount);

    textFlow.getChildren().addAll(textPlayer, textClan, textMatches);
    return textFlow;
  }

  @Override
  protected String createToolTipText(String item) {
    var playerText = item + " ";
    var clanText = playerInfoFunction.apply(item).getClanAbbrev();
    if (!clanText.isEmpty()) {
      clanText = "[" + clanText + "]";
    } else {
      clanText = "";
    }

    int overallBattleCount = battleCountFunction.apply(item, null);
    return String.format("%s%s (%d)", playerText, clanText, overallBattleCount);
  }
}
