package by.maiseichyk.finalproject.util.validator.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BankCardValidatorImplTest {
    private static final BankCardValidatorImpl cardValidator = BankCardValidatorImpl.getInstance();

    @Test
    public void testCheckNumber() {
        String cardNumber = "1234567812345678";
        assertTrue(cardValidator.checkNumber(cardNumber));
    }

    @Test
    public void testCheckOwnerName() {
        String ownerName = "MAKSIMMAISEICHYK";
        assertTrue(cardValidator.checkOwnerName(ownerName));
    }

    @Test
    public void testCheckExpirationDate() {
        String date = "2020-12-12";
        assertTrue(cardValidator.checkExpirationDate(date));
    }

    @Test
    public void testCheckCVVNumber() {
        String cvvNumber = "465";
        assertTrue(cardValidator.checkCVVNumber(cvvNumber));
    }
}