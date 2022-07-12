package com.github.spencerk.prompt;

import com.github.spencerk.IO.HighScoreReadWriter;

public class NewGamePrompt implements Prompt {
    public Prompt run() {
        System.out.println("Welcome to Hangman!");
        System.out.println("Try to guess the word I'm thinking of. If you make 6 mistakes, you're dead!\n");
        System.out.printf("\nHigh score record: %s\n", HighScoreReadWriter.getInstance().readSaveRecord());

        return PromptFactory.getGetPlayerNamePrompt();
    }
}
