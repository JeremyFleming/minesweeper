package minesweeper;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Board {
    private Pane gamePane;
    private BoardSquare[][] squares;
    private int currentHighlightedRow;
    private int currentHighlightedCol;
    private boolean firstHighlight;
    private boolean firstClick;

    public Board(Pane gamePane){
        this.gamePane = gamePane;
        this.squares = new BoardSquare[Constants.NUM_ROWS][Constants.NUM_COLS];
        for(int row = 0; row < Constants.NUM_ROWS; row++){
            for(int col = 0; col < Constants.NUM_COLS; col++){
                this.squares[row][col] = new BoardSquare(this.gamePane, (col + row) % 2 == 0, row, col);
            }
        }
        this.currentHighlightedRow = -1;
        this.currentHighlightedCol = -1;
        this.firstHighlight = true;
        this.firstClick = true;
        /*
        this.gamePane.setOnMouseMoved((MouseEvent e) -> this.highlightBoardSquare(e.getX(), e.getY()));
        this.gamePane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e));
        this.gamePane.setOnKeyPressed((KeyEvent) -> this.handleKeyPressed(KeyEvent));
        this.gamePane.setFocusTraversable(true);

         */
    }

    public void highlightBoardSquare(double x, double y){
        if(this.firstHighlight){
            this.currentHighlightedRow = (int) y / Constants.SQUARE_SIZE;
            this.currentHighlightedCol = (int) x / Constants.SQUARE_SIZE;
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].highlight();
            this.firstHighlight = false;
        } else if ((int) (y / Constants.SQUARE_SIZE) != this.currentHighlightedRow
                || (int) (x / Constants.SQUARE_SIZE) != this.currentHighlightedCol){
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].unHighlight();
            this.currentHighlightedRow = (int) y / Constants.SQUARE_SIZE;
            this.currentHighlightedCol = (int) x / Constants.SQUARE_SIZE;
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].highlight();
        }
    }

    /*
    private void handleMouseClick(MouseEvent e){
        MouseButton click = e.getButton();
        switch(click){
            case PRIMARY: this.openBoardSquare(); break;
            case SECONDARY: this.flagSquare(); break;
            default: break;
        }
        e.consume();
    }

    private void handleKeyPressed(KeyEvent e){
        KeyCode code = e.getCode();
        switch(code){
            case S: this.openBoardSquare(); break;
            case F: this.flagSquare(); break;
            default: break;
        }
        e.consume();
    }

     */

    public void openBoardSquare(){
        if(this.firstClick){
            this.generateBombs(this.currentHighlightedRow, this.currentHighlightedCol);
            if(this.squares[this.currentHighlightedRow][this.currentHighlightedCol].open()){
                this.openNotNullAndNotEmpty(this.currentHighlightedRow - 1, currentHighlightedCol - 1);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow - 1, currentHighlightedCol);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow - 1, currentHighlightedCol + 1);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow, currentHighlightedCol - 1);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow, currentHighlightedCol + 1);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow + 1, currentHighlightedCol - 1);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow + 1, currentHighlightedCol);
                this.openNotNullAndNotEmpty(this.currentHighlightedRow + 1, currentHighlightedCol + 1);
            }
            this.firstClick = false;
        } else {
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].open();
        }
    }

    private void openNotNullAndNotEmpty(int row, int col){
        if(row > -1 && row < Constants.NUM_ROWS && col > -1 && col < Constants.NUM_COLS
                && !this.squares[row][col].isOpen()){
            this.squares[row][col].open();
        }
    }

    private void generateBombs(double safeRow, double safeCol){
        int mineCount = 0;
        while (mineCount <= Constants.MINE_COUNT){
            int row = (int) (Math.random() * Constants.NUM_ROWS);
            int col = (int) (Math.random() * Constants.NUM_COLS);
            boolean isSafe = row > safeRow - 3 && row < safeRow + 3 && col > safeCol - 3 && col < safeCol + 3;
            boolean isMine = this.squares[row][col].isMine();
            if(!isSafe && !isMine){
                this.squares[row][col].addMine();
                mineCount++;
            }
        }
        this.generateNumbers();
    }

    private void generateNumbers(){
        for(int row = 0; row < Constants.NUM_ROWS; row++){
            for(int col = 0; col < Constants.NUM_COLS; col++){
                if(!this.squares[row][col].isMine()){
                    int mineCount = 0;
                    mineCount += this.notNullAndIsMine(row - 1, col - 1);
                    mineCount += this.notNullAndIsMine(row - 1, col);
                    mineCount += this.notNullAndIsMine(row - 1, col + 1);
                    mineCount += this.notNullAndIsMine(row, col - 1);
                    mineCount += this.notNullAndIsMine(row, col + 1);
                    mineCount += this.notNullAndIsMine(row + 1, col - 1);
                    mineCount += this.notNullAndIsMine(row + 1, col);
                    mineCount += this.notNullAndIsMine(row + 1, col + 1);
                    this.squares[row][col].setNumber(mineCount);
                }
            }
        }
    }

    private int notNullAndIsMine(int row, int col){
        if(row > -1 && row < Constants.NUM_ROWS && col > -1 && col < Constants.NUM_COLS
                && this.squares[row][col].isMine()){
            return 1;
        }
        return 0;
    }

    public void flagSquare(){
        this.squares[this.currentHighlightedRow][this.currentHighlightedCol].flag();
    }
}
