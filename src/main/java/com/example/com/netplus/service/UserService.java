package com.example.com.netplus.service;

import com.example.com.netplus.config.JwtUtil;
import com.example.com.netplus.dto.user.request.UserLoginRequest;
import com.example.com.netplus.dto.user.request.UserSignUpRequest;
import com.example.com.netplus.dto.user.response.UserLoginResponse;
import com.example.com.netplus.dto.user.response.UserSignUpResponse;
import com.example.com.netplus.entity.User;
import com.example.com.netplus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //회원가입
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        verifyEmail(request.email());
        User.validatePassword(request.password());
        String encrytedPassword = User.generateEncryptedPassword(request.password());

        User newUser = User.create(request.email(), request.password(), encrytedPassword);
        User savedUser = userRepository.save(newUser);
        return UserSignUpResponse.toDto();
    }

    //로그인
    public UserLoginResponse login(UserLoginRequest request) {
        User foundUser = userRepository.findByEmail(request.email());
        //1.이메일이 중복되지 않는다.
        matchPassword(request.password(), foundUser.getPassword());
        //토큰
        String generatedToken = jwtUtil.generateToken(foundUser.getUserId());
        return UserLoginResponse.toDto(generatedToken);
    }

    //탈퇴
    public void deletedUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(true);
        userRepository.save(user);
    }

    //public methods
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("invalid password"));
    }


    //private methods
    private void verifyEmail(String Email) {
        User.generateEmail(Email);
        if (userRepository.existsByEmail(Email)) {
            throw new IllegalArgumentException("invalid password");
        }
    }

    private void matchPassword(String requestPassword, String storagePassword) {
        User.matchesPassword(requestPassword, storagePassword);
    }
}
