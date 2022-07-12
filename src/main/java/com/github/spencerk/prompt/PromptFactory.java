package com.github.spencerk.prompt;

public class PromptFactory {
    private static GallowsPrompt    gallowsPrompt;
    private static NewGamePrompt    newGamePrompt;
    private static PlayAgainPrompt  playAgainPrompt;
    private static GuessLetterPrompt guessLetterPrompt;
    private static GetPlayerNamePrompt getPlayerNamePrompt;

    public static GallowsPrompt getGallowsPrompt() {
        if(gallowsPrompt == null) gallowsPrompt = new GallowsPrompt();
        return gallowsPrompt;
    }

    public static NewGamePrompt getNewGamePrompt() {
        if(newGamePrompt == null) newGamePrompt = new NewGamePrompt();
        return newGamePrompt;
    }

    public static PlayAgainPrompt getPlayAgainPrompt() {
        if(playAgainPrompt == null) playAgainPrompt = new PlayAgainPrompt();
        return playAgainPrompt;
    }

    public static GuessLetterPrompt getGuessLetterPrompt() {
        if(guessLetterPrompt == null) guessLetterPrompt = new GuessLetterPrompt();
        return guessLetterPrompt;
    }

    public static GetPlayerNamePrompt getGetPlayerNamePrompt() {
        if(getPlayerNamePrompt == null) getPlayerNamePrompt = new GetPlayerNamePrompt();
        return getPlayerNamePrompt;
    }
}
