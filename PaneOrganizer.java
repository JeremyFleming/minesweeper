package minesweeper;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer(){
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.NUM_COLS * Constants.SQUARE_SIZE,
                Constants.NUM_ROWS * Constants.SQUARE_SIZE + Constants.INFO_BOARD_HEIGHT);
        Pane gamePane = new Pane();
        gamePane.setPrefSize(Constants.NUM_COLS * Constants.SQUARE_SIZE,
                Constants.NUM_ROWS * Constants.SQUARE_SIZE);
        this.root.setCenter(gamePane);
        MinesweeperGame minesweeper = new MinesweeperGame(gamePane, this.setUpInfoBoard());
    }

    private HBox setUpInfoBoard(){
        HBox statsBoard = new HBox();
        statsBoard.setPrefSize(Constants.NUM_COLS * Constants.SQUARE_SIZE, Constants.INFO_BOARD_HEIGHT);
        statsBoard.setSpacing(Constants.INFO_BOARD_SPACING);
        statsBoard.setAlignment(Pos.CENTER);
        this.root.setTop(statsBoard);

        /*
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setFocusTraversable(false);
        statsBoard.getChildren().add(quitButton);

         */
        return statsBoard;
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
