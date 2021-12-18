package minesweeper;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MinesweeperGame {
    private Pane gamePane;
    private HBox infoBoard;
    private Board board;
    private boolean gameOver;

    public MinesweeperGame(Pane gamePane, HBox infoBoard){
        this.gamePane = gamePane;
        this.infoBoard = infoBoard;
        this.setUpGame();
    }

    private void setUpGame(){
        this.board = new Board(this.gamePane);
        this.gamePane.setOnMouseMoved((MouseEvent e) -> this.board.highlightBoardSquare(e.getX(), e.getY()));
        this.gamePane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e));
        this.gamePane.setOnKeyPressed((KeyEvent) -> this.handleKeyPressed(KeyEvent));
        this.gamePane.setFocusTraversable(true);
        this.gameOver = false;
    }

    private void handleMouseClick(MouseEvent e){
        if(!this.gameOver){
            MouseButton click = e.getButton();
            switch(click){
                case PRIMARY: if(this.board.openBoardSquares()){
                    this.gameOver = true;
                }; break;
                case SECONDARY: this.board.flagSquare(); break;
                default: break;
            }
        }
        e.consume();
    }

    private void handleKeyPressed(KeyEvent e){
        if(!this.gameOver){
            KeyCode code = e.getCode();
            switch(code){
                case S: if(this.board.openBoardSquares()){
                    this.gameOver = true;
                }; break;
                case F: this.board.flagSquare(); break;
                default: break;
            }
        }
        e.consume();
    }
}
