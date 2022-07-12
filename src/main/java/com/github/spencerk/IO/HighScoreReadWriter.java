package com.github.spencerk.IO;

import com.github.spencerk.gamedata.GameData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HighScoreReadWriter {
    public static final String                  SAVE_FILE           = "high-scores.txt";
    private static      HighScoreReadWriter     instance;
    private             byte                    highScore           = 127; //Basically 0 if score were ascending

    private HighScoreReadWriter() {}

    public static HighScoreReadWriter getInstance() {
        if(instance == null) instance = new HighScoreReadWriter();
        return instance;
    }

    public String readSaveRecord() {
        return readSaveRecord(SAVE_FILE);
    }

    public String readSaveRecord(String filename) {
        File        file        = new File(filename);
        Scanner     scanner;
        String      data        = null;

        //File may not exist
        if( ! file.exists()) return "No record is set.";

        try {
            scanner = new Scanner(file);

            data = scanner.nextLine();
            highScore = Byte.parseByte(data.substring(data.indexOf(':') + 2));
        } catch(IOException | NumberFormatException e) {
            System.err.println("Error: Could not read from save file!");
            System.err.println(e.getMessage());
        }

        return data == null ? "" : data;
    }

    public boolean writeSaveRecord() {
        return writeSaveRecord(SAVE_FILE);
    }

    public boolean writeSaveRecord(String filename) {
        PrintWriter writer;

        //Do not record if not highest and newest record
        if(GameData.data().getNumBadGuesses() > highScore) { return false; }

        try {
            //Do not append, overwrite
            writer = new PrintWriter(new FileWriter(filename), false);

            writer.printf(
                    "%s has the high score of: %d\n",
                    GameData.data().getPlayerName(),
                    GameData.data().getNumBadGuesses()
            );
            writer.close();
        } catch(IOException e) {
            System.err.println("Error: Could not save record to file~");
            System.err.println(e.getMessage());
        }

        //Player had highest score
        return true;
    }
}
