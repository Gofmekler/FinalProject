package by.maiseichyk.finalproject.util.validator.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BetValidatorImplTest {
    private static final BetValidatorImpl betValidator = BetValidatorImpl.getInstance();

    @Test
    public void testCheckWinCoefficient() {
        String coeff = "2.6";
        assertTrue(betValidator.checkWinCoefficient(coeff));
    }

    @Test
    public void testCheckBetStatus() {
        String betStatus = "WIN";
        assertTrue(betValidator.checkBetStatus(betStatus));
    }

    @Test
    public void testCheckBetAmount() {
        String amount = "20";
        assertTrue(betValidator.checkBetAmount(amount));
    }
}