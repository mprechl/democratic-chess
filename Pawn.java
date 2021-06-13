package dem_chess;

import java.util.ArrayList;
import java.util.Random;

public class Pawn extends Piece{
    public Pawn(String colour) {
        this.colour = colour;
    }

    /**
     * randomly selects a valid move and makes the move
     * @return returns false if no valid move can be made, true otherwise
     */
    @Override
    public boolean move() {
        ArrayList<Square> validGoals = getValidMoves();
        System.out.println(validGoals.size() + " options available");
        //return false if there are no options
        if (validGoals.size() == 0) return false;

        for (Square goal : validGoals) {
            System.out.println(goal);
        }
        Random random = new Random();
        //otherwise choose a move randomly
        Square goal = validGoals.get(random.nextInt(validGoals.size()));
        System.out.println("random choice: " + goal);

        //actually make the move
        square.clearPiece();
        goal.setPiece(this);
        return true;
    }

    /**
     * collects all valid moves, including capturing an enemy piece
     * @return ArrayList w/ Square objects onto which valid moves can be made
     */
    private ArrayList<Square> getValidMoves() {
        ArrayList<Square> validGoals = new ArrayList<Square>();

        //variables that depend on what colour piece this object is
        //TODO move outside of method?
        String enemy = "black";
        int offset = -1;
        if (colour.equals("black")) {
            enemy = "white";
            offset = 1;
        }

        int x = square.getX();
        int y = square.getY();
        Square[][] squares = board.getSquares();

        //check if square in front is clear
        //deal with index out of range
        if (((y + offset) <= 7) && ((y + offset) >= 0)) {
            if (squares[y + offset][x].getPiece() == null) {
                //it's a valid move
                validGoals.add(squares[y + offset][x]);
                //if on starting line, check the square behind it as well
                //no need to check for out of range, because we're on the starting line
                if (((colour.equals("black") && y == 1) || (colour.equals("white") && y == 7 - 1))
                        && (squares[y + 2 * offset][x].getPiece() == null)) {
                    validGoals.add(squares[y + 2 * offset][x]);
                }
            }

            //check valid captures
            //deal with index out of range for x as well
            //capture to the left
            if (((x - 1) >= 0) && squares[y + offset][x - 1].getPiece() != null) {
                //is there even a piece there?
                if (squares[y + offset][x - 1].getPiece().getColour().equals(enemy)) {
                    //and is it an enemy?
                    validGoals.add(squares[y + offset][x - 1]);
                }
            }
            //capture to the right
            if (((x + 1) <= 7) && squares[y + offset][x + 1].getPiece() != null) {
                if (squares[y + offset][x + 1].getPiece().getColour().equals(enemy)) {
                    validGoals.add(squares[y + offset][x + 1]);
                }
            }
        }
        return validGoals;
    }

    @Override
    public String toString() {
        if (colour.equals("white")) return "P";
        else return "p";
    }
}
