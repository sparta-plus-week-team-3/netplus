package com.example.com.netplus.controller;

import com.example.com.netplus.dto.user.request.UserLoginRequest;
import com.example.com.netplus.dto.user.request.UserProfileUpdateRequest;
import com.example.com.netplus.dto.user.request.UserSignUpRequest;
import com.example.com.netplus.dto.user.response.UserLoginResponse;
import com.example.com.netplus.dto.user.response.UserProfileResponse;
import com.example.com.netplus.dto.user.response.UserSignUpResponse;
import com.example.com.netplus.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(
            @Valid @RequestBody UserSignUpRequest signUpRequest) {

        UserSignUpResponse signUpResponse = userService.signUp(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest loginRequest) {
        UserLoginResponse loginResponse = userService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    //탈퇴
    @PatchMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId) {
        boolean isDeleted = userService.deletedUser(userId);
        String message = isDeleted ? "User deleted successfully" : "User deleted";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //프로필 조회
    @GetMapping("profile/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse profileResponse = userService.getUserProfile(userId);
        return ResponseEntity.ok(profileResponse);
    }

    //프로필 수정
    @PatchMapping("/profile/{userId}")
    public ResponseEntity<String> updateProfile(
            @RequestBody UserProfileUpdateRequest request,
            @AuthenticationPrincipal(expression = "userId") Long userId) {

        userService.updateUserProfile(userId, request);
        return ResponseEntity.ok("Profile updated successfully");
    }
}
