package by.maiseichyk.finalproject.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger();

    private PasswordEncoder() {
    }

    public static String encode(String password) {
        return DigestUtils.md5Hex(password);
    }
}
