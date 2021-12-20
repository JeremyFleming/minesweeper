package minesweeper;

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
    private Difficulty difficulty;
    private Board board;
    private boolean gameOver;
    private boolean gameStart;
    private Label label;
    private HBox labelBox;
    private int flagCount;

    public MinesweeperGame(Pane gamePane, HBox infoBoard, Difficulty difficulty){
        this.gamePane = gamePane;
        this.infoBoard = infoBoard;
        this.difficulty = difficulty;
        this.setUpGame();
        this.createLabelBox();
        this.setUpInfoBoard();
    }

    private void setUpGame(){
        this.board = new Board(this.gamePane, this.difficulty);
        this.gamePane.setOnMouseMoved((MouseEvent e) -> this.board.highlightBoardSquare(e.getX(), e.getY()));
        this.gamePane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e));
        this.gamePane.setOnKeyPressed((KeyEvent) -> this.handleKeyPressed(KeyEvent));
        this.gamePane.setFocusTraversable(true);
        this.gameOver = false;
        this.gameStart = true;
    }

    private void createLabelBox(){
        this.label = new Label("");
        this.label.setFont(new Font(this.difficulty.getTextSize()));

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
        restartButton.setOnAction((ActionEvent e) -> this.restart(false));
        restartButton.setFocusTraversable(false);
        this.infoBoard.getChildren().addAll(restartButton, quitButton);
    }

    private void handleMouseClick(MouseEvent e){
        if(!this.gameOver){
            MouseButton click = e.getButton();
            switch(click){
                case PRIMARY:
                    switch(this.board.handleLeftClick(this.gameStart)){
                        case 1: this.lose(); break;
                        case 2: this.win(); break;
                        default: break;
                    }
                    this.gameStart = false;
                    break;
                case SECONDARY: this.flag(); break;
                default: break;
            }
        }
        e.consume();
    }

    private void handleKeyPressed(KeyEvent e){
        if(!this.gameOver){
            KeyCode code = e.getCode();
            switch(code){
                case S:
                switch(this.board.handleLeftClick(this.gameStart)){
                    case 1: this.lose(); break;
                    case 2: this.win(); break;
                    default: break;
                }
                    this.gameStart = false;
                    break;
                case F: this.flag(); break;
                default: break;
            }
        }
        e.consume();
    }

    private void flag(){
        if(!this.gameStart){
            switch(this.board.handleRightClick()){
                case 1: this.flagCount++; break;
                case 2: this.lose(); break;
                case 3: this.win(); break;
                default: this.flagCount--; break;
            }
        }
    }

    private void lose(){
        this.gameOver = true;
        this.label.setText("Game Over");
        this.gamePane.getChildren().add(this.labelBox);
    }

    private void win(){
        this.gameOver = true;
        this.label.setText("You Win");
        this.gamePane.getChildren().add(this.labelBox);
    }

    public void restart(boolean fully){
        this.gameOver = false;
        this.gameStart = true;
        this.label.setText("");
        this.gamePane.getChildren().remove(this.labelBox);
        this.board.resetBoard();
        if(!fully){
            this.board = new Board(this.gamePane, this.difficulty);
        }
    }
}
