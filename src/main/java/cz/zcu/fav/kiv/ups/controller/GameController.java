package cz.zcu.fav.kiv.ups.controller;

import cz.zcu.fav.kiv.ups.controller.abstracts.AbstractController;
import cz.zcu.fav.kiv.ups.game.TicTacToes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends AbstractController {

    private TicTacToes ticTacToes;

    @FXML
    private Pane playGroundPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        this.ticTacToes = new TicTacToes();
        this.ticTacToes.getWinProperty().addListener((e) -> {
            System.err.println("Wiiin");
        });
        this.invalidatePlayground();
    }

    public void invalidatePlayground() {
        this.playGroundPane.getChildren().clear();
        GridPane playground = new GridPane();
        for (int i = 0; i < this.ticTacToes.getPlayground().length; i++) {
            char[] row = this.ticTacToes.getPlayground()[i];
            for (int j = 0; j < row.length; j++) {
                playground.add(createTicTacToeButton(i, j), i, j);
            }
        }
        this.playGroundPane.getChildren().add(playground);
    }

    private Button createTicTacToeButton(int x, int y) {
        String buttonText = String.valueOf(this.ticTacToes.getPlayer(x, y));
        Button button = new Button(buttonText);
        button.setMaxWidth(50);
        button.setMaxHeight(50);
        button.setMinWidth(40);
        button.setMinHeight(40);
        button.setOnAction((e) -> {
            if (this.ticTacToes.put(x, y)) {
                invalidatePlayground();
                // TODO inform opponent about action
            } else {
                // TODO log some info
            }
        });
        return button;
    }

    @Override
    public void reset() {

    }
}
