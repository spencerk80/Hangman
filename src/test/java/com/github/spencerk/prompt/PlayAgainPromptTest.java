package com.github.spencerk.prompt;

import org.junit.jupiter.api.*;
import static  org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayAgainPromptTest {

    InputStream OG_IN;
    OutputStream OG_OUT;
    ByteArrayOutputStream TEST_OUT    = new ByteArrayOutputStream();

    //Change all input and output streams to test ones for IO testing
    @BeforeAll
    public void setUpTestIO() {
        OG_IN = System.in;
        OG_OUT = System.out;

        System.setOut(new PrintStream(TEST_OUT));
    }

    @BeforeEach
    public void clearStreams() {
        TEST_OUT.reset();

    }

    //Restore the streams to work on the CLI again
    @AfterAll
    public void restoreIO() {
        System.setIn(OG_IN);
        System.setOut(new PrintStream(OG_OUT));
    }

    @Test
    public void giveBadResponse() {
        //Bad response "fox" given then a good response to allow prompt to return
        System.setIn(new ByteArrayInputStream("fox\nno".getBytes()));
        PromptFactory.getPlayAgainPrompt().run();

        assertEquals("Would you like to play again? (yes/no)", TEST_OUT.toString().split("\n")[1]);
    }

    @Test
    public void answerYes() {
        Prompt returnedPrompt;

        System.setIn(new ByteArrayInputStream("yes".getBytes()));
        returnedPrompt = PromptFactory.getPlayAgainPrompt().run();

        assertTrue(returnedPrompt.getClass() == PromptFactory.getNewGamePrompt().getClass());
    }

    @Test
    public void answerNo() {
        Prompt returnedPrompt;

        System.setIn(new ByteArrayInputStream("no".getBytes()));
        returnedPrompt = PromptFactory.getPlayAgainPrompt().run();

        assertTrue(returnedPrompt == null);
    }

}
