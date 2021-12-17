package minesweeper;

import javafx.scene.layout.BorderPane;

public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer(){
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.NUM_COLS * Constants.SQUARE_SIZE,
                Constants.NUM_ROWS * Constants.SQUARE_SIZE);
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
