package dem_chess;

import java.io.Serializable;

public class Board implements Serializable {
    private final int width;
    private final int height;
    private final Square[][] squares;
    private String nextPlayer;
    private String otherPlayer;

    public Board() {
        width = 8;
        height = 8;
        squares = new Square[width][height];
        nextPlayer = "white";
        otherPlayer = "black";
        generateSquares();
        generatePieces();
    }

    /**
     * generate the squares that make up the board
     */
    private void generateSquares() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                squares[y][x] = new Square(this, x, y);
            }
        }
    }

    /**
     * fill the second row on both sides with pawns of the correct colour
     */
    private void generatePieces() {
        for (int i = 0; i < width; i++) {
            //black
            squares[1][i].setPiece(new Pawn("black"));
            //white
            squares[7 - 1][i].setPiece(new Pawn("white"));
        }
    }

    public Square[][] getSquares() {
        return squares;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    private void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    private void setOtherPlayer(String otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    /**
     * hand over control to the other player
     */
    public void advance() {
        //by switching nextPlayer and otherPlayer
        String buffer = nextPlayer;
        setNextPlayer(otherPlayer);
        setOtherPlayer(buffer);
    }

    @Override
    public String toString() {
        //for drawing the board on the command line
        String resString = "  " + "+---".repeat(width) + "+";
        for (Square[] row: squares) {
            resString += "\n" + (8 - row[0].getY()) + " ";
            for (Square square: row) {
                String middle = " ";
                if (square.getPiece() != null) middle = square.getPiece().toString();
                resString += "| " + middle + " ";
            }
            resString += "|\n";
            resString += "  " + "+---".repeat(width) + "+";
        }
        resString += "\n    a   b   c   d   e   f   g   h";
        return resString;
    }
}
