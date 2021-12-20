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
    private int value;
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
    private boolean highlighted;
    private boolean crossed;
    private double sizeMultiplier;
    private double squareSize;

    public BoardSquare(Pane gamePane, boolean dark, int row, int col, double sizeMultiplier){
        this.gamePane = gamePane;
        this.row = row;
        this.col = col;
        this.sizeMultiplier = sizeMultiplier;
        this.squareSize = Constants.HARD_SQUARE_SIZE * sizeMultiplier;
        this.opened = false;
        this.flagged = false;
        this.isMine = false;
        this.isEmpty = true;
        this.highlighted = false;
        this.crossed = false;
        this.value = 0;
        this.setUpBoardSquare(dark);
        this.createFlag();
        this.createMine();
        //this.createLabel();
        //this.square.setOpacity(0.4);
    }

    private void setUpBoardSquare(boolean dark){
        this.square = new Rectangle(this.col * this.squareSize, this.row * this.squareSize,
                this.squareSize, this.squareSize);
        this.openedSquare = new Rectangle(this.col * this.squareSize, this.row * this.squareSize,
                this.squareSize, this.squareSize);
        this.highlightSquare = new Rectangle(this.col * this.squareSize, this.row * this.squareSize,
                this.squareSize, this.squareSize);
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
                this.square.getX() + this.squareSize / 2 - Constants.FLAG_BASE_WIDTH * this.sizeMultiplier / 2,
                this.square.getY() + this.squareSize - 8 * this.sizeMultiplier,
                Constants.FLAG_BASE_WIDTH * this.sizeMultiplier,
                Constants.FLAG_BASE_HEIGHT * this.sizeMultiplier);
        this.flagBase.setFill(Constants.FLAG_POLE_COLOR);
        this.flagPole = new Rectangle(
                this.square.getX() + this.squareSize / 2 - Constants.FLAG_POLE_WIDTH * this.sizeMultiplier / 2,
                this.square.getY() + 10 * this.sizeMultiplier,
                Constants.FLAG_POLE_WIDTH * this.sizeMultiplier,
                Constants.FLAG_POLE_HEIGHT * this.sizeMultiplier);
        this.flagPole.setFill(Constants.FLAG_POLE_COLOR);
        this.flag = new Polygon();
        this.flag.getPoints().addAll(
                this.square.getX() + this.squareSize / 2
                        - Constants.FLAG_POLE_WIDTH * this.sizeMultiplier / 2,
                this.square.getY() + 4.0 * this.sizeMultiplier,
                this.square.getX() + this.squareSize / 2
                        - Constants.FLAG_POLE_WIDTH * this.sizeMultiplier / 2
                        + Constants.FLAG_WIDTH * this.sizeMultiplier,
                this.square.getY() + 4 * this.sizeMultiplier + Constants.FLAG_HEIGHT * this.sizeMultiplier / 2,
                this.square.getX() + this.squareSize / 2 - Constants.FLAG_POLE_WIDTH * this.sizeMultiplier / 2,
                this.square.getY() + 4 * this.sizeMultiplier + Constants.FLAG_HEIGHT * this.sizeMultiplier
        );
        this.flag.setFill(Constants.FLAG_COLOR);
    }

    public void createMine(){
        this.mine = new Ellipse();
        this.mine.setCenterX(this.square.getX() + this.squareSize / 2);
        this.mine.setCenterY(this.square.getY() + this.squareSize / 2);
        this.mine.setRadiusX(Constants.MINE_RADIUS * this.sizeMultiplier);
        this.mine.setRadiusY(Constants.MINE_RADIUS * this.sizeMultiplier);
        this.mine.setFill(Constants.MINE_COLOR);
    }

    public void createLabel(){
        this.number = new Label("");
        this.number.setFont(new Font(Constants.TEXT_SIZE * this.sizeMultiplier));
        this.numberBox = new HBox();
        this.numberBox.setStyle(Constants.NUMBER_BOX_COLOR);
        this.numberBox.setPrefSize(this.squareSize, this.squareSize);
        this.numberBox.setAlignment(Pos.CENTER);
        this.numberBox.setTranslateX(this.square.getX());
        this.numberBox.setTranslateY(this.square.getY());
        this.numberBox.getChildren().add(this.number);
    }

    public void highlight(){
        this.gamePane.getChildren().add(this.highlightSquare);
        this.highlighted = true;
    }

    public void unHighlight(){
        this.gamePane.getChildren().remove(this.highlightSquare);
        this.highlighted = false;
    }

    public boolean open(){
        if(!this.opened && !this.flagged){
            this.gamePane.getChildren().remove(this.square);
            this.opened = true;
        }
        if(this.flagged){
            return false;
        }
        return this.isEmpty;
    }

    public void revealMine(){
        if(!this.flagged && this.isMine){
            this.gamePane.getChildren().remove(this.mine);
            this.gamePane.getChildren().add(this.mine);
        } else if (this.flagged && !this.isMine){
            this.createCross();
            this.gamePane.getChildren().removeAll(this.flag, this.flagPole, this.flagBase);
            this.gamePane.getChildren().addAll(this.cross);
            this.crossed = true;
        }
    }

    private void createCross(){
        this.cross = new Polygon();
        this.cross.getPoints().addAll(
                this.square.getX(),
                this.square.getY(),
                this.square.getX() + Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getY(),
                this.square.getX() + this.squareSize / 2,
                this.square.getY() + this.squareSize / 2 - Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getX() + this.squareSize - Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getY(),
                this.square.getX() + this.squareSize,
                this.square.getY(),
                this.square.getX() + this.squareSize,
                this.square.getY() + Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getX() + this.squareSize / 2 + Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getY() + this.squareSize / 2,
                this.square.getX() + this.squareSize,
                this.square.getY() + this.squareSize - Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getX() + this.squareSize,
                this.square.getY() + this.squareSize,
                this.square.getX() + this.squareSize - Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getY() + this.squareSize,
                this.square.getX() + this.squareSize / 2,
                this.square.getY() + this.squareSize / 2 + Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getX() + Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getY() + this.squareSize,
                this.square.getX(),
                this.square.getY() + this.squareSize,
                this.square.getX(),
                this.square.getY() + this.squareSize - Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getX() + this.squareSize / 2 - Constants.CROSS_THICKNESS * this.sizeMultiplier,
                this.square.getY() + this.squareSize / 2,
                this.square.getX(),
                this.square.getY() + Constants.CROSS_THICKNESS * this.sizeMultiplier
        );
        this.cross.setFill(Constants.CROSS_COLOR);
        //this.cross.setOpacity(Constants.CROSS_OPACITY);
    }

    public boolean isOpen(){ return this.opened; }

    public boolean isFlagged(){ return this.flagged; }

    public int flag(){
        if(!this.opened){
            if(!this.flagged){
                this.gamePane.getChildren().addAll(this.flagBase, this.flagPole, this.flag);
                this.flagged = true;
                return 1;
            } else {
                this.gamePane.getChildren().removeAll(this.flagBase, this.flagPole, this.flag);
                this.flagged = false;
                return 0;
            }
        } else if (!this.isMine && !this.isEmpty){
            return 2;
        }
        return 0;
    }

    public boolean isMine(){ return this.isMine; }

    public void addMine(){
        this.isMine = true;
        this.isEmpty = false;
        this.gamePane.getChildren().add(this.mine);
        this.gamePane.getChildren().remove(this.square);
        this.gamePane.getChildren().add(this.square);
    }

    public void setNumber(int number){
        this.createLabel();
        this.value = number;
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

    public void removeGraphically(){
        this.gamePane.getChildren().remove(this.openedSquare);
        if(this.highlighted){
            this.gamePane.getChildren().remove(this.highlightSquare);
        }
        if(this.flagged){
            this.gamePane.getChildren().removeAll(this.flag, this.flagPole, this.flagBase);
        }
        if(this.isMine){
            this.gamePane.getChildren().remove(this.mine);
        }
        if(!this.opened){
            this.gamePane.getChildren().remove(this.square);
        }
        if(this.crossed){
            this.gamePane.getChildren().remove(this.cross);
        }
        if(!this.isEmpty && !this.isMine){
            this.gamePane.getChildren().removeAll(this.number, this.numberBox);
        }
    }

    public boolean matchesNumber(int valueToCheck){
        return valueToCheck == this.value;
    }

    public void eraseHighlight(){
        this.highlightSquare.setOpacity(0);
    }
}
