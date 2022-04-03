package ru.mikhail.server.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderBeanTest {
    PasswordEncoderBean passwordEncoderBean;
    String rawPassword = "test";
    String encodedPasswordMD5 = "098F6BCD4621D373CADE4E832627B4F6";

    @BeforeEach
    void setUp() {
        passwordEncoderBean = new PasswordEncoderBean();
    }

    @Test
    void encodePasswordTest() {
        String testString = passwordEncoderBean.encodePassword(rawPassword);
        assertEquals(encodedPasswordMD5, testString);
    }

    @Test
    void matchesTest() {
        assertTrue(passwordEncoderBean.matches(rawPassword, encodedPasswordMD5));
        assertFalse(passwordEncoderBean.matches(rawPassword + "salt", encodedPasswordMD5));
    }
}