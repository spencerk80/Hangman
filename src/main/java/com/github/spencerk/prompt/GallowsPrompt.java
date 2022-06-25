package com.github.spencerk.prompt;

import com.github.spencerk.gamedata.GameData;
import com.github.spencerk.util.Console;

public class GallowsPrompt implements Prompt {
    public Prompt run() {

        switch(GameData.data().getNumBadGuesses()) {
            case 0:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("  =================|=  \n");
                break;
            case 1:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("         / \\       |   ");
                System.out.println("        |x.x|      |   ");
                System.out.println("         \\-/       |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("  =================|=  \n");
                break;
            case 2:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("         / \\       |   ");
                System.out.println("        |x.x|      |   ");
                System.out.println("         \\-/       |   ");
                System.out.println("         | |       |   ");
                System.out.println("         | |       |   ");
                System.out.println("         | |       |   ");
                System.out.println("                   |   ");
                System.out.println("                   |   ");
                System.out.println("  =================|=  \n");
                break;
            case 3:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("         / \\       |   ");
                System.out.println("        |x.x|      |   ");
                System.out.println("         \\-/       |   ");
                System.out.println("         | |       |   ");
                System.out.println("         | |       |   ");
                System.out.println("         | |       |   ");
                System.out.println("            \\      |   ");
                System.out.println("             \\     |   ");
                System.out.println("  =================|=  \n");
                break;
            case 4:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("         / \\       |   ");
                System.out.println("        |x.x|      |   ");
                System.out.println("         \\-/       |   ");
                System.out.println("         | |       |   ");
                System.out.println("         | |       |   ");
                System.out.println("         | |       |   ");
                System.out.println("        /   \\      |   ");
                System.out.println("       /     \\     |   ");
                System.out.println("  =================|=  \n");
                break;
            case 5:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("         / \\       |   ");
                System.out.println("        |x.x|      |   ");
                System.out.println("         \\-/       |   ");
                System.out.println("         | |\\      |   ");
                System.out.println("         | | \\     |   ");
                System.out.println("         | |       |   ");
                System.out.println("        /   \\      |   ");
                System.out.println("       /     \\     |   ");
                System.out.println("  =================|=  \n");
                break;
            case 6:
                System.out.println("          ==========   ");
                System.out.println("          |        |   ");
                System.out.println("         / \\       |   ");
                System.out.println("        |x.x|      |   ");
                System.out.println("         \\-/       |   ");
                System.out.println("        /| |\\      |   ");
                System.out.println("       / | | \\     |   ");
                System.out.println("         | |       |   ");
                System.out.println("        /   \\      |   ");
                System.out.println("       /     \\     |   ");
                System.out.println("  =================|=  \n");
                break;
            default:
                System.err.println("Error: Invalid hanging stage given!");
        }

        return PromptFactory.getGuessLetterPrompt();
    }
}
