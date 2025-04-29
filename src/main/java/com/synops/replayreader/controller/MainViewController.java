package com.synops.replayreader.controller;

import com.synops.replayreader.comparator.PlayerListComparatorLong;
import com.synops.replayreader.comparator.SortingComparators;
import com.synops.replayreader.model.MainModel;
import com.synops.replayreader.model.PlayerSort;
import com.synops.replayreader.service.ReplayService;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainViewController {

  private final ReplayService replayService;
  private final ResourceBundle resourceBundle;
  private final StringProperty selectedClan = new SimpleStringProperty("");
  @Value("${replay-reader.application.config.max-players}")
  private int MAX_PLAYERS;
  @Value("${replay-reader.application.config.max-clans}")
  private int MAX_CLANS;
  @Value("${replay-reader.application.config.clan-all}")
  private String CLAN_ALL;
  @FXML
  private AnchorPane root;
  @FXML
  private ChoiceBox<String> sortingChoiceBox;
  @FXML
  private ChoiceBox<String> clanChoiceBox;
  @FXML
  private HBox hbox;
  @FXML
  private ListView<String> playersList;
  private Comparator<String> playersComparator;

  private MainModel mainModel;

  public MainViewController(ReplayService replayService, ResourceBundle resourceBundle) {
    this.replayService = replayService;
    this.resourceBundle = resourceBundle;
  }

  @FXML
  public void initialize() {
    initMainModel();
    hbox.prefHeightProperty().bind(root.heightProperty());
    initSortingChoiceBox();
    initClanChoiceBox();
  }

  @FXML
  private void onMenuExit() {
    Platform.exit();
  }

  @FXML
  private void onClickAbout() {
    (new AboutWindowController(resourceBundle)).showAndWait();
  }

  private void initSortingChoiceBox() {
    var sortingList = new SortedList<>(FXCollections.observableArrayList(
        Stream.of(PlayerSort.values()).map(PlayerSort::getResourceBundleName)
            .collect(Collectors.toList())));
    sortingChoiceBox.setItems(sortingList);
    sortingChoiceBox.getSelectionModel().select(PlayerSort.GAMES.getResourceBundleName());
    sortingChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::onSortingChanged);
    sortingChoiceBox.setTooltip(new Tooltip(
        String.format(resourceBundle.getString("main.toolbar.sort.tooltip"), MAX_PLAYERS)));

    playersComparator = new PlayerListComparatorLong(replayService::getNumberOfGames);
  }

  private void initClanChoiceBox() {
    clanChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::onClanChanged);
    clanChoiceBox.setTooltip(new Tooltip(
        String.format(resourceBundle.getString("main.toolbar.filter-clan.tooltip"), MAX_CLANS)));
    selectedClan.bind(clanChoiceBox.getSelectionModel().selectedItemProperty());
  }

  private void onSortingChanged(ObservableValue<? extends String> player, String oldValue,
      String newValue) {
    if (getMainModel().getPlayersData() != null) {
      playersComparator = (new SortingComparators(replayService)).getComparator(
          PlayerSort.of(newValue));
      setTextFieldColor(PlayerSort.of(newValue));
      updatePlayers();
    }

    playersList.getSelectionModel().selectFirst();
  }

  private void updatePlayers() {
    var playersRawData = replayService.getPlayers();
    FXCollections.sort(playersRawData, playersComparator);
    var filteredPlayersData = new FilteredList<>(playersRawData);
    var selectedClanValue = selectedClan.getValue();
    if (selectedClanValue != null) {
      filteredPlayersData.setPredicate(
          (player) -> selectedClanValue.equals(CLAN_ALL) || selectedClanValue.equals(
              replayService.getPlayerInfo(player).getClanAbbreviation()));
    }

    var newPlayersData = FXCollections.observableArrayList(filteredPlayersData);
    int size = newPlayersData.size();
    if (size > MAX_PLAYERS) {
      newPlayersData.removeAll(newPlayersData.subList(MAX_PLAYERS, size));
    }

    getMainModel().setPlayersData(newPlayersData);
    playersList.setItems(getMainModel().getPlayersData());
    playersList.getSelectionModel().select(0);
    playersList.scrollTo(0);
  }

  private void onClanChanged(ObservableValue<? extends String> player, String oldValue,
      String newValue) {
    if (oldValue != null) {
      updatePlayers();
    }

    playersList.getSelectionModel().selectFirst();
  }

  private MainModel getMainModel() {
    return mainModel;
  }

  private void initMainModel() {
    mainModel = new MainModel();
  }

  private void setTextFieldColor(PlayerSort playerSort) {
    for (var sort : PlayerSort.values()) {
      var element = root.getScene().lookup("#" + sort.getElementId());
      if (element != null) {
        element.setStyle("");
      }
    }

    var lookup = root.getScene().lookup("#" + playerSort.getElementId());
    if (lookup != null) {
      lookup.setStyle("-fx-border-color: red");
    }
  }
}
