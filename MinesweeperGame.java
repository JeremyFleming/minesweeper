package minesweeper;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
    private Label flagLabel;
    private int time;
    private Timeline timer;
    private Label clockLabel;

    public MinesweeperGame(Pane gamePane, HBox infoBoard, Difficulty difficulty){
        this.gamePane = gamePane;
        this.infoBoard = infoBoard;
        this.difficulty = difficulty;
        this.setUpGame();
        this.createLabelBox();
        this.setUpInfoBoard();
        this.setUpTimeline();
    }

    private void setUpGame(){
        this.flagCount = this.difficulty.getMineCount();
        this.time = 0;
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

        HBox flagBox = new HBox(Constants.FLAG_BOX_SPACING);
        flagBox.setAlignment(Pos.CENTER);
        this.flagLabel = new Label("" + this.flagCount);
        this.flagLabel.setFont(new Font(Constants.TEXT_SIZE));
        Pane flagPane = new Pane();
        flagPane.setPrefSize(Constants.INFO_BOARD_HEIGHT, Constants.INFO_BOARD_HEIGHT);
        flagBox.getChildren().addAll(flagPane, this.flagLabel);

        double x = flagPane.getLayoutX();
        double squareSize = Constants.INFO_BOARD_HEIGHT;
        double sizeMultiplier = Constants.INFO_BOARD_HEIGHT / Constants.HARD_SQUARE_SIZE;
        Rectangle flagBase = new Rectangle( x + squareSize / 2 - Constants.FLAG_BASE_WIDTH * sizeMultiplier / 2,
                squareSize - 8 * sizeMultiplier,
                Constants.FLAG_BASE_WIDTH * sizeMultiplier,
                Constants.FLAG_BASE_HEIGHT * sizeMultiplier);
        flagBase.setFill(Constants.FLAG_POLE_COLOR);
        Rectangle flagPole = new Rectangle(x + squareSize / 2 - Constants.FLAG_POLE_WIDTH * sizeMultiplier / 2,
                10 * sizeMultiplier,
                Constants.FLAG_POLE_WIDTH * sizeMultiplier,
                Constants.FLAG_POLE_HEIGHT * sizeMultiplier - 1);
        flagPole.setFill(Constants.FLAG_POLE_COLOR);
        Polygon flag = new Polygon();
        flag.getPoints().addAll(x + squareSize / 2 - Constants.FLAG_POLE_WIDTH * sizeMultiplier / 2 - 0.3,
                4.0 * sizeMultiplier,
                x + squareSize / 2 - Constants.FLAG_POLE_WIDTH * sizeMultiplier / 2 - 0.3
                        + Constants.FLAG_WIDTH * sizeMultiplier,
                4 * sizeMultiplier + Constants.FLAG_HEIGHT * sizeMultiplier / 2,
                x + squareSize / 2 - Constants.FLAG_POLE_WIDTH * sizeMultiplier / 2 - 0.3,
                4 * sizeMultiplier + Constants.FLAG_HEIGHT * sizeMultiplier
        );
        flag.setFill(Constants.FLAG_COLOR);

        flagPane.getChildren().addAll(flagPole, flagBase, flag);

        HBox clockBox = new HBox(Constants.FLAG_BOX_SPACING);
        clockBox.setAlignment(Pos.CENTER);
        this.clockLabel = new Label("00" + this.time);
        this.clockLabel.setFont(new Font(Constants.TEXT_SIZE));
        ImageView clockImage = new ImageView(
                new Image("./minesweeper/31048.png",
                        Constants.INFO_BOARD_HEIGHT - 10,
                        Constants.INFO_BOARD_HEIGHT - 10,
                        false,
                        true)
        );
        clockImage.setX(clockBox.getLayoutX());
        clockBox.getChildren().addAll(clockImage, this.clockLabel);

        this.infoBoard.getChildren().addAll(flagBox, restartButton, clockBox, quitButton);
    }

    private void setUpTimeline(){
        KeyFrame kf = new KeyFrame(Duration.seconds(1), (ActionEvent e) -> this.updateTime());
        this.timer = new Timeline(kf);
        this.timer.setCycleCount(Animation.INDEFINITE);
    }

    private void updateTime(){
        this.time++;
        if(time < 10){
            this.clockLabel.setText("00" + this.time);
        } else if (time < 100) {
            this.clockLabel.setText("0" + this.time);
        } else if (time < 1000) {
            this.clockLabel.setText("" + this.time);
        } else {
            this.timer.stop();
        }
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
                    if(this.gameStart){
                        this.gameStart = false;
                        this.timer.play();
                    }
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
                if(this.gameStart){
                    this.gameStart = false;
                    this.timer.play();
                }
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
                case 0: this.flagCount++;
                this.flagLabel.setText("" + this.flagCount);
                break;
                case 1: this.flagCount--;
                this.flagLabel.setText("" + this.flagCount);
                break;
                case 2: this.lose(); break;
                case 3: this.win(); break;
                default: break;
            }
        }
    }

    private void lose(){
        this.gameOver = true;
        this.label.setText("Game Over");
        this.gamePane.getChildren().add(this.labelBox);
        this.timer.stop();
    }

    private void win(){
        this.gameOver = true;
        this.label.setText("You Win");
        this.gamePane.getChildren().add(this.labelBox);
        this.timer.stop();
    }

    public void restart(boolean fully){
        this.gameOver = false;
        this.gameStart = true;
        this.flagCount = this.difficulty.getMineCount();
        this.time = 0;
        this.flagLabel.setText("" + this.flagCount);
        this.clockLabel.setText("00" + this.time);
        this.timer.stop();
        this.label.setText("");
        this.gamePane.getChildren().remove(this.labelBox);
        this.board.resetBoard();
        if(!fully){
            this.board = new Board(this.gamePane, this.difficulty);
        }
    }
}
