package dem_chess;

import java.io.Serializable;

public class Square implements Serializable {
    private final Board board;
    private final int x;
    private final int y;
    private Piece piece;

    public Square(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * sets the piece to the one specified in parameter
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        piece.setSquare(this);
        piece.setBoard(board);
    }

    public Piece getPiece() {
        return piece;
    }

    /**
     * removes any piece from this square
     */
    public void clearPiece() {
        piece = null;
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
