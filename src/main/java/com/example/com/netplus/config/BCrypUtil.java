package com.example.com.netplus.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class BCrypUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }
}
