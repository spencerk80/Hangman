package com.github.spencerk.gamedata;

import one.util.streamex.EntryStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class GameData {

    private final Random    random;
    private final String[]  secretWords = { "cat", "dog", "person", "fox", "pokemon", "hamburger", "computer", "java",
                                            "javascript", "typescript", "react", "enchilada", "cellphone", "cup",
                                            "bottle", "horse", "pony", "game", "apple", "banana", "bread", "pizza",
                                            "hippopotomonstrosesquippedaliophobia"}; //Yes it's a real word
    private String          secret      = "",
                            blankWord   = "",
                            playerName  = "";
    private List<Character> attempts    = null;
    private byte            numBadGuesses = 0;
    private static GameData instance    = null;

    /*------------------------------------------------------------*
     * Constructor and instance                                   *
     *------------------------------------------------------------*/

    //Not allowed to construct this. Ask for the single instance by data method
    private GameData() {
        random      = new Random();
        reset();
    }

    //Get the single instance of game data by calling this
    public static GameData data() {
        if(instance == null) instance = new GameData();
        return instance;
    }

    /*------------------------------------------------------------*
     * The game running methods                                   *
     *------------------------------------------------------------*/

    //This method will take the guessed letter and update everything accordingly
    //Returns whether the letter was good or not
    public boolean makeAttempt(char letter) {

        //If letter is not in the word
        if( ! addLetterToBlank(letter)) {
            numBadGuesses++;
            attempts.add(letter);
            return false;
        }

        return true;
    }

    //Check whether win condition is met i.e. the word has no blanks
    public boolean gameWon() {
        return blankWord.indexOf('_') < 0;
    }

    //Set up the data for a new game
    public void reset() {
//        StringBuilder sb = new StringBuilder("");
//
        secret = secretWords[random.nextInt(secretWords.length)];
//
//        for(int i = 0; i < secret.length(); i++) sb.append('_');
//
//        blankWord = sb.toString();

        blankWord = secret.chars().boxed().map(c -> '_').map(Object::toString).collect(Collectors.joining());

        attempts = new ArrayList<>();
        numBadGuesses = 0;
    }

    /*------------------------------------------------------------*
     * Private helper methods
     *------------------------------------------------------------*/

    private boolean addLetterToBlank(char c) {

        //I am not a fan of this solution. It's way more CPU heavy and mem heavy. But it satisfies the new requirements

        //Get indices of the letter occurring in the secret word
        List<Integer> indices = IntStream.range(0, secret.length())
                .filter(i -> secret.charAt(i) == c)
                .boxed()
                .collect(toList());

        //Check whether the letter is in the word
        if(indices.size() == 0) return false;

        //Insert letter at the indices
        blankWord = EntryStream.of(blankWord.chars()
                        .boxed()
                        .collect(toList()))
                .mapKeyValue((i,ch) -> indices.contains(i) ? c : ch)
                .map(ch -> String.format("%c", ch))
                .collect(Collectors.joining());

        return true;

//        byte    occurrences = (byte) secret.chars().filter(l -> l == c).count(),
//                index       = 0;
//        byte[]  indices     = null;
//        char[]  blanks      = null;
//
//        //The guessed letter isn't in the secret
//        if(occurrences == 0) return false;
//
//        //Make space to hold the indices of the occurring guess letter
//        indices = new byte[occurrences];
//        //Find the indices
//        for(byte i = 0; i < secret.length(); i++)
//            if(secret.charAt(i) == c)
//                indices[index++] = i;
//
//        //Prepare blanks for letter replacement
//        blanks = blankWord.toCharArray();
//
//        //Replace blanks with the letter
//        for(byte i : indices) blanks[i] = c;
//        blankWord = String.valueOf(blanks);
//
//        return true;
    }

    /*------------------------------------------------------------*
     * Getters                                                    *
     *------------------------------------------------------------*/

    public String getBlankWord() {
        return blankWord;
    }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public List<Character> getAttempts() {
        return attempts;
    }

    public byte getNumBadGuesses() {
        return numBadGuesses;
    }
}
