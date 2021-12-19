package minesweeper;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class MinesweeperGame {
    private Pane gamePane;
    private HBox infoBoard;
    private Board board;
    private boolean gameOver;
    private Label label;
    private HBox labelBox;

    public MinesweeperGame(Pane gamePane, HBox infoBoard){
        this.gamePane = gamePane;
        this.infoBoard = infoBoard;
        this.setUpGame();
        this.createLabelBox();
        this.setUpInfoBoard();
    }

    private void setUpGame(){
        this.board = new Board(this.gamePane);
        this.gamePane.setOnMouseMoved((MouseEvent e) -> this.board.highlightBoardSquare(e.getX(), e.getY()));
        this.gamePane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e));
        this.gamePane.setOnKeyPressed((KeyEvent) -> this.handleKeyPressed(KeyEvent));
        this.gamePane.setFocusTraversable(true);
        this.gameOver = false;
    }

    private void createLabelBox(){
        this.label = new Label("");
        this.label.setFont(new Font(Constants.BIGGER_TEXT_SIZE));

        this.labelBox = new HBox();
        this.labelBox.setStyle(Constants.LABEL_BOX_COLOR);
        this.labelBox.setPrefSize(this.gamePane.getPrefWidth(), this.gamePane.getPrefHeight());
        this.labelBox.setAlignment(Pos.CENTER);
        this.labelBox.getChildren().add(this.label);
    }

    private void setUpInfoBoard(){
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setFocusTraversable(false);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction((ActionEvent e) -> this.restart());
        restartButton.setFocusTraversable(false);
        this.infoBoard.getChildren().addAll(quitButton, restartButton);
    }

    private void handleMouseClick(MouseEvent e){
        if(!this.gameOver){
            MouseButton click = e.getButton();
            switch(click){
                case PRIMARY:
                    switch(this.board.openBoardSquares()){
                        case 1: this.lost(); break;
                        case 2: this.win(); break;
                        default: break;
                } break;
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
                case S: switch(this.board.openBoardSquares()){
                    case 1: this.lost(); break;
                    case 2: this.win(); break;
                    default: break;
                } break;
                case F: this.board.flagSquare(); break;
                default: break;
            }
        }
        e.consume();
    }

    private void lost(){
        this.gameOver = true;
        this.label.setText("Game Over");
        this.gamePane.getChildren().add(this.labelBox);
    }

    private void win(){
        this.gameOver = true;
        this.label.setText("You Win");
        this.gamePane.getChildren().add(this.labelBox);
    }

    private void restart(){
        this.gameOver = false;
        this.label.setText("");
        this.gamePane.getChildren().remove(this.labelBox);
        this.board.resetBoard();
    }
}
