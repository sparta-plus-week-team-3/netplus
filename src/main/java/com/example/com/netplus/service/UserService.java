package com.example.com.netplus.service;

import com.example.com.netplus.config.JwtUtil;
import com.example.com.netplus.dto.user.request.UserLoginRequest;
import com.example.com.netplus.dto.user.request.UserProfileUpdateRequest;
import com.example.com.netplus.dto.user.request.UserSignUpRequest;
import com.example.com.netplus.dto.user.response.UserLoginResponse;
import com.example.com.netplus.dto.user.response.UserProfileResponse;
import com.example.com.netplus.dto.user.response.UserSignUpResponse;
import com.example.com.netplus.entity.User;
import com.example.com.netplus.exception.BusinessException;
import com.example.com.netplus.exception.ErrorCode;
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

        // 직접 BCryptPasswordEncoder를 사용하는 대신, generateEncryptedPassword 메서드를 사용
        String encryptedPassword = User.generateEncryptedPassword(request.password());

        User newUser = User.create(request.name(), request.email(), encryptedPassword);  // 암호화된 비밀번호만 사용
        User savedUser = userRepository.save(newUser);
        return UserSignUpResponse.toDto(savedUser);
    }

    //로그인
    public UserLoginResponse login(UserLoginRequest request) {
        User foundUser = userRepository.findByEmail(request.email());
        //1.이메일이 존재하는지 확인
        if (foundUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "user not found");
        }
        matchPassword(request.password(), foundUser.getPassword());
        //토큰
        String generatedToken = jwtUtil.generateToken(foundUser.getUserId());
        return UserLoginResponse.toDto(generatedToken);
    }

    //탈퇴
    public boolean deletedUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(true);
        userRepository.save(user);
        return true;
    }

    //조회
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .filter(u -> !u.isDeleted())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserProfileResponse(user.getUserId(), user.getName(), user.getEmail());
    }

    //수정
    public UserProfileResponse updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }
        userRepository.save(user);
        return new UserProfileResponse(user.getUserId(), user.getName(), user.getEmail());
    }

    //public methods
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }


    //private methods
    private void verifyEmail(String Email) {
        User.generateEmail(Email);
        if (userRepository.existsByEmail(Email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    private void matchPassword(String requestPassword, String storagePassword) {
        if (!User.matchesPassword(requestPassword, storagePassword)) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED, "Invalid password");
        }
    }
}
