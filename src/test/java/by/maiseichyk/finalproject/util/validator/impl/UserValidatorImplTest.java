package by.maiseichyk.finalproject.util.validator.impl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorImplTest {
    private static final UserValidatorImpl userValidator = UserValidatorImpl.getInstance();

    @Test
    public void testCheckLogin() {
        String login = "Gofmekler";
        assertTrue(userValidator.checkLogin(login));
    }

    @Test
    public void testCheckPassword() {
        String password = "123456Asd";
        assertTrue(userValidator.checkPassword(password));
    }

    @Test
    public void testCheckSurname() {
        String lastname = "Maiseichyk";
        assertTrue(userValidator.checkSurname(lastname));
    }

    @Test
    public void testCheckName() {
        String name = "Maksim";
        assertTrue(userValidator.checkName(name));
    }

    @Test
    public void testCheckDate() {
        String date = "2020-12-12";
        assertTrue(userValidator.checkDate(date));
    }

    @Test
    public void testCheckEmail() {
        String email = "gofmekler@gmail.com";
        assertTrue(userValidator.checkEmail(email));
    }

    @Test
    public void testCheckRole() {
        String role = "ADMIN";
        assertTrue(userValidator.checkRole(role));
    }

    @Test
    public void testCheckBalance() {
        String balance = "400";
        assertTrue(userValidator.checkBalance(balance));
    }
}