package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

/**
 * @author Maiseichyk
 * The interface User validator.
 * @project Totalizator
 */
public interface UserValidator {
    /**
     * @param login the login
     * @return boolean
     */
    boolean checkLogin(String login);

    /**
     * @param password the password
     * @return boolean
     */
    boolean checkPassword(String password);

    /**
     * @param surname the lastname
     * @return boolean
     */
    boolean checkSurname(String surname);

    /**
     * @param name the name
     * @return boolean
     */
    boolean checkName(String name);

    /**
     * @param date the date
     * @return boolean
     */
    boolean checkDate(String date);

    /**
     * @param email the email
     * @return boolean
     */
    boolean checkEmail(String email);

    /**
     * @param role the role
     * @return boolean
     */
    boolean checkRole(String role);

    /**
     * @param balance the balance
     * @return boolean
     */
    boolean checkBalance(String balance);

    /**
     * @param userData the user data
     * @return boolean
     */
    boolean checkUserData(Map<String, String> userData);

    /**
     * @param birthDate the birthdate
     * @return boolean
     */
    boolean checkLegalAge(String birthDate);
}
