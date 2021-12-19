package minesweeper;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneOrganizer {
    private Stage stage;
    private BorderPane root;
    private VBox home;
    private Difficulty difficulty;
    private MinesweeperGame game;

    public PaneOrganizer(Stage stage){
        this.stage = stage;
        this.setUpHome();
        /*
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.HARD_NUM_COLS * Constants.HARD_SQUARE_SIZE,
                Constants.HARD_NUM_ROWS * Constants.HARD_SQUARE_SIZE + Constants.INFO_BOARD_HEIGHT);
        Pane gamePane = new Pane();
        gamePane.setPrefSize(Constants.HARD_NUM_COLS * Constants.HARD_SQUARE_SIZE,
                Constants.HARD_NUM_ROWS * Constants.HARD_SQUARE_SIZE);
        this.root.setCenter(gamePane);
        MinesweeperGame minesweeper = new MinesweeperGame(gamePane, this.setUpInfoBoard());

         */
    }

    private void setUpHome(){
        //this.difficulty = Difficulty.EASY;
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.HOME_SCREEN_WIDTH, Constants.HOME_SCREEN_HEIGHT);

        this.home = new VBox(Constants.BUTTON_SPACING);
        this.home.setPrefSize(Constants.HOME_SCREEN_WIDTH, Constants.HOME_SCREEN_HEIGHT);
        this.home.setAlignment(Pos.CENTER);

        Button easy = new Button("Easy");
        Button medium = new Button("Medium");
        Button hard = new Button("Hard");

        easy.setPrefSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        medium.setPrefSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        hard.setPrefSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);

        easy.setOnAction((ActionEvent) -> this.runGame(Difficulty.EASY));
        medium.setOnAction((ActionEvent) -> this.runGame(Difficulty.MEDIUM));
        hard.setOnAction((ActionEvent) -> this.runGame(Difficulty.HARD));

        easy.setFocusTraversable(false);
        medium.setFocusTraversable(false);
        hard.setFocusTraversable(false);

        this.home.getChildren().addAll(easy, medium, hard);
        this.root.setCenter(this.home);
    }

    private void runGame(Difficulty mode){
        this.difficulty = mode;
        this.root.setPrefSize(this.difficulty.getCols() * this.difficulty.getSquareSize(),
                this.difficulty.getRows() * this.difficulty.getSquareSize() + Constants.INFO_BOARD_HEIGHT);
        Pane gamePane = new Pane();
        gamePane.setPrefSize(this.difficulty.getCols() * this.difficulty.getSquareSize(),
                this.difficulty.getRows() * this.difficulty.getSquareSize());
        this.root.setCenter(gamePane);
        this.stage.sizeToScene();
        this.game = new MinesweeperGame(gamePane, this.setUpInfoBoard(), this.difficulty);
    }

    private HBox setUpInfoBoard(){
        HBox statsBoard = new HBox();
        statsBoard.setPrefSize(this.difficulty.getCols() * this.difficulty.getSquareSize(),
                Constants.INFO_BOARD_HEIGHT);
        statsBoard.setSpacing(Constants.INFO_BOARD_SPACING);
        statsBoard.setAlignment(Pos.CENTER);
        this.root.setTop(statsBoard);


        Button back = new Button("Back");
        back.setOnAction((ActionEvent) -> this.returnHome());
        back.setFocusTraversable(false);
        statsBoard.getChildren().add(back);

        return statsBoard;
    }

    private void returnHome(){
        this.game.restart(true);
        this.root.setTop(null);
        this.root.setCenter(this.home);
        this.root.setPrefSize(Constants.HOME_SCREEN_WIDTH, Constants.HOME_SCREEN_HEIGHT);
        this.stage.sizeToScene();
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
