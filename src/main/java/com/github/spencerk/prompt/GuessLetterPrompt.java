package com.github.spencerk.prompt;

import com.github.spencerk.gamedata.GameData;
import com.github.spencerk.util.Console;

import java.util.List;
import java.util.Scanner;

public class GuessLetterPrompt implements Prompt {
    private Scanner scanner;

    public Prompt run() {
        List<Character> incorrectLetters    = GameData.data().getAttempts();
        String          blankWord           = GameData.data().getBlankWord();
        char            guess;

        //After gallows prints, check to see if game is over
        if(GameData.data().getNumBadGuesses() == 6) {
            System.out.println("Sorry, you've died...");
            return PromptFactory.getPlayAgainPrompt();
        }

        //Required to be reconstructed every time for testing
        scanner = new Scanner(System.in);

        //Print out the progress on the blank word
        System.out.print("Secret word: ");
        blankWord.chars().forEach(c -> System.out.printf("%c ", c));
        System.out.println("\n");

        //Print out the incorrect guesses
        System.out.print("Incorrect guesses:");
        for(char c : incorrectLetters) System.out.printf(" %c", c);
        System.out.println("\n");

        //Get new guess
        do {
            System.out.print("Enter a letter to guess (1 to give up): ");
            guess = scanner.nextLine().trim().toLowerCase().charAt(0);
            System.out.println();

            if(incorrectLetters.contains(guess)) System.out.printf("You've already guessed %c. Try again.%n", guess);
        } while(
                !(guess + "").matches("[a-z]|1")
                || incorrectLetters.contains(guess)
        );

        //Mostly as a way to make testing easier
        if(guess == '1') return PromptFactory.getPlayAgainPrompt();

        //Process guess
        GameData.data().makeAttempt(guess);

        //Is game over?
        if(GameData.data().gameWon()) { //Game won
            System.out.println(GameData.data().getBlankWord());
            System.out.println("You did it! You've guessed it right!");
            return PromptFactory.getPlayAgainPrompt();
        }

        //Game continues
        Console.clearScreen();
        return PromptFactory.getGallowsPrompt();

    }
}
