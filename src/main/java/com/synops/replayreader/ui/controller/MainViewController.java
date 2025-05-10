package com.synops.replayreader.ui.controller;

import static com.synops.replayreader.common.util.Constants.CLAN_ALL;
import static com.synops.replayreader.common.util.Constants.OVERALL;

import com.synops.replayreader.clan.comparator.ClanListComparator;
import com.synops.replayreader.clan.util.ClanStringConverter;
import com.synops.replayreader.common.comparator.SortingComparators;
import com.synops.replayreader.core.event.ReplayProgressEvent;
import com.synops.replayreader.core.service.DialogService;
import com.synops.replayreader.core.service.NotificationService;
import com.synops.replayreader.maps.comparator.MapsComparator;
import com.synops.replayreader.maps.ui.MapListCell;
import com.synops.replayreader.player.comparator.PlayerListComparatorLong;
import com.synops.replayreader.player.model.PlayerSort;
import com.synops.replayreader.player.ui.PlayerListCell;
import com.synops.replayreader.replay.service.ReplayService;
import com.synops.replayreader.ui.model.MainModel;
import com.synops.replayreader.ui.util.DragDropSupport;
import com.synops.replayreader.update.UpdateClient;
import com.synops.replayreader.update.VersionChecker;
import com.synops.replayreader.vehicle.ui.VehicleListCell;
import com.synops.replayreader.vehicle.util.TanksUtil;
import java.io.File;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.HostServices;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MainViewController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainViewController.class);
  private static final String REPLAY_READER_BUGS_URL = "https://github.com/synopss/replay-reader/issues/new/choose";
  private static final String REPLAY_RELEASES_URL = "https://github.com/synopss/replay-reader/releases/latest";
  private final ReplayService replayService;
  private final HostServices hostServices;
  private final DialogService dialogService;
  private final UpdateClient updateClient;
  private final ResourceBundle resourceBundle;
  private final ClanStringConverter clanStringConverter;
  private final ClanListComparator clanListComparator;
  private final SortingComparators sortingComparators;
  private final MapsComparator mapsComparator;
  private final DragDropSupport dragDropSupport;
  private final StringProperty selectedPlayer = new SimpleStringProperty("");
  private final StringProperty selectedVehicle = new SimpleStringProperty("");
  private final StringProperty selectedClan = new SimpleStringProperty("");
  private final BuildProperties buildProperties;
  private final NotificationService notificationService;
  @Value("${replay-reader.config.max-players}")
  private int MAX_PLAYERS;
  @Value("${replay-reader.config.max-clans}")
  private int MAX_CLANS;
  @FXML
  private StackPane root;
  @FXML
  private ChoiceBox<String> sortingChoiceBox;
  @FXML
  private ChoiceBox<String> clanChoiceBox;
  @FXML
  private HBox hbox;
  @FXML
  private MenuItem exitApplication;
  @FXML
  private MenuItem reportBug;
  @FXML
  private MenuItem showAboutWindow;
  @FXML
  private MenuItem versionCheck;
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
  private VersionChecker versionChecker;

  public MainViewController(ReplayService replayService, @Nullable HostServices hostServices,
      DialogService dialogService, UpdateClient updateClient, ResourceBundle resourceBundle,
      ClanStringConverter clanStringConverter, ClanListComparator clanListComparator,
      SortingComparators sortingComparators, MapsComparator mapsComparator,
      DragDropSupport dragDropSupport, BuildProperties buildProperties,
      NotificationService notificationService) {
    this.replayService = replayService;
    this.hostServices = hostServices;
    this.dialogService = dialogService;
    this.updateClient = updateClient;
    this.resourceBundle = resourceBundle;
    this.clanStringConverter = clanStringConverter;
    this.clanListComparator = clanListComparator;
    this.sortingComparators = sortingComparators;
    this.mapsComparator = mapsComparator;
    this.dragDropSupport = dragDropSupport;
    this.buildProperties = buildProperties;
    this.notificationService = notificationService;
  }

  @FXML
  public void initialize() {
    initMainModel();
    hbox.prefHeightProperty().bind(root.heightProperty());
    initMenu();
    initLists();
    initDragDrop();
    initSortingChoiceBox();
    initClanChoiceBox();
    bindingSelectedElements();
    progressBar.setVisible(false);
    versionChecker = new VersionChecker();
    versionChecker.scheduleVersionCheck(this::checkForUpdateInBackground);
  }

  private void initMenu() {
    exitApplication.setOnAction(_ -> Platform.exit());
    reportBug.setOnAction(_ -> openUrl(REPLAY_READER_BUGS_URL));
    showAboutWindow.setOnAction(_ -> (new AboutWindowController(resourceBundle)).showAndWait());
    versionCheck.setOnAction(_ -> checkForUpdate());
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
    selectedVehicle.bind(vehiclesList.getSelectionModel().selectedItemProperty());
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
    if (newValue == null) {
      return;
    }

    String vehicle = null;
    if (!OVERALL.equals(newValue)) {
      vehicle = newValue;
    }

    textVehicle.setText(String.format("%s (%d)", TanksUtil.getTankName(selectedVehicle.get()),
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
    LOGGER.debug("onLoadSucceeded before SelectionModel changes");
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

  private void openUrl(String url) {
    if (hostServices == null) {
      return;
    }
    hostServices.showDocument(url);
  }

  private void checkForUpdate() {
    updateClient.getLatestVersion().doOnSuccess(versionResponse -> Platform.runLater(() -> {
      if (versionChecker.isNewVersionAvailable(versionResponse.tagName(),
          buildProperties.getVersion())) {
        dialogService.alertConfirm(
            MessageFormat.format(resourceBundle.getString("update.new-version"),
                versionResponse.tagName().substring(1)), () -> openUrl(REPLAY_RELEASES_URL));
      } else {
        notificationService.information(resourceBundle.getString("update.latest-already"), root);
      }
    })).doOnError(dialogService::showAlertError).subscribe();
  }

  private void checkForUpdateInBackground() {
    LOGGER.info("Checking for update in background");
    updateClient.getLatestVersion().doOnSuccess(versionResponse -> Platform.runLater(() -> {
      if (versionChecker.isNewVersionAvailable(versionResponse.tagName(),
          buildProperties.getVersion())) {
        dialogService.alertConfirm(
            MessageFormat.format(resourceBundle.getString("update.new-version"),
                versionResponse.tagName().substring(1)), () -> openUrl(REPLAY_RELEASES_URL));
      }
    })).doOnError(dialogService::showAlertError).subscribe();;
  }
}
