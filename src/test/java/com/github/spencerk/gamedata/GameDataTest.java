package com.github.spencerk.gamedata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameDataTest {

    //Just a shortcut to this to shorten lines/less typing
    private GameData gameData = GameData.data();
    private String   secret   = "fox";

    //Reset the data before every test so every test can assume data is in a fresh state
    @BeforeEach
    public void resetData() {
        gameData.reset();
    }

    //Set secret to a known word for testing purposes
    private void setSecret() {
        Field secretField = null;
        Field blankField  = null;
        StringBuilder sb = new StringBuilder("");
        String blank;

        //Build blank word
        for(byte i = 0; i < secret.length(); i++) sb.append('_');
        blank = sb.toString();

        try {
            secretField = gameData.getClass().getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(gameData, secret);

            blankField = gameData.getClass().getDeclaredField("blankWord");
            blankField.setAccessible(true);
            blankField.set(gameData, blank);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: Tests failed to set secret to known value!");
            System.err.println(e.getMessage());
        }

    }

    private void setRandomList() {
        Field secrets = null;

        try {
            secrets = gameData.getClass().getDeclaredField("secretWords");
            secrets.setAccessible(true);
            secrets.set(gameData, new String[]{"fox", "cat"});
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: Tests failed to set secret list to easily tested values!");
            System.err.println(e.getMessage());
        }

        //Needed to make it pick a secret from the new list
        gameData.reset();

    }

    private void resetGameDataSecrets() {
        Field instance = null;

        //by setting the instance to null and calling the data() method, it'll recreate the object with original
        //settings
        try {
            instance = gameData.getClass().getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(gameData, null);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: Tests failed to restore the secret list originally given!");
            System.err.println(e.getMessage());
        }

        //Recreate
        gameData = GameData.data();
    }

    @Test
    public void newDataSetUp() {
        boolean allBlank = true;
        String  blankWord = gameData.getBlankWord();

        for(byte i = 0; i < blankWord.length(); i++) if(blankWord.charAt(i) != '_') {
            allBlank = false;
            break;
        }

        assertTrue(gameData.getAttempts().isEmpty());
        assertTrue( ! gameData.gameWon());
        assertTrue(allBlank);

    }

    @Test
    public void giveWrongGuess() {
        boolean result;
        List<Character> attempts = gameData.getAttempts();

        setSecret();
        result = gameData.makeAttempt('q');

        assertTrue( ! result);
        assertTrue(gameData.getNumBadGuesses() == 1);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 1);
        assertEquals('q', attempts.get(0));
        assertEquals("___", gameData.getBlankWord());
    }

    @Test
    public void giveSeveralWrongGuesses() {
        boolean result;
        List<Character> attempts = gameData.getAttempts();

        setSecret();

        result = gameData.makeAttempt('q');

        assertTrue( ! result);
        assertTrue(gameData.getNumBadGuesses() == 1);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 1);
        assertEquals('q', attempts.get(0));
        assertTrue(gameData.getBlankWord().equals("___"));

        result = gameData.makeAttempt('u');

        assertTrue( ! result);
        assertTrue(gameData.getNumBadGuesses() == 2);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 2);
        assertEquals('u', attempts.get(1));
        assertTrue(gameData.getBlankWord().equals("___"));

        result = gameData.makeAttempt('i');

        assertTrue( ! result);
        assertTrue(gameData.getNumBadGuesses() == 3);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 3);
        assertEquals('i', attempts.get(2));
        assertTrue(gameData.getBlankWord().equals("___"));

        result = gameData.makeAttempt('t');

        assertTrue( ! result);
        assertTrue(gameData.getNumBadGuesses() == 4);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 4);
        assertEquals('t', attempts.get(3));
        assertTrue(gameData.getBlankWord().equals("___"));
    }

    @Test
    public void giveGoodGuess() {
        boolean result;
        List<Character> attempts = gameData.getAttempts();

        setSecret();
        result = gameData.makeAttempt('f');

        assertTrue(result);
        assertTrue(gameData.getNumBadGuesses() == 0);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 0);
        assertTrue(gameData.getBlankWord().equals("f__"));
    }

    @Test
    public void oneLetterToManyBlanks() {
        boolean result;
        List<Character> attempts = gameData.getAttempts();

        secret = "sleeplessness";
        setSecret();
        result = gameData.makeAttempt('e');

        assertTrue(result);
        assertTrue(gameData.getNumBadGuesses() == 0);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 0);
        assertTrue(gameData.getBlankWord().equals("__ee__e___e__"));

        result = gameData.makeAttempt('s');

        assertTrue(result);
        assertTrue(gameData.getNumBadGuesses() == 0);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 0);
        assertTrue(gameData.getBlankWord().equals("s_ee__ess_ess"));

        //Restore for other tests
        secret = "fox";
    }

    @Test
    public void solveWord() {
        boolean result;
        List<Character> attempts = gameData.getAttempts();

        setSecret();

        result = gameData.makeAttempt('f');

        assertTrue(result);
        assertTrue(gameData.getNumBadGuesses() == 0);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 0);
        assertTrue(gameData.getBlankWord().equals("f__"));

        result = gameData.makeAttempt('x');

        assertTrue(result);
        assertTrue(gameData.getNumBadGuesses() == 0);
        assertTrue( ! gameData.gameWon());
        assertTrue(attempts.size() == 0);
        assertTrue(gameData.getBlankWord().equals("f_x"));

        result = gameData.makeAttempt('o');

        assertTrue(result);
        assertTrue(gameData.getNumBadGuesses() == 0);
        assertTrue(gameData.gameWon());
        assertTrue(attempts.size() == 0);
        assertTrue(gameData.getBlankWord().equals("fox"));
    }

    @Test
    public void solveRandomWord() {
        boolean result;
        List<Character> attempts;

        setRandomList();
        attempts = gameData.getAttempts();

        result = gameData.makeAttempt('f');

        if(result) {

            assertTrue(gameData.getNumBadGuesses() == 0);
            assertTrue( ! gameData.gameWon());
            assertTrue(attempts.size() == 0);
            assertTrue(gameData.getBlankWord().equals("f__"));

            result = gameData.makeAttempt('x');

            assertTrue(result);
            assertTrue(gameData.getNumBadGuesses() == 0);
            assertTrue( ! gameData.gameWon());
            assertTrue(attempts.size() == 0);
            assertTrue(gameData.getBlankWord().equals("f_x"));

            result = gameData.makeAttempt('o');

            assertTrue(result);
            assertTrue(gameData.getNumBadGuesses() == 0);
            assertTrue(gameData.gameWon());
            assertTrue(attempts.size() == 0);
            assertTrue(gameData.getBlankWord().equals("fox"));

        } else {

            assertTrue(!result);
            assertTrue(gameData.getNumBadGuesses() == 1);
            assertTrue(!gameData.gameWon());
            assertTrue(attempts.size() == 1);
            assertEquals('f', attempts.get(0));
            assertTrue(gameData.getBlankWord().equals("___"));

            result = gameData.makeAttempt('c');

            assertTrue(gameData.getNumBadGuesses() == 1);
            assertTrue(!gameData.gameWon());
            assertTrue(attempts.size() == 1);
            assertTrue(gameData.getBlankWord().equals("c__"));

            result = gameData.makeAttempt('t');

            assertTrue(result);
            assertTrue(gameData.getNumBadGuesses() == 1);
            assertTrue(!gameData.gameWon());
            assertTrue(attempts.size() == 1);
            assertTrue(gameData.getBlankWord().equals("c_t"));

            result = gameData.makeAttempt('a');

            assertTrue(result);
            assertTrue(gameData.getNumBadGuesses() == 1);
            assertTrue(gameData.gameWon());
            assertTrue(attempts.size() == 1);
            assertTrue(gameData.getBlankWord().equals("cat"));

        }

        //Reset the data for other tests
        resetGameDataSecrets();

    }

    @Test
    public void gameChoosesNewRandomWord() {
        Field   secretField = null;
        String  prevWord    = "",
                secret      = null;
        byte    badChange   = 0;

        try {
            secretField = gameData.getClass().getDeclaredField("secret");
            secretField.setAccessible(true);
        } catch(NoSuchFieldException nsfe) {
            System.err.println("Error: Tests could not access secret word!");
            System.err.println(nsfe.getMessage());
        }

        for(byte i = 0; i < 5; i++) try {
            secret = (String) secretField.get(gameData);
            if( ! prevWord.equals(secret)) badChange++;
            prevWord = secret;
        } catch(IllegalAccessException iae) {
            System.err.println("Error: Tests could not access secret word!");
            System.err.println(iae.getMessage());
        }

        //Check if secret didn't change at all
        //Note: though the loop iterates 5 times, the embedded if-statement will always be false first go
        //hence the 4 below
        assertTrue(badChange < 4);
    }

}
