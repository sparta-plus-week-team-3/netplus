package com.example.com.netplus.entity;

import com.example.com.netplus.config.BCrypUtil;
import com.example.com.netplus.exception.BusinessException;
import com.example.com.netplus.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Entity
@Getter
@Setter
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

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleted = false;

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
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "Email must not be empty");
        }
        if (!validateEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL, "Email is not valid");
        }
    }

    //이메일 형식 검증
    private static boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    //비밀번호 형식 검증
    public static void validatePassword(String password) {
        if (!passwordPattern.matcher(password).matches()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,
                    "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character.");
        }
    }

    //비밀번호 암호화
    public static String generateEncryptedPassword(String rawPassword) {
        return BCrypUtil.encrypt(rawPassword);
    }

    public static Boolean matchesPassword(String rawPassword, String encodedPassword) {
        if (!BCrypUtil.matches(rawPassword, encodedPassword)) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        return true;
    }
}