package cz.zcu.fav.kiv.ups.sp.client.ui.controller;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.AbstractController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.ILobbyEvent;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.text.ColorText;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.RoomLabel;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.animated.WaitingLabel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager.REGEX_DELIMITER;

/**
 * @author honza on 05.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class LobbyController extends AbstractController implements ILobbyEvent {

    private final static Logger logger = LogManager.getLogger(LobbyController.class);

    @FXML
    private WaitingLabel lostConnectionLabel;
    @FXML
    private Label lobbyStatusLabel;
    @FXML
    private Label userStatusLabel;
    @FXML
    private VBox lobbyListRoot;
    @FXML
    private TextField lobbyNameField;

    @FXML
    private Button createLobbyButton;
    @FXML
    private Button joinLobbyButton;
    @FXML
    private Button backButton;

    /**
     * Signalize if user is located in decision menu [create/join lobby button].
     * If variable is set on true and one of create/join lobby buttons will be clicked, window will redirect to create/join lobby.
     * If variable is set on false and one of create/join lobby buttons will be clicked, window will proceed create/join task [create lobby or join lobby to server].
     */
    private boolean isInLobby;

    private ScheduledFuture<?> lobbyListScheduleFuture;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetWindow();
    }

    @Override
    public void resetWindow() {
        this.isInLobby = true;

        this.lobbyListRoot.setVisible(false);
        this.lobbyListRoot.setManaged(false);
        this.lobbyListRoot.getChildren().clear();

        this.joinLobbyButton.setVisible(true);
        this.joinLobbyButton.setManaged(true);
        this.joinLobbyButton.setDisable(false);

        this.createLobbyButton.setVisible(true);
        this.createLobbyButton.setManaged(true);
        this.createLobbyButton.setDisable(false);

        this.lobbyNameField.setVisible(false);
        this.lobbyNameField.setManaged(false);
        this.lobbyNameField.setDisable(false);

        this.lobbyStatusLabel.setVisible(false);
        this.lobbyStatusLabel.setManaged(false);
        this.lobbyStatusLabel.setGraphic(null);
        this.lobbyStatusLabel.setText("");

        this.userStatusLabel.setVisible(true);
        this.userStatusLabel.setManaged(true);
        this.userStatusLabel.setGraphic(new ColorText(String.format("Successfully logged in as \"%s\"", LoginController.getUsername()), Color.GREEN));
        this.userStatusLabel.setText("");

        this.backButton.setVisible(true);
        this.backButton.setManaged(true);
        this.backButton.setDisable(false);

        this.lostConnectionLabel.setVisible(false);
        this.lostConnectionLabel.setManaged(false);

        this.cancelJoinLobbyListScheduler();
    }

    @FXML
    public void reset() {
        resetWindow();
    }

    /**
     * FXML action listener for {@link #createLobbyButton}. Set window to create lobby appearance.
     */
    @FXML
    public void createLobby() {
        if (!isInLobby) {
            NetManager.sendMessage(RequestPrefix.LOBBY_CREATE, this.lobbyNameField.getText());
        }
        this.isInLobby = false;

        this.joinLobbyButton.setVisible(false);
        this.joinLobbyButton.setManaged(false);

        this.lobbyNameField.setVisible(true);
        this.lobbyNameField.setManaged(true);

        this.lobbyStatusLabel.setVisible(true);
        this.lobbyStatusLabel.setManaged(true);

        this.cancelJoinLobbyListScheduler();
    }

    /**
     * FXML action listener for {@link #joinLobbyButton}. Set window to join lobby appearance.
     * If user is located in decision menu [create/join lobby button], it will start scheduler to update immediately the room list.
     * Otherwise, the {@link #joinLobbyButton} listener will not be shown.
     */
    @FXML
    public void joinLobby() {
        this.initJoinLobbyListScheduler();

        this.isInLobby = false;

        this.lobbyListRoot.setVisible(true);
        this.lobbyListRoot.setManaged(true);

        this.joinLobbyButton.setVisible(false);
        this.joinLobbyButton.setManaged(false);

        this.createLobbyButton.setVisible(false);
        this.createLobbyButton.setManaged(false);

        this.lobbyStatusLabel.setVisible(true);
        this.lobbyStatusLabel.setManaged(true);
    }

    /**
     * Start lobby list scheduler [updating lobby list].
     */
    private void initJoinLobbyListScheduler() {
        this.lobbyListScheduleFuture = Executors
                .newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(
                        () -> NetManager.sendMessage(RequestPrefix.LOBBY_LIST),
                        0,
                        300,
                        TimeUnit.MILLISECONDS);
    }

    /**
     * Cancel lobby list scheduler [updating lobby list].
     */
    private void cancelJoinLobbyListScheduler() {
        if (this.lobbyListScheduleFuture != null) {
            this.lobbyListScheduleFuture.cancel(false);
        }
    }


    @Override
    public void onRoomListSuccess(ResponseMessage responseMessage) {
        String message = responseMessage.getContentMessage();
        String[] lobbyArray = message.split(REGEX_DELIMITER);
        if (lobbyArray.length == 0 || message.trim().equals("")) {
            this.resolveEmptyRoomList();
        } else {
            this.resolveRoomList(lobbyArray);
        }
    }

    /**
     * Print information label with "No lobby available.".
     */
    private void resolveEmptyRoomList() {
        ColorText emptyLobbyListText = new ColorText("No lobby available.", Color.BLUE);
        this.lobbyStatusLabel.setVisible(true);
        this.lobbyStatusLabel.setManaged(true);
        this.lobbyStatusLabel.setGraphic(emptyLobbyListText);

        this.lobbyListRoot.getChildren().clear();
    }

    /**
     * Create room list in {@link #lobbyListRoot} according to "lobbyNames".
     *
     * @param lobbyNames Array of available room names.
     */
    private void resolveRoomList(String[] lobbyNames) {
        this.lobbyStatusLabel.setVisible(false);
        this.lobbyStatusLabel.setManaged(false);

        this.lobbyListRoot.getChildren().clear();
        for (String lobbyName : lobbyNames) {
            RoomLabel lobbyLabel = new RoomLabel(lobbyName);

            this.lobbyListRoot.getChildren().add(lobbyLabel);
            VBox.setMargin(lobbyLabel, new Insets(5, 10, 5, 10));
        }
    }

    @Override
    public void onRoomListError(ResponseMessage responseMessage) {
        this.lobbyStatusLabel.setGraphic(new ColorText("Lobby list could not be updated. Please, try it again later!", Color.RED));
    }

    @Override
    public void onLobbyCreateSuccess(ResponseMessage responseMessage) {
        this.lobbyStatusLabel.setGraphic(new ColorText("Lobby was successfully created. Waiting...", Color.BLUE));

        this.createLobbyButton.setDisable(true);
        this.backButton.setDisable(true);
        this.lobbyNameField.setDisable(true);
    }

    @Override
    public void onLobbyCreateError(ResponseMessage responseMessage) {
        this.lobbyStatusLabel.setGraphic(new ColorText("Lobby could not be created. Please, try it again later!", Color.RED));
    }

    @Override
    public void onLobbyJoinSuccess(ResponseMessage responseMessage) {
        this.lobbyStatusLabel.setGraphic(new ColorText("Lobby was successfully joined.", Color.BLUE));

        this.cancelJoinLobbyListScheduler();
    }

    @Override
    public void onLobbyJoinError(ResponseMessage responseMessage) {
        this.lobbyStatusLabel.setGraphic(new ColorText("Lobby could not be joined. Please, try it again later.", Color.RED));
    }

    @Override
    public void onConnectionLost() {
        this.lostConnectionLabel.setVisible(true);
        this.lostConnectionLabel.setManaged(true);
        this.lostConnectionLabel.start();
    }

    @Override
    public void onReconnect() {
        this.lostConnectionLabel.setVisible(false);
        this.lostConnectionLabel.setManaged(false);
        this.lostConnectionLabel.stop();
    }
}
