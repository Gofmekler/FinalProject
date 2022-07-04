package by.maiseichyk.finalproject.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @author Maiseichyk
 * The type Password encoder.
 * @project Totalizator
 */
public class PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger();

    private PasswordEncoder() {
    }

    /**
     * @param password the password
     * @return the string
     */
    public static String encode(String password) {
        LOGGER.info("Password encoded.");
        return DigestUtils.md5Hex(password);
    }
}
