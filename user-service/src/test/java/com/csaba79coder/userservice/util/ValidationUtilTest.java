package com.csaba79coder.userservice.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**

 Unit test for {@link ValidationUtil}.
 */
class ValidationUtilTest {

    /**

     Test case for valid email address.
     */
    @Test
    @DisplayName("Test valid email")
    public void testValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("john.doe@example.com"));
    }

    /**

     Test case for invalid email address.
     */
    @Test
    @DisplayName("Test invalid email")
    public void testInvalidEmail() {
        assertFalse(ValidationUtil.isValidEmail("johndoe@example"));
    }

    /**

     Test case for valid password.
     */
    @Test
    @DisplayName("Test valid password")
    public void testValidPassword() {
        assertTrue(ValidationUtil.isValidPassword("P@ssword1"));
    }

    /**

     Test case for invalid password.
     */
    @Test
    @DisplayName("Test invalid password")
    public void testInvalidPassword() {
        assertFalse(ValidationUtil.isValidPassword("password"));
    }
}