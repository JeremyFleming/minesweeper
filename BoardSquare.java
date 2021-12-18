package minesweeper;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BoardSquare {
    private Pane gamePane;
    private Rectangle square;
    private Rectangle openedSquare;
    private Rectangle highlightSquare;
    private Ellipse mine;
    private Label number;
    private HBox numberBox;
    private Polygon flag;
    private Rectangle flagPole;
    private Rectangle flagBase;
    private Polygon cross;
    private int row;
    private int col;
    private boolean opened;
    private boolean flagged;
    private boolean isMine;
    private boolean isEmpty;

    public BoardSquare(Pane gamePane, boolean dark, int row, int col){
        this.gamePane = gamePane;
        this.row = row;
        this.col = col;
        this.opened = false;
        this.flagged = false;
        this.isMine = false;
        this.isEmpty = true;
        this.setUpBoardSquare(dark);
        this.createFlag();
        this.createMine();
        //this.createLabel();
        //this.square.setOpacity(0.4);
        //this.square.setOnMouseEntered((MouseEvent e) -> this.square.setOpacity(Constants.HIGHLIGHT_OPACITY));
        //this.square.setOnMouseExited((MouseEvent e) -> this.square.setOpacity(1));
    }

    private void setUpBoardSquare(boolean dark){
        this.square = new Rectangle(this.col * Constants.SQUARE_SIZE, this.row * Constants.SQUARE_SIZE,
                Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.openedSquare = new Rectangle(this.col * Constants.SQUARE_SIZE, this.row * Constants.SQUARE_SIZE,
                Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.highlightSquare = new Rectangle(this.col * Constants.SQUARE_SIZE, this.row * Constants.SQUARE_SIZE,
                Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.highlightSquare.setFill(Constants.HIGHLIGHT_COLOR);
        this.highlightSquare.setOpacity(Constants.HIGHLIGHT_OPACITY);
        if(dark){
            this.square.setFill(Constants.DARK_COLOR);
            this.openedSquare.setFill(Constants.OPENED_DARK_COLOR);
        } else {
            this.square.setFill(Constants.LIGHT_COLOR);
            this.openedSquare.setFill(Constants.OPENED_LIGHT_COLOR);
        }
        this.gamePane.getChildren().addAll(this.openedSquare, this.square);
    }

    private void createFlag(){
        this.flagBase = new Rectangle(
                this.square.getX() + Constants.SQUARE_SIZE / 2 - Constants.FLAG_BASE_WIDTH / 2,
                this.square.getY() + Constants.SQUARE_SIZE - 8,
                Constants.FLAG_BASE_WIDTH, Constants.FLAG_BASE_HEIGHT);
        this.flagBase.setFill(Constants.FLAG_POLE_COLOR);
        this.flagPole = new Rectangle(
                this.square.getX() + Constants.SQUARE_SIZE / 2 - Constants.FLAG_POLE_WIDTH / 2,
                this.square.getY() + 10,
                Constants.FLAG_POLE_WIDTH, Constants.FLAG_POLE_HEIGHT);
        this.flagPole.setFill(Constants.FLAG_POLE_COLOR);
        this.flag = new Polygon();
        this.flag.getPoints().addAll(
                this.square.getX() + Constants.SQUARE_SIZE / 2 - Constants.FLAG_POLE_WIDTH / 2,
                this.square.getY() + 4.0,
                this.square.getX() + Constants.SQUARE_SIZE / 2 - Constants.FLAG_POLE_WIDTH / 2 + Constants.FLAG_WIDTH,
                this.square.getY() + 4 + Constants.FLAG_HEIGHT / 2,
                this.square.getX() + Constants.SQUARE_SIZE / 2 - Constants.FLAG_POLE_WIDTH / 2,
                this.square.getY() + 4 + Constants.FLAG_HEIGHT
        );
        this.flag.setFill(Constants.FLAG_COLOR);
    }

    public void createMine(){
        this.mine = new Ellipse();
        this.mine.setCenterX(this.square.getX() + Constants.SQUARE_SIZE / 2);
        this.mine.setCenterY(this.square.getY() + Constants.SQUARE_SIZE / 2);
        this.mine.setRadiusX(Constants.MINE_RADIUS);
        this.mine.setRadiusY(Constants.MINE_RADIUS);
        this.mine.setFill(Constants.MINE_COLOR);
    }

    public void createLabel(){
        this.number = new Label("");
        this.number.setFont(new Font(Constants.TEXT_SIZE));
        this.numberBox = new HBox();
        this.numberBox.setStyle(Constants.NUMBER_BOX_COLOR);
        this.numberBox.setPrefSize(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.numberBox.setAlignment(Pos.CENTER);
        this.numberBox.setTranslateX(this.square.getX());
        this.numberBox.setTranslateY(this.square.getY());
        this.numberBox.getChildren().add(this.number);
    }

    public void highlight(){
        this.gamePane.getChildren().add(this.highlightSquare);
    }

    public void unHighlight(){
        this.gamePane.getChildren().remove(this.highlightSquare);
    }

    public boolean open(){
        if(!this.opened && !this.flagged){
            this.gamePane.getChildren().remove(this.square);
            this.opened = true;
        }
        return this.isEmpty;
    }

    public void revealMine(){
        if(!this.flagged){
            this.gamePane.getChildren().remove(this.mine);
            this.gamePane.getChildren().add(this.mine);
        }
    }

    private void createCross(){
        this.cross = new Polygon();
        this.cross.getPoints().addAll(
                this.square.getX(),
                this.square.getY(),
                this.square.getX() + Constants.CROSS_THICKNESS,
                this.square.getY(),
                this.square.getX() + Constants.SQUARE_SIZE / 2,
                this.square.getY() + Constants.SQUARE_SIZE / 2 - Constants.CROSS_THICKNESS,
                this.square.getX() + Constants.SQUARE_SIZE - Constants.CROSS_THICKNESS,
                this.square.getY(),
                this.square.getX() + Constants.SQUARE_SIZE,
                this.square.getY(),
                this.square.getX() + Constants.SQUARE_SIZE,
                this.square.getY() + Constants.CROSS_THICKNESS,
                this.square.getX() + Constants.SQUARE_SIZE / 2 + Constants.CROSS_THICKNESS,
                this.square.getY() + Constants.SQUARE_SIZE / 2,
                this.square.getX() + Constants.SQUARE_SIZE,
                this.square.getY() + Constants.SQUARE_SIZE - Constants.CROSS_THICKNESS,
                this.square.getX() + Constants.SQUARE_SIZE,
                this.square.getY() + Constants.SQUARE_SIZE,
                this.square.getX() + Constants.SQUARE_SIZE - Constants.CROSS_THICKNESS,
                this.square.getY() + Constants.SQUARE_SIZE,
                this.square.getX() + Constants.SQUARE_SIZE / 2,
                this.square.getY() + Constants.SQUARE_SIZE / 2 + Constants.CROSS_THICKNESS,
                this.square.getX() + Constants.CROSS_THICKNESS,
                this.square.getY() + Constants.SQUARE_SIZE,
                this.square.getX(),
                this.square.getY() + Constants.SQUARE_SIZE,
                this.square.getX(),
                this.square.getY() + Constants.SQUARE_SIZE - Constants.CROSS_THICKNESS,
                this.square.getX() + Constants.SQUARE_SIZE / 2 - Constants.CROSS_THICKNESS,
                this.square.getY() + Constants.SQUARE_SIZE / 2,
                this.square.getX(),
                this.square.getY() + Constants.CROSS_THICKNESS
        );
        this.cross.setFill(Constants.CROSS_COLOR);
        this.cross.setOpacity(Constants.CROSS_OPACITY);
    }

    public boolean isOpen(){ return this.opened; }

    public boolean isFlagged(){ return this.flagged; }

    public void flag(boolean gameStarted){
        if(gameStarted){
            if(!this.opened){
                if(!this.flagged){
                    this.gamePane.getChildren().addAll(this.flagBase, this.flagPole, this.flag);
                    this.flagged = true;
                } else {
                    this.gamePane.getChildren().removeAll(this.flagBase, this.flagPole, this.flag);
                    this.flagged = false;
                }
            }
        }
    }

    public boolean isMine(){ return this.isMine; }

    public void addMine(){
        this.isMine = true;
        this.isEmpty = false;
        this.gamePane.getChildren().add(this.mine);
        this.gamePane.getChildren().remove(this.square);
        this.gamePane.getChildren().add(this.square);
        if(this.isFlagged()){
            this.gamePane.getChildren().removeAll(this.flagPole, this.flagBase, this.flag);
            this.gamePane.getChildren().addAll(this.flagPole, this.flagBase, this.flag);
        }
    }

    public void setNumber(int number){
        this.createLabel();
        if(number > 0){
            this.isEmpty = false;
            this.number.setText("" + number);
            this.gamePane.getChildren().addAll(this.numberBox);
            this.gamePane.getChildren().remove(this.square);
            this.gamePane.getChildren().add(this.square);
        }
        switch(number){
            case 1: this.number.setTextFill(Constants.COLOR1); break;
            case 2: this.number.setTextFill(Constants.COLOR2); break;
            case 3: this.number.setTextFill(Constants.COLOR3); break;
            case 4: this.number.setTextFill(Constants.COLOR4); break;
            case 5: this.number.setTextFill(Constants.COLOR5); break;
            case 6: this.number.setTextFill(Constants.COLOR6); break;
            case 7: this.number.setTextFill(Constants.COLOR7); break;
            case 8: this.number.setTextFill(Constants.COLOR8); break;
            default: break;
        }
    }
}
