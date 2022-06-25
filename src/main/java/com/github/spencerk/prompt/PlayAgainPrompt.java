package com.github.spencerk.prompt;

import com.github.spencerk.gamedata.GameData;
import com.github.spencerk.util.Console;

import java.util.Scanner;

public class PlayAgainPrompt implements Prompt {

    private Scanner scanner;
    public Prompt run() {
        String response;

        //Required to make new one each time for tests to work
        scanner = new Scanner(System.in);

        do {
            System.out.println("Would you like to play again? (yes/no)");
            response = scanner.nextLine().trim().toLowerCase();
        } while( ! "yes".equals(response) && ! "no".equals(response));

        if("yes".equals(response)) {
            GameData.data().reset();
            Console.clearScreen();
            return PromptFactory.getNewGamePrompt();
        }
        return null;
    }
}
