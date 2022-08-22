package cz.zcu.fav.kiv.ups.sp.client.ui.controller;

import cz.zcu.fav.kiv.ups.sp.client.net.dto.RequestPrefix;
import cz.zcu.fav.kiv.ups.sp.client.net.dto.ResponseMessage;
import cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.AbstractController;
import cz.zcu.fav.kiv.ups.sp.client.ui.controller.abstracts.IGameEvent;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.QuizLabel;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.ResultLabel;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.animated.ConnectionLostLabel;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.label.animated.InactiveOpponentLabel;
import cz.zcu.fav.kiv.ups.sp.client.ui.element.text.ColorText;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static cz.zcu.fav.kiv.ups.sp.client.net.manager.NetManager.REGEX_DELIMITER;

/**
 * @author honza on 05.01.2022
 * @version 0.0.0
 * @project UPS-client
 */
public class GameController extends AbstractController implements IGameEvent {

    private final static Logger logger = LogManager.getLogger(GameController.class);

    @FXML
    private InactiveOpponentLabel opponentLostConnectionLabel;
    @FXML
    private ConnectionLostLabel lostConnectionLabel;
    @FXML
    private BorderPane root;
    @FXML
    private Label nextGameLabel;
    @FXML
    private HBox resultBox2;
    @FXML
    private HBox resultBox1;
    @FXML
    private Label question;
    @FXML
    private QuizLabel answer1;
    @FXML
    private QuizLabel answer2;
    @FXML
    private QuizLabel answer3;
    @FXML
    private Label infoLabel;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button nextGameButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetWindow();
    }

    @Override
    public void resetWindow() {
        this.nextGameButton.setVisible(false);
        this.nextGameButton.setManaged(false);
        this.nextGameButton.setOnAction(event -> NetManager.sendMessage(RequestPrefix.GAME_CLOSE));

        this.disconnectButton.setVisible(false);
        this.disconnectButton.setManaged(false);
        this.disconnectButton.setOnAction(event -> NetManager.sendMessage(RequestPrefix.DISCONNECT));

        this.nextGameLabel.setVisible(false);
        this.nextGameLabel.setManaged(false);

        this.resultBox1.setVisible(false);
        this.resultBox1.setManaged(false);

        this.resultBox2.setVisible(false);
        this.resultBox2.setManaged(false);

        this.lostConnectionLabel.setVisible(false);
        this.lostConnectionLabel.setManaged(false);

        this.opponentLostConnectionLabel.setVisible(false);
        this.opponentLostConnectionLabel.setManaged(false);
    }

    @Override
    public void onGameStart(ResponseMessage responseMessage) {
        String opponentName = responseMessage.getContentMessage();
        this.infoLabel.setGraphic(new ColorText("Your opponent is: " + opponentName, Color.BLUE));
    }

    @Override
    public void onGameEnd(ResponseMessage responseMessage) {
        String message = responseMessage.getContentMessage();
        String[] messagePartArray = message.split(REGEX_DELIMITER);

        if (messagePartArray.length != 3) {
            logger.warn("Receive invalid GAME_END message: \"" + responseMessage + "\". This message will be ignored!");
            return;
        }

        String status = messagePartArray[0];
        String resultString1 = messagePartArray[1];
        String resultString2 = messagePartArray[2];

        this.nextGameLabel.setVisible(true);
        this.nextGameLabel.setManaged(true);

        this.disconnectButton.setVisible(true);
        this.disconnectButton.setManaged(true);

        this.nextGameButton.setVisible(true);
        this.nextGameButton.setManaged(true);

        resolveGameEndStatus(status);
        resolveGameEndResult(resultString1, this.resultBox1, "You");
        resolveGameEndResult(resultString2, this.resultBox2, "Opponent");
    }

    private void resolveGameEndStatus(String status) {
        switch (status) {
            case "win":
                this.infoLabel.setGraphic(new ColorText("You won!", Color.GREEN));
                break;
            case "lose":
                this.infoLabel.setGraphic(new ColorText("You lost!", Color.RED));
                break;
            case "draw":
                this.infoLabel.setGraphic(new ColorText("It is a draw!", Color.BLUE));
                break;
        }
    }

    private void resolveGameEndResult(String resultString, HBox box, String player) {
        box.getChildren().clear();
        box.setVisible(true);
        box.setManaged(true);

        Label label = new Label(player + ": ");
        box.getChildren().add(label);

        char[] resultArray = resultString.toCharArray();
        for (char result : resultArray) {
            ResultLabel resultLabel = new ResultLabel(result == '1');
            box.getChildren().add(resultLabel);
        }
    }

    @Override
    public void onRoundStart(ResponseMessage responseMessage) {
        String message = responseMessage.getContentMessage();
        String[] messagePartArray = message.split(REGEX_DELIMITER);

        if (messagePartArray.length != 4) {
            logger.warn("Receive invalid round start message: \"" + responseMessage + "\". This message will be ignored!");
            return;
        }
        String question = messagePartArray[0];
        this.question.setText(question);

        String rightAnswer = messagePartArray[1];

        List<String> shuffleAnswerList = Arrays.asList(messagePartArray[1], messagePartArray[2], messagePartArray[3]);
        List<QuizLabel> quizLabelList = Arrays.asList(answer1, answer2, answer3);

        quizLabelList.forEach(q -> q.setDisable(false));
        quizLabelList.forEach(q -> q.setBackground(null));

        Collections.shuffle(shuffleAnswerList);


        for (int i = 0; i < shuffleAnswerList.size() && i < quizLabelList.size(); i++) {
            String text = shuffleAnswerList.get(i);
            boolean right = rightAnswer.equals(text);

            QuizLabel quizLabel = quizLabelList.get(i);
            quizLabel.setText(text);
            quizLabel.setRight(right);
            quizLabel.setOnMouseClicked(event -> {
                NetManager.sendMessage(RequestPrefix.PLAY_ROUND, quizLabel.isRight() ? "1" : "-1");
                if (quizLabel.isRight()) {
                    quizLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(3), Insets.EMPTY)));
                } else {
                    quizLabel.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(3), Insets.EMPTY)));
                }

                quizLabelList.forEach(q -> q.setDisable(true));
            });

        }
    }

    @Override
    public void onOpponentConnectionLost() {
        this.opponentLostConnectionLabel.setVisible(true);
        this.opponentLostConnectionLabel.setManaged(true);
        this.opponentLostConnectionLabel.start();
    }

    @Override
    public void onOpponentReconnect() {
        this.opponentLostConnectionLabel.setVisible(false);
        this.opponentLostConnectionLabel.setManaged(false);
        this.opponentLostConnectionLabel.stop();
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
