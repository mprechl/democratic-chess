package dem_chess;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class IO {
    private static final File saveDir = new File(System.getProperty("user.dir"), "saves");

    /**
     * save the game through serialisation
     * @param board Board object to be saved
     */
    public static void save(Board board) {
        //find next empty save slot
        String saveName = null;
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(saveDir.list()));
        for (int i = 0; i < 1000; i++) {
            String iString = String.format("%03d", i); //zero-padded string eg. 001
            if (!fileNames.contains("save" + iString + ".ser")) {
                saveName = saveDir.getPath() + File.separator + "save"+ iString + ".ser";
                break;
            }
        }

        if (saveName == null) System.out.println("no empty save slot available, please delete some save files");
        else {
            System.out.println("saving as " + saveName);
            try {
                FileOutputStream file = new FileOutputStream(saveName);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(board);
                out.close();
            } catch (IOException e) {
                System.err.println(e);
            }
            System.out.println("saved successfully");
        }
    }

    /**
     * deserialisation of previously saved game
     * @param cmd the file to load is provided as a parameter
     * @return returns the loaded Board object
     */
    public static Board load(String[] cmd) {
        Board result_board = null;

        //make sure parameter is provided
        if (cmd.length == 2) {
            String fileName = null;
            //if parameter is latest find which file it is
            if (cmd[1].equals("latest")) {
                //find largest numbered save file
                ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(saveDir.list()));
                for (int i = 999; i >= 0; i--) {
                    String iString = String.format("%03d", i); //zero-padded string eg. 001
                    if (fileNames.contains("save" + iString + ".ser")) {
                        fileName = saveDir.getPath() + File.separator + "save"+ iString + ".ser";
                        break;
                    }
                }
            //otherwise make sure correct filename is provided
            } else if (cmd[1].length() == 7 && cmd[1].startsWith("save")
                    && Character.isDigit(cmd[1].charAt(4))
                    && Character.isDigit(cmd[1].charAt(5))
                    && Character.isDigit(cmd[1].charAt(6))) {
                fileName = saveDir.getPath() + File.separator + cmd[1] + ".ser";
            } else {
                System.out.println("incorrect savefile name provided");
                return null;
            }

            //load the file and continue the game
            if (fileName != null) {
                System.out.println("loading " + fileName);
                try {
                    FileInputStream file = new FileInputStream(fileName);
                    ObjectInputStream in = new ObjectInputStream(file);
                    result_board = (Board) in.readObject();
                    in.close();
                } catch (IOException e) {
                    System.err.println(e);
                } catch (ClassNotFoundException e) {
                    System.err.println(e);
                }
            } else System.out.println("no savefile found");
        } else System.out.println("no savefile specifed, use 'load saveXXX' or 'load latest'");

        return result_board;
    }

}
