package by.maiseichyk.finalproject.util.validator.impl;

import by.maiseichyk.finalproject.util.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UserValidatorImpl implements UserValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String LOGIN_REGEX = "[\\p{Alpha}][\\p{Alpha}\\d]{4,29}";
    private static final String PASSWORD_REGEX = "[\\p{Alpha}]*[\\p{Alpha}\\d]{7,29}";
    private static final String SURNAME_REGEX = "[А-Я\\p{Upper}][а-я\\p{Lower}]{1,20}";
    private static final String NAME_REGEX = "[А-Я\\p{Upper}][а-яё\\p{Lower}]{1,15}";
    private static final String EMAIL_REGEX = "(([\\p{Alpha}\\d._*]+){5,25}@([\\p{Lower}]+){3,7}\\.([\\p{Lower}]+){2,3})";
    private static final String NUMBER_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";
    private static final String DATA_REGEX = "\\d{2}-\\d{2}-\\d{2}";
    private static final UserValidatorImpl instance = new UserValidatorImpl();

    private UserValidatorImpl() {
    }

    public static UserValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkLogin(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean checkPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean checkSurname(String surname) {
        return surname != null && surname.matches(SURNAME_REGEX);
    }

    @Override
    public boolean checkName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean checkDate(String date) {
        return date != null && date.matches(DATA_REGEX);
    }

    @Override
    public boolean checkEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean checkNumber(String number) {
        return number != null && number.matches(NUMBER_REGEX);
    }

    @Override
    public boolean checkUserData(Map<String, String> userData) {
        boolean isValid = true;
        if (!checkLogin(userData.get("login"))) {
            userData.put("login", userData.get("login") + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPassword(userData.get("password"))) {
            userData.put("password", userData.get("password") + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkSurname(userData.get("lastName"))) {
            userData.put("lastName", userData.get("lastName") + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkName(userData.get("firstName"))) {
            userData.put("firstName", userData.get("firstName") + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkEmail(userData.get("email"))) {
            userData.put("email", userData.get("email") + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkDate(userData.get("date"))) {
            userData.put("date", userData.get("date") + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean checkAge(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate todayDate = LocalDate.of(2022, 6, 30);
        return !todayDate.minusYears(18).isBefore(date) || todayDate.minusYears(18).equals(date);//todo
    }
}
