package com.github.spencerk.prompt;

public class NewGamePrompt implements Prompt {
    public Prompt run() {
        System.out.println("Welcome to Hangman!");
        System.out.println("Try to guess the word I'm thinking of. If you make 6 mistakes, you're dead!\n");

        return PromptFactory.getGallowsPrompt();
    }
}
