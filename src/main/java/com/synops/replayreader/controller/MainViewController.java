package com.synops.replayreader.controller;

import static com.synops.replayreader.util.Constants.CLAN_ALL;
import static com.synops.replayreader.util.Constants.OVERALL;

import com.synops.replayreader.comparator.ClanListComparator;
import com.synops.replayreader.comparator.MapsComparator;
import com.synops.replayreader.comparator.PlayerListComparatorLong;
import com.synops.replayreader.comparator.SortingComparators;
import com.synops.replayreader.control.MapListCell;
import com.synops.replayreader.control.PlayerListCell;
import com.synops.replayreader.control.VehicleListCell;
import com.synops.replayreader.model.MainModel;
import com.synops.replayreader.model.PlayerSort;
import com.synops.replayreader.model.ReplayProgressEvent;
import com.synops.replayreader.service.ReplayService;
import com.synops.replayreader.util.ClanStringConverter;
import com.synops.replayreader.util.DragDropSupport;
import com.synops.replayreader.util.LogUtil;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainViewController {

  private final ReplayService replayService;
  private final ResourceBundle resourceBundle;
  private final ClanStringConverter clanStringConverter;
  private final ClanListComparator clanListComparator;
  private final SortingComparators sortingComparators;
  private final MapsComparator mapsComparator;
  private final DragDropSupport dragDropSupport;
  private final StringProperty selectedPlayer = new SimpleStringProperty("");
  private final StringProperty selectedVehicle = new SimpleStringProperty("");
  private final StringProperty selectedClan = new SimpleStringProperty("");
  @Value("${replay-reader.config.max-players}")
  private int MAX_PLAYERS;
  @Value("${replay-reader.config.max-clans}")
  private int MAX_CLANS;
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
  @FXML
  private ListView<String> vehiclesList;
  @FXML
  private Label textVehicle;
  @FXML
  private TextField textDmg;
  @FXML
  private TextField textKills;
  @FXML
  private TextField textPenRate;
  @FXML
  private TextField textHitRate;
  @FXML
  private TextField textAssist;
  @FXML
  private TextField textSpots;
  @FXML
  private TextField textBlocked;
  @FXML
  private TextField textLifeTime;
  @FXML
  private TextField textHitsReceived;
  @FXML
  private TextField textDmgReceived;
  @FXML
  private TextField textDmgReceivedInvisible;
  @FXML
  private TextField textTeamDmg;
  @FXML
  private TextField textTeamKills;
  @FXML
  private TextField textShots;
  @FXML
  private TextField textXP;
  @FXML
  private TextField textCredits;
  @FXML
  private TextField textMileage;
  @FXML
  private TextField textWinrate;
  @FXML
  private TextField textCap;
  @FXML
  private TextField textCapReset;
  @FXML
  private TextField textXPRank;
  @FXML
  private TextField textDamageRank;
  @FXML
  private ListView<String> mapsList;
  @FXML
  private ProgressBar progressBar;
  @FXML
  private Label progressLabel;
  private MainModel mainModel;

  public MainViewController(ReplayService replayService, ResourceBundle resourceBundle,
      ClanStringConverter clanStringConverter, ClanListComparator clanListComparator,
      SortingComparators sortingComparators, MapsComparator mapsComparator,
      DragDropSupport dragDropSupport) {
    this.replayService = replayService;
    this.resourceBundle = resourceBundle;
    this.clanStringConverter = clanStringConverter;
    this.clanListComparator = clanListComparator;
    this.sortingComparators = sortingComparators;
    this.mapsComparator = mapsComparator;
    this.dragDropSupport = dragDropSupport;
  }

  @FXML
  public void initialize() {
    initMainModel();
    hbox.prefHeightProperty().bind(root.heightProperty());
    initLists();
    initDragDrop();
    initSortingChoiceBox();
    initClanChoiceBox();
    bindingSelectedElements();
    progressBar.setVisible(false);
  }

  @FXML
  private void onMenuExit() {
    Platform.exit();
  }

  @FXML
  private void onClickAbout() {
    (new AboutWindowController(resourceBundle)).showAndWait();
  }

  private void initLists() {
    playersList.setCellFactory(
        (_) -> new PlayerListCell(replayService::getNumberOfGames, replayService::getPlayerInfo));
    playersList.getSelectionModel().selectedItemProperty().addListener(this::onSelectPlayer);
    vehiclesList.setCellFactory(
        (_) -> new VehicleListCell(selectedPlayer, replayService::getNumberOfGames));
    vehiclesList.getSelectionModel().selectedItemProperty().addListener(this::showVehicleStats);
    mapsList.setCellFactory((_) -> new MapListCell(selectedPlayer, selectedVehicle,
        replayService::getNumberOfMapsPlayed));
  }

  private void initDragDrop() {
    dragDropSupport.configure(this::load);
    root.sceneProperty().addListener((_, oldValue, newValue) -> {
      if (oldValue == null && newValue != null) {
        newValue.setOnDragOver(dragDropSupport::onDragOver);
        newValue.setOnDragDropped(dragDropSupport::onDragDropped);
      }
    });
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
  }

  private void bindingSelectedElements() {
    selectedPlayer.bind(playersList.getSelectionModel().selectedItemProperty());
    selectedVehicle.bind(this.vehiclesList.getSelectionModel().selectedItemProperty());
    selectedClan.bind(clanChoiceBox.getSelectionModel().selectedItemProperty());
  }

  private void onSelectPlayer(ObservableValue<? extends String> player, String oldValue,
      String newValue) {
    if (newValue != null) {
      var vehicles = replayService.getVehicles(newValue);
      vehiclesList.getItems().clear();
      vehicles.addFirst(OVERALL);
      vehiclesList.setItems(vehicles);
      vehiclesList.getSelectionModel().selectFirst();
    }
  }

  private void onSortingChanged(ObservableValue<? extends String> player, String oldValue,
      String newValue) {
    if (getMainModel().getPlayersData() != null) {
      sortingComparators.initMap();
      playersComparator = sortingComparators.getComparator(PlayerSort.of(newValue));
      setTextFieldColor(PlayerSort.of(newValue));
      updatePlayers();
    }
  }

  private void showVehicleStats(ObservableValue<? extends String> player, String oldValue,
      String newValue) {
    String vehicle = null;
    if (!OVERALL.equals(newValue)) {
      vehicle = newValue;
    }

    textVehicle.setText(String.format("%s (%d)", selectedVehicle.get(),
        replayService.getNumberOfGames(selectedPlayer.get(), vehicle)));
    textDmg.setText(
        String.format("%d (%d)", replayService.getDamageDealt(selectedPlayer.get(), vehicle),
            replayService.getAvgSniperDamageDealt(selectedPlayer.get(), vehicle)));
    textKills.setText(
        String.format("%.2f", replayService.getAvgKills(selectedPlayer.get(), vehicle)));
    textPenRate.setText(
        String.format("%.2f%%", 100 * replayService.getPenRate(selectedPlayer.get(), vehicle)));
    textHitRate.setText(
        String.format("%.2f%%", 100 * replayService.getHitRate(selectedPlayer.get(), vehicle)));
    var assistSpots = replayService.getAvgSpotAssist(selectedPlayer.get(), vehicle);
    var assistTracks = replayService.getAvgTrackAssist(selectedPlayer.get(), vehicle);
    textAssist.setText(
        String.format("%d (%d/%d)", assistSpots + assistTracks, assistSpots, assistTracks));
    textSpots.setText(
        String.format("%.2f", replayService.getAvgSpots(selectedPlayer.get(), vehicle)));
    textBlocked.setText(String.valueOf(replayService.getAvgBlocked(selectedPlayer.get(), vehicle)));
    var lifeTime = replayService.getAvgLifeTime(selectedPlayer.get(), vehicle);
    var min = lifeTime / 60;
    var sec = lifeTime % 60;
    textLifeTime.setText(String.format("%d:%02dmin", min, sec));
    textHitsReceived.setText(
        String.valueOf(replayService.getAvgHitsReceived(selectedPlayer.get(), vehicle)));
    textDmgReceived.setText(
        String.valueOf(replayService.getAvgDamageReceived(selectedPlayer.get(), vehicle)));
    textDmgReceivedInvisible.setText(String.valueOf(
        replayService.getAvgDamageReceivedFromInvisibles(selectedPlayer.get(), vehicle)));
    textTeamDmg.setText(
        String.valueOf(replayService.getAvgTdamageDealt(selectedPlayer.get(), vehicle)));
    textTeamKills.setText(
        String.format("%.2f", replayService.getAvgTKills(selectedPlayer.get(), vehicle)));
    textShots.setText(
        String.format("%.2f", replayService.getAvgShots(selectedPlayer.get(), vehicle)));
    textXP.setText(String.valueOf(replayService.getAvgXp(selectedPlayer.get(), vehicle)));
    textCredits.setText(String.valueOf(replayService.getAvgCredits(selectedPlayer.get(), vehicle)));
    textMileage.setText(
        String.format("%dm", replayService.getAvgMileage(selectedPlayer.get(), vehicle)));
    textWinrate.setText(
        String.format("%.2f%%", 100 * replayService.getWinrate(selectedPlayer.get(), vehicle)));
    textCap.setText(String.valueOf(replayService.getAvgCap(selectedPlayer.get(), vehicle)));
    textCapReset.setText(
        String.valueOf(replayService.getAvgCapReset(selectedPlayer.get(), vehicle)));
    textXPRank.setText(
        String.format("%.2f", replayService.getAvgXPRank(selectedPlayer.get(), vehicle)));
    textDamageRank.setText(
        String.format("%.2f", replayService.getAvgDamageRank(selectedPlayer.get(), vehicle)));
    var maps = replayService.getMaps(selectedPlayer.get(), vehicle);
    mapsComparator.configure(selectedPlayer, selectedVehicle, replayService::getNumberOfMapsPlayed);
    maps.sort(mapsComparator);
    mapsList.setItems(maps);
    mapsList.scrollTo(0);
  }

  private void load(final List<File> files) {
    progressBar.setVisible(true);
    final Consumer<ReplayProgressEvent> progress = this::onProgressChanged;
    var task = new Task<>() {
      protected Boolean call() {
        MainViewController.this.replayService.load(files, progress);
        return Boolean.TRUE;
      }
    };
    task.setOnRunning(_ -> {
    });
    progressBar.setProgress(0);
    task.setOnSucceeded(this::onLoadSucceeded);
    var thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  private void onProgressChanged(ReplayProgressEvent event) {
    Platform.runLater(() -> progressLabel.setText(
        String.format("%d/%d (%d %s)", event.getCount(), event.getTotal(), event.getCountCorrupt(),
            resourceBundle.getString("main.footer.corrupted"))));
    progressBar.setProgress((double) event.getCount() / (double) event.getTotal());
  }

  private void onLoadSucceeded(WorkerStateEvent event) {
    updatePlayers();
    updateClans();
    LogUtil.debug("onLoadSucceeded before selectionmodel changes");
    playersList.getSelectionModel().selectFirst();
    clanChoiceBox.getSelectionModel().selectFirst();
    sortingChoiceBox.getSelectionModel().selectFirst();
    progressBar.setVisible(false);
  }

  private void updatePlayers() {
    var playersRawData = replayService.getPlayers();
    FXCollections.sort(playersRawData, playersComparator);
    var filteredPlayersData = new FilteredList<>(playersRawData);
    var selectedClanValue = selectedClan.getValue();
    if (selectedClanValue != null) {
      filteredPlayersData.setPredicate(
          (player) -> selectedClanValue.equals(CLAN_ALL) || selectedClanValue.equals(
              replayService.getPlayerInfo(player).getClanAbbrev()));
    }

    var newPlayersData = FXCollections.observableArrayList(filteredPlayersData);
    int size = newPlayersData.size();
    if (size > MAX_PLAYERS) {
      newPlayersData.removeAll(newPlayersData.subList(MAX_PLAYERS, size));
    }

    getMainModel().setPlayersData(newPlayersData);
    playersList.getItems().clear();
    playersList.setItems(getMainModel().getPlayersData());
    playersList.getSelectionModel().selectFirst();
  }

  private void updateClans() {
    var newClansData = replayService.getClans();
    newClansData.addFirst(CLAN_ALL);
    var sortedClans = new SortedList<>(newClansData);
    clanListComparator.configure(replayService::getNumberOFClanPlayers);
    sortedClans.setComparator(clanListComparator);
    clanStringConverter.configure(replayService::getNumberOFClanPlayers,
        replayService.getPlayers().size());
    clanChoiceBox.setConverter(clanStringConverter);
    int size = newClansData.size();
    if (size > MAX_CLANS) {
      newClansData.removeAll(sortedClans.subList(MAX_CLANS, size - 1));
    }
    getMainModel().setClansData(newClansData);
    clanChoiceBox.setItems(sortedClans);
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

  private void setTextFieldColor(String playerSort) {
    for (var sort : PlayerSort.values()) {
      var element = root.getScene().lookup("#" + sort.getElementId());
      if (element != null) {
        element.setStyle("");
      }
    }

    var lookup = root.getScene().lookup("#" + playerSort);
    if (lookup != null) {
      lookup.setStyle("-fx-border-color: red");
    }
  }
}
