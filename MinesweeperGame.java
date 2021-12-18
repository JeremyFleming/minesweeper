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

    }

    private void handleMouseClick(MouseEvent e){
        MouseButton click = e.getButton();
        switch(click){
            case PRIMARY: this.board.openBoardSquare(); break;
            case SECONDARY: this.board.flagSquare(); break;
            default: break;
        }
        e.consume();
    }

    private void handleKeyPressed(KeyEvent e){
        KeyCode code = e.getCode();
        switch(code){
            case S: this.board.openBoardSquare(); break;
            case F: this.board.flagSquare(); break;
            default: break;
        }
        e.consume();
    }
}
