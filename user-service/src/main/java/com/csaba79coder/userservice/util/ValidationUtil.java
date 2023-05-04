package com.csaba79coder.userservice.util;

import java.util.regex.Pattern;

/**
 * A utility class that provides methods for validating email addresses and passwords.
 */
public class ValidationUtil {

    // A regular expression that matches valid email addresses
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    /**
     * Validates an email address. Returns true if the email is valid,
     * and false otherwise.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates a password. Returns true if the password is valid,
     * and false otherwise. A valid password must be at least 8 characters
     * long and contain at least one uppercase letter, one lowercase letter,
     * one digit, and one special character.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(pattern);
    }

    private ValidationUtil() {
    }
}
