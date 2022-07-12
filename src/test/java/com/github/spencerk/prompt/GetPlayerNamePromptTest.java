package com.github.spencerk.prompt;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetPlayerNamePromptTest {
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
    public void enterNoName() {
        System.setIn(new ByteArrayInputStream("\nKris\ny\n".getBytes()));
        PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt will just ask again
        assertEquals("Enter your character's name: ", TEST_OUT.toString().split("\n")[1]);
    }

    @Test
    public void enterNothingOnConfirmation() {
        System.setIn(new ByteArrayInputStream("Kris\n\ny\n".getBytes()));
        PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt should just ask again
        assertEquals("Kris. Are you sure that's your name?(y/n): ", TEST_OUT.toString().split("\n")[2]);
    }

    @Test
    public void enterInvalidConfirm() {
        System.setIn(new ByteArrayInputStream("Kris\nG\ny\n".getBytes())); //G is invalid
        PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt should just ask again
        assertEquals("Kris. Are you sure that's your name?(y/n): ", TEST_OUT.toString().split("\n")[2]);
    }

    @Test
    public void changeName() {
        Prompt returnedPrompt;

        System.setIn(new ByteArrayInputStream("Kris\nn\nKristoffer\ny\n".getBytes()));
        returnedPrompt = PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt should just ask again
        assertEquals("Kris. Are you sure that's your name?(y/n): ", TEST_OUT.toString().split("\n")[1]);
        assertEquals("Enter your character's name: ", TEST_OUT.toString().split("\n")[2]);
        assertEquals(
                "Kristoffer. Are you sure that's your name?(y/n): ",
                TEST_OUT.toString().split("\n")[3]
        );
        assertSame(returnedPrompt.getClass(), GallowsPrompt.class);
    }
}
