package com.github.spencerk.IO;

import com.github.spencerk.gamedata.GameData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HighScoreReadWriterTest {
    private static final String     TEST_FILE       = "test-record.txt";

    @AfterAll
    public void cleanup() {
        eraseFile();
        GameData.data().reset();
    }

    private void eraseFile() {
        File    file    = new File(TEST_FILE);

        if(file.exists()) file.delete();
        resetHighScore();
    }

    private void resetHighScore() {
        Field   score;

        try {
            score = HighScoreReadWriter.class.getDeclaredField("highScore");
            score.setAccessible(true);
            score.set(HighScoreReadWriter.getInstance(), (byte) 127);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Tests could not reset score!");
            System.err.println(e.getMessage());
        }
    }

    private void createSaveWithRecord() {
        GameData.data().reset();
        GameData.data().setPlayerName("Test User");

        //Make a couple bad tries to change score
        GameData.data().makeAttempt('3');
        GameData.data().makeAttempt('6');

        HighScoreReadWriter.getInstance().writeSaveRecord(TEST_FILE);
    }

    @Test
    public void readNonExistentFile() {
        //In case this isn't the first test run
        eraseFile();

        assertEquals("No record is set.", HighScoreReadWriter.getInstance().readSaveRecord(TEST_FILE));
    }

    @Test
    public void readRecord() {
        createSaveWithRecord();

        assertEquals(
                "Test User has the high score of: 2",
                HighScoreReadWriter.getInstance().readSaveRecord(TEST_FILE)
        );
    }

    @Test
    public void writeNewFile() {
        Scanner     scanner;

        eraseFile();
        GameData.data().reset();
        GameData.data().setPlayerName("Test User");

        assertTrue(HighScoreReadWriter.getInstance().writeSaveRecord(TEST_FILE));

        try {
            scanner = new Scanner(new File(TEST_FILE));

            assertEquals("Test User has the high score of: 0", scanner.nextLine());
        } catch(IOException e) {
            assertTrue(false);
        }
    }

    @Test
    public void overWriteOldRecord() {
        Scanner     scanner;

        createSaveWithRecord();

        GameData.data().reset();
        GameData.data().setPlayerName("Test User");

        assertTrue(HighScoreReadWriter.getInstance().writeSaveRecord(TEST_FILE));

        try {
            scanner = new Scanner(new File(TEST_FILE));

            assertEquals("Test User has the high score of: 0", scanner.nextLine());
        } catch(IOException e) {
            assertTrue(false);
        }
    }
}
