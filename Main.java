package dem_chess;

import java.util.Scanner;

public class Main {
    private static Board board;

    /**
     * create a new game with a clear board
     */
    public static void newGame() {
        board = new Board();
        System.out.println("P = white pawn, p = black pawn");

        nextTurn(false);
    }

    /**
     * ask the piece selected by the player to make a random move
     * @param cmd the selected coordinates are given as parameter
     */
    public static void move(String[] cmd) {
        //check if game is running and correct number of parameters are present
        if (board != null && cmd.length == 2) {
            //convert coordinates
            String abc = "abcdefgh";
            String inputCoords = cmd[1];
            char[] inputChars = inputCoords.toCharArray();
            int x = abc.indexOf(inputChars[0]);
            int y = 8 - Character.getNumericValue(inputChars[1]);

            //check if correct colour piece is on selected square
            if (board.getSquares()[y][x].getPiece() != null) {
                //is there even a piece there
                if (board.getSquares()[y][x].getPiece().getColour().equals(board.getNextPlayer())) {
                    System.out.println("correct selection");
                    if (board.getSquares()[y][x].getPiece().move()) {
                        System.out.println("move made");
                        nextTurn(true);
                    } else {
                        System.out.println("no move can be made, please choose a different piece");
                    }
                } else {
                    System.out.println("wrong colour piece selected, please choose correct colour piece");
                }
            } else {
                System.out.println("no piece selected, please choose a square with a piece");
            }
        }
    }

    /**
     * draw game status and if advanceFlag is set, advance the game
     * @param advanceFlag if false, just draw the game board wo/ advancing the game
     */
    public static void nextTurn(boolean advanceFlag) {
        System.out.println(board);
        if (advanceFlag) board.advance();
        System.out.println(board.getNextPlayer() + " player's turn, please choose a piece to move by" +
                " typing 'move' followed by its coordinates eg. 'move a2'");
    }

    /**
     * prints the help message to command line
     */
    public static void help() {
        System.out.println("Commands:\nexit: quit the game\nnew: start a new game" +
                "\nmove [coords]: if a game is running, this command selects a piece to move" +
                "\nsave: saves the current state of the game for later use" +
                "\nload [saveXXX or latest]: loads specified (or the latest) savefile" +
                "\nhelp: displays this page");
    }

    /**
     * exits the program, but if a game is running require extra confirmation
     * @param cmd extra confirmation is provided as a parameter
     * @return returns true if the exit should go through
     */
    public static boolean exit(String[] cmd) {
        if (board == null) return true;
        if (cmd.length == 2 && cmd[1].equals("yes")) return true;

        System.out.println("the game is currently running, any unsaved progress will be lost, " +
                "are you sure you want to quit?" +
                "\ntype 'exit yes' to confirm or save the game by typing 'save' or continue playing");
        return false;
    }

    /**
     * main loop, where command line interaction happens
     * @param args
     */
    public static void main(String[] args) {
        //introduction
        System.out.println("Welcome to Democratic Chess!");
        System.out.println("Commands: exit, new, move, save, load, help");

        //command line interaction
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            String[] cmd = line.split(" ");
            if (cmd[0].equals("exit")) {
                if (exit(cmd)) break;
            }
            else if (cmd[0].equals("new")) newGame();
            else if (cmd[0].equals("move")) move(cmd);
            else if (cmd[0].equals("help")) help();
            else if (cmd[0].equals("save")) IO.save(board);
            else if (cmd[0].equals("load")) {
                Board res = IO.load(cmd);
                if (res != null) {
                    board = res;
                    nextTurn(false);
                }
            } else System.out.println("unknown command entered, please try again (type 'help' for available commands)");
        }
        scanner.close();
    }
}
