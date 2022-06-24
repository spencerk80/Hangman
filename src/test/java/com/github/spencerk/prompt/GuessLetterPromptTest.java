package com.github.spencerk.prompt;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuessLetterPromptTest {

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
    public void enterNonLetter() {
        String[] output;

        System.setIn(new ByteArrayInputStream("-\n1".getBytes()));
        PromptFactory.getGuessLetterPrompt().run();

        output = TEST_OUT.toString().split("\n");

        assertEquals("Enter a letter to guess (1 to give up): ", output[output.length - 1]);
    }

    @Test
    public void enterSameLetterTwice() {
        String[] output;

        System.setIn(new ByteArrayInputStream("x".getBytes()));
        PromptFactory.getGuessLetterPrompt().run();
        System.setIn(new ByteArrayInputStream("x\n1".getBytes()));
        PromptFactory.getGuessLetterPrompt().run();

        output = TEST_OUT.toString().split("\n");

        assertEquals("You've already guessed x. Try again.", output[output.length - 2]);
    }

    @Test
    public void quit() {
        String[]    output;
        Prompt      returnedPrompt;

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        returnedPrompt = PromptFactory.getGuessLetterPrompt().run();

        assertTrue(returnedPrompt.getClass() == PromptFactory.getPlayAgainPrompt().getClass());
    }
}
