package com.github.spencerk.IO;

import com.github.spencerk.prompt.GallowsPrompt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GallowsArtLoader {
    private static final List<String>       gallowsArt = new ArrayList<>();
    private static       GallowsArtLoader   instance;

    private GallowsArtLoader() {/*Get from instance singleton*/}

    public static GallowsArtLoader instance() {
        if(instance == null) instance = new GallowsArtLoader();
        return instance;
    }

    public String getGallowsArt(byte stage) {
        if(gallowsArt.size() == 0) loadFromFile();
        return gallowsArt.get(stage);
    }

    private void loadFromFile() {
        File    gallowsArtFile  = new File("src/main/resources/gallows-art.txt");
        Scanner scanner;

        try {
            scanner = new Scanner(gallowsArtFile);

            scanner.useDelimiter("\n\n");
            while(scanner.hasNext()) gallowsArt.add(scanner.next());
        } catch(IOException e) {
            System.err.println("Error: Unable to load gallows art. Defaulting to descriptions...");
            defaultArtOnIOFail();
        }
    }

    //If file IO fails, show a description instead. Better than crashing or no display
    private void defaultArtOnIOFail() {
        gallowsArt.add("Empty_gallows");
        gallowsArt.add("Head");
        gallowsArt.add("Head body");
        gallowsArt.add("Head body right_arm");
        gallowsArt.add("Head body right_arm left_arm");
        gallowsArt.add("Head body right_arm left_arm right_leg");
        gallowsArt.add("Head body right_arm left_arm right leg left_leg");
    }
}
