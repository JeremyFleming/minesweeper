package minesweeper;

import javafx.scene.layout.Pane;

public class Board {
    private Pane gamePane;
    private Difficulty difficulty;
    private BoardSquare[][] squares;
    private int currentHighlightedRow;
    private int currentHighlightedCol;
    private boolean firstHighlight;
    //private boolean firstClick;

    public Board(Pane gamePane, Difficulty difficulty){
        this.gamePane = gamePane;
        this.difficulty = difficulty;
        this.squares = new BoardSquare[this.difficulty.getRows()][this.difficulty.getCols()];
        double sizeMultiplier = this.difficulty.getSquareSize() / Constants.HARD_SQUARE_SIZE;
        for(int row = 0; row < this.difficulty.getRows(); row++){
            for(int col = 0; col < this.difficulty.getCols(); col++){
                this.squares[row][col] =
                        new BoardSquare(this.gamePane, (col + row) % 2 == 0, row, col, sizeMultiplier);
            }
        }
        this.currentHighlightedRow = 0;
        this.currentHighlightedCol = 0;
        this.firstHighlight = true;
        //this.firstClick = true;
    }

    public void highlightBoardSquare(double x, double y){
        if(this.firstHighlight){
            this.currentHighlightedRow = (int) y / (int) this.difficulty.getSquareSize();
            this.currentHighlightedCol = (int) x / (int) this.difficulty.getSquareSize();
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].highlight();
            this.firstHighlight = false;
        } else if ((int) (y / this.difficulty.getSquareSize()) != this.currentHighlightedRow
                || (int) (x / this.difficulty.getSquareSize()) != this.currentHighlightedCol){
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].unHighlight();
            this.currentHighlightedRow = (int) y / (int) this.difficulty.getSquareSize();
            this.currentHighlightedCol = (int) x / (int) this.difficulty.getSquareSize();
            this.squares[this.currentHighlightedRow][this.currentHighlightedCol].highlight();
        }
    }

    public int handleLeftClick(boolean firstClick){
        if(firstClick){
            this.generateBombs(this.currentHighlightedRow, this.currentHighlightedCol);
            //this.firstClick = false;
        }
        if(this.squares[this.currentHighlightedRow][this.currentHighlightedCol].isMine() &&
                !this.squares[this.currentHighlightedRow][this.currentHighlightedCol].isFlagged()){
            this.revealMines();
            return 1;
        } else {
            this.openBoardSquare(this.currentHighlightedRow, this.currentHighlightedCol);
            if(this.allSquaresCleared()){
                return 2;
            }
        }
        return 0;
    }

    private void openBoardSquare(int row, int col){
        if(this.notNull(row, col) && !this.squares[row][col].isOpen() && this.squares[row][col].open()){
            for(int r = -1; r <= 1; r++){
                for(int c = -1; c <= 1; c++){
                    if(!(r == 0 && c == 0)){
                        this.openBoardSquare(row + r, col + c);
                    }
                }
            }
            /*
            this.openBoardSquare(row - 1, col - 1);
            this.openBoardSquare(row - 1, col);
            this.openBoardSquare(row - 1, col + 1);
            this.openBoardSquare(row, col - 1);
            this.openBoardSquare(row, col + 1);
            this.openBoardSquare(row + 1, col - 1);
            this.openBoardSquare(row + 1, col);
            this.openBoardSquare(row + 1, col + 1);

             */
        }
    }

    private boolean allSquaresCleared(){
        for(int row = 0; row < this.difficulty.getRows(); row++) {
            for (int col = 0; col < this.difficulty.getCols(); col++) {
                if(!this.squares[row][col].isMine() && !this.squares[row][col].isOpen()){
                    return false;
                }
            }
        }
        this.eraseHighlightAllSquares();
        return true;
    }

    private void revealMines(){
        this.eraseHighlightAllSquares();
        for(int row = 0; row < this.difficulty.getRows(); row++) {
            for (int col = 0; col < this.difficulty.getCols(); col++) {
                this.squares[row][col].revealMine();
            }
        }
    }

    private void eraseHighlightAllSquares(){
        for(int row = 0; row < this.difficulty.getRows(); row++) {
            for (int col = 0; col < this.difficulty.getCols(); col++) {
                this.squares[row][col].eraseHighlight();
            }
        }
    }

    private boolean notNull(int row, int col){
        return(row > -1 && row < this.difficulty.getRows() && col > -1 && col < this.difficulty.getCols());
    }

    private void generateBombs(double safeRow, double safeCol){
        int mineCount = 0;
        while (mineCount < this.difficulty.getMineCount()){
            int row = (int) (Math.random() * this.difficulty.getRows());
            int col = (int) (Math.random() * this.difficulty.getCols());
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
        for(int row = 0; row < this.difficulty.getRows(); row++){
            for(int col = 0; col < this.difficulty.getCols(); col++){
                if(!this.squares[row][col].isMine()){
                    int mineCount = 0;
                    for(int r = -1; r <= 1; r++){
                        for(int c = -1; c <= 1; c++){
                            if(!(r == 0 && c == 0)){
                                mineCount += this.notNullAndIsMine(row + r, col + c);
                            }
                        }
                    }
                    this.squares[row][col].setNumber(mineCount);
                }
            }
        }
    }

    private int notNullAndIsMine(int row, int col){
        if(this.notNull(row, col) && this.squares[row][col].isMine()){
            return 1;
        }
        return 0;
    }

    public int handleRightClick(){
        switch(this.squares[this.currentHighlightedRow][this.currentHighlightedCol].flag()){
            case 1: return 1;
            case 2: return (this.checkFlags());
            default: return 0;
        }
    }

    private boolean correctFlag(int row, int col){
        if(this.notNull(row, col) && this.squares[row][col].isFlagged() && !this.squares[row][col].isMine()){
            return false;
        }
        return true;
    }

    private int notNullAndIsFlag(int row, int col){
        if(this.notNull(row, col) && this.squares[row][col].isFlagged()){
            return 1;
        }
        return 0;
    }

    private int checkFlags(){
        int flagCount = 0;
        boolean legal = true;
        int row = this.currentHighlightedRow;
        int col = this.currentHighlightedCol;
        for(int r = -1; r <= 1; r++){
            for(int c = -1; c <= 1; c++){
                if(!(r == 0 && c == 0)){
                    flagCount += this.notNullAndIsFlag(row + r, col + c);
                    if(!correctFlag(row + r, col + c)){
                        legal = false;
                    }
                }
            }
        }
        if(this.squares[row][col].matchesNumber(flagCount)){
            if(!legal) {
                this.revealMines();
                return 2;
            }
            for(int r = -1; r <= 1; r++){
                for(int c = -1; c <= 1; c++){
                    if(!(r == 0 && c == 0)){
                        this.openBoardSquare(row + r, col + c);
                    }
                }
            }
            if(this.allSquaresCleared()){
                return 3;
            }
        }
        return 0;
    }

    public void resetBoard(){
        for(int row = 0; row < this.difficulty.getRows(); row++) {
            for (int col = 0; col < this.difficulty.getCols(); col++) {
                this.squares[row][col].removeGraphically();
                this.squares[row][col] = null; //new BoardSquare(this.gamePane, (col + row) % 2 == 0, row, col);
            }
        }
        /*
        this.currentHighlightedRow = -1;
        this.currentHighlightedCol = -1;
        this.firstHighlight = true;
        this.firstClick = true;

         */
    }
}
