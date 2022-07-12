package com.github.spencerk.IO;

import com.github.spencerk.prompt.GallowsPrompt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GallowsArtLoader {
    private static final    List<String>       gallowsArt   = new ArrayList<>();
    private static final    byte               PROJECT_DIR  = 0,
                                               JAR_DIR      = 1;
    private static final    String[]           directories  = {"src/main/resources/gallows-art.txt", "/gallows-art.txt"};
    private static          GallowsArtLoader   instance;

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
        File    gallowsArtFile  = new File(directories[PROJECT_DIR]);
        Scanner scanner;

        //Try either directory
        if( ! gallowsArtFile.exists()) {
            loadFromJar();
            return;
        }

        try {
            scanner = new Scanner(gallowsArtFile);

            scanner.useDelimiter(":");
            while(scanner.hasNext()) gallowsArt.add(scanner.next());
        } catch(IOException e) {
            System.err.println("Error: Unable to load gallows art. Defaulting to descriptions...");
            defaultArtOnIOFail();
        }
    }

    private void loadFromJar() {
        BufferedReader  reader  = new BufferedReader(new InputStreamReader(
                                                            getClass().getResourceAsStream(directories[JAR_DIR])
                                                        ));
        StringBuilder   sb      = new StringBuilder();
        String          line;

        try {

            while((line = reader.readLine()) != null) sb.append(String.format("%s\n", line));
        } catch(IOException e) {
            System.err.println("Error: Unable to load gallows art. Defaulting to descriptions...");
            defaultArtOnIOFail();
        }

        if(sb.length() > 0) formatInputStreamData(sb.toString());
        else {
            System.err.println("Error: Failed to read data for gallows art!");
            defaultArtOnIOFail();
        }
    }

    private void formatInputStreamData(String data) {
        Arrays.stream(data.split(":")).forEach(gallows -> gallowsArt.add(gallows));
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
