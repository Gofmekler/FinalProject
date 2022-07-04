package by.maiseichyk.finalproject.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordEncoderTest {
    private static final String passToEncode = "111";
    private static final String expectedPass = "698d51a19d8a121ce581499d7b701668";

    @Test
    public void testEncode() {
        String actualPass = PasswordEncoder.encode(passToEncode);
        assertEquals(actualPass, expectedPass);
    }
}