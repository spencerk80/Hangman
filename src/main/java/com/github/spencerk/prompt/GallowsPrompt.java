package com.github.spencerk.prompt;

import com.github.spencerk.IO.GallowsArtLoader;
import com.github.spencerk.gamedata.GameData;

public class GallowsPrompt implements Prompt {
    public Prompt run() {
        System.out.println(GallowsArtLoader.instance().getGallowsArt(GameData.data().getNumBadGuesses()));

        return PromptFactory.getGuessLetterPrompt();
    }
}
