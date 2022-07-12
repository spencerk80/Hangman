package com.github.spencerk;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectPackages({
        "com.github.spencerk.gamedata",
        "com.github.spencerk.prompt",
        "com.github.spencerk.IO"
})
@SuiteDisplayName("Hangman Functional Tests")
@Suite
public class HangmanTestSuite {
}
