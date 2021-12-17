package minesweeper;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class BoardSquare {
    private Pane gamePane;
    private Rectangle square;
    private Rectangle openedSquare;
    private Ellipse bomb;
    private int row;
    private int col;

    public BoardSquare(Pane gamePane, boolean dark, int row, int col){
        this.gamePane = gamePane;
        this.row = row;
        this.col = col;
        this.square = new Rectangle(this.col * Constants.SQUARE_SIZE, this.row * Constants.SQUARE_SIZE,
                Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.openedSquare = new Rectangle(this.col * Constants.SQUARE_SIZE, this.row * Constants.SQUARE_SIZE,
                Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        if(dark){
            this.square.setFill(Constants.DARK_COLOR);
            this.openedSquare.setFill(Constants.OPENED_DARK_COLOR);
        } else {
            this.square.setFill(Constants.LIGHT_COLOR);
            this.openedSquare.setFill(Constants.OPENED_LIGHT_COLOR);
        }
        this.gamePane.getChildren().addAll(this.openedSquare, this.square);
    }
}
