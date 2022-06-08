package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

public interface UserValidator {
    boolean checkLogin(String login);

    boolean checkPassword(String password);

    boolean checkSurname(String surname);

    boolean checkName(String name);

    boolean checkDate(String date);

    boolean checkEmail(String email);

    boolean checkNumber(String number);

    boolean checkUserData(Map<String, String> userData);

    boolean checkAge(String birthDate);
}
