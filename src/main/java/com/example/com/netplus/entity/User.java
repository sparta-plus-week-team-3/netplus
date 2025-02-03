package com.example.com.netplus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "users")
public class User {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String name;

    public User() {
    }

    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User create(String email, String password, String name) {
        return new User(email, password, name);
    }

    // 유효성 검증
    //이메일 검증
    public static void generateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email is null or empty");
        }
        if (!validateEmail(email)) {
            throw new IllegalArgumentException("invalid email");
        }
    }

    //이메일 형식 검증
    private static boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    //비밀번호 형식 검증
    public static void validatePassword(String password) {
        if (!passwordPattern.matcher(password).matches()) {
            throw new IllegalArgumentException("invalid password");
        }
    }
}