package minesweeper;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    public int getRows(){
        switch(this){
            case EASY: return Constants.EASY_NUM_ROWS;
            case MEDIUM: return Constants.MEDIUM_NUM_ROWS;
            case HARD: return Constants.HARD_NUM_ROWS;
            default: return 0;
        }
    }

    public int getCols(){
        switch(this){
            case EASY: return Constants.EASY_NUM_COLS;
            case MEDIUM: return Constants.MEDIUM_NUM_COLS;
            case HARD: return Constants.HARD_NUM_COLS;
            default: return 0;
        }
    }

    public double getSquareSize(){
        switch(this){
            case EASY: return Constants.EASY_SQUARE_SIZE;
            case MEDIUM: return Constants.MEDIUM_SQUARE_SIZE;
            case HARD: return Constants.HARD_SQUARE_SIZE;
            default: return 0;
        }
    }

    public int getMineCount(){
        switch(this){
            case EASY: return Constants.EASY_MINE_COUNT;
            case MEDIUM: return Constants.MEDIUM_MINE_COUNT;
            case HARD: return Constants.HARD_MINE_COUNT;
            default: return 0;
        }
    }

    public int getTextSize(){
        switch(this){
            case EASY: return Constants.EASY_TEXT_SIZE;
            case MEDIUM: return Constants.MEDIUM_TEXT_SIZE;
            case HARD: return Constants.HARD_TEXT_SIZE;
            default: return 0;
        }
    }
}
