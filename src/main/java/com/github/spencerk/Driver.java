package com.github.spencerk;

import com.github.spencerk.prompt.Prompt;
import com.github.spencerk.prompt.PromptFactory;

public class Driver {
    public static void main(String args[]) {
        Prompt prompt = PromptFactory.getNewGamePrompt();

        while(prompt != null) prompt = prompt.run();

        System.out.println("Thanks for playing. Good bye.");
    }
}
