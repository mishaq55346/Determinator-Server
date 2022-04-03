package ru.mikhail.server.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordEncoderBean {
    public String encodePassword(String rawPassword) {
        return DigestUtils.md5Hex(rawPassword).toUpperCase();
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.toUpperCase().equals(encodePassword(rawPassword));
    }
}
