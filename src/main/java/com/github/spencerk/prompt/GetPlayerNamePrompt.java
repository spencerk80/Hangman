package com.github.spencerk.prompt;

import com.github.spencerk.gamedata.GameData;
import com.github.spencerk.util.Console;

import java.util.Scanner;

public class GetPlayerNamePrompt implements Prompt {

    private Scanner scanner;
    @Override
    public Prompt run() {
        char input;
        String name, inputStr;

        scanner = new Scanner(System.in);

        do {
            do {
                System.out.print("Enter your name: ");
                name = scanner.nextLine().trim();
                System.out.println();
            } while("".equals(name));

            do {
                System.out.printf("%s. Are you sure that's your name?(y/n): ", name);
                inputStr = scanner.nextLine().trim().toLowerCase();
                input = "".equals(inputStr) ? 0 : inputStr.charAt(0); //Set char to null terminator if string empty
                System.out.println();
            } while(input != 'y' && input != 'n');
        } while(input != 'y');

        GameData.data().setPlayerName(name);

        Console.clearScreen();
        return PromptFactory.getGallowsPrompt();
    }
}
