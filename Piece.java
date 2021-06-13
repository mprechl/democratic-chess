package dem_chess;

import java.io.Serializable;

abstract class Piece implements Serializable {
    protected String colour;
    protected Board board;
    protected Square square;

    abstract public boolean move();

    public void setSquare(Square square) {
        this.square = square;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getColour() {
        return colour;
    }
}
