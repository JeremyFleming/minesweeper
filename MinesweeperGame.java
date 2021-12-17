package minesweeper;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MinesweeperGame {
    private Pane gamePane;
    private HBox infoBoard;
    private Board board;
    //private Timeline timeline;

    public MinesweeperGame(Pane gamePane, HBox infoBoard){
        this.gamePane = gamePane;
        this.infoBoard = infoBoard;
        /*
        Label label = new Label("Hi");
        this.infoBoard.getChildren().add(label);

         */
        //this.setUpTimeline();
        this.setUpGame();
    }

    /*
    private void setUpTimeline(){

    }

     */

    private void setUpGame(){
        this.board = new Board(this.gamePane);

    }

}
