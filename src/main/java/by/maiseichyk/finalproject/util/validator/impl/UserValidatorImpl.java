package by.maiseichyk.finalproject.util.validator.impl;

import by.maiseichyk.finalproject.entity.UserRole;
import by.maiseichyk.finalproject.util.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.RequestParameter.*;

public class UserValidatorImpl implements UserValidator {
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String LOGIN_REGEX = "[\\p{Alpha}][\\p{Alpha}\\d]{4,29}";
    private static final String PASSWORD_REGEX = "[\\p{Alpha}]*[\\p{Alpha}\\d]{7,29}";
    private static final String SURNAME_REGEX = "[\\p{Upper}][\\p{Lower}]{1,20}";
    private static final String NAME_REGEX = "[\\p{Upper}][\\p{Lower}]{1,15}";
    private static final String EMAIL_REGEX = "(([\\p{Alpha}\\d._*]+){5,25}@([\\p{Lower}]+){3,7}\\.([\\p{Lower}]+){2,3})";
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String BALANCE_REGEX = "\\d+\\.*\\d+";
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
        return date != null && date.matches(DATE_REGEX);
    }

    @Override
    public boolean checkEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean checkRole(String role) {
        if (role != null && !role.isBlank()) {
            List<UserRole> roleList = Arrays.stream(UserRole.values()).toList();
            for (UserRole userRole : roleList) {
                if (userRole.toString().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkBalance(String balance) {
        return balance != null && balance.matches(BALANCE_REGEX);
    }

    @Override
    public boolean checkUserData(Map<String, String> userData) {
        boolean isValid = true;
        if (!checkLogin(userData.get(LOGIN))) {
            userData.put(LOGIN, userData.get(LOGIN) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPassword(userData.get(PASSWORD))) {
            userData.put(PASSWORD, userData.get(PASSWORD) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkSurname(userData.get(LASTNAME))) {
            userData.put(LASTNAME, userData.get(LASTNAME) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkName(userData.get(NAME))) {
            userData.put(NAME, userData.get(NAME) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkEmail(userData.get(EMAIL))) {
            userData.put(EMAIL, userData.get(EMAIL) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkDate(userData.get(BIRTH_DATE))) {
            userData.put(BIRTH_DATE, userData.get(BIRTH_DATE) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean checkLegalAge(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate todayDate = LocalDate.now();
        return todayDate.minusYears(18).isAfter(date) || todayDate.minusYears(18).equals(date);
    }
}
