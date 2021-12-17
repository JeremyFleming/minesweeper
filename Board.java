package minesweeper;

import javafx.scene.layout.Pane;

public class Board {
    private Pane gamePane;
    private BoardSquare[][] squares;

    public Board(Pane gamePane){
        this.gamePane = gamePane;
        this.squares = new BoardSquare[Constants.NUM_ROWS][Constants.NUM_COLS];
        for(int row = 0; row < Constants.NUM_ROWS; row++){
            for(int col = 0; col < Constants.NUM_COLS; col++){
                this.squares[row][col] = new BoardSquare(this.gamePane, (col + row) % 2 == 0, row, col);
            }
        }
    }

}
