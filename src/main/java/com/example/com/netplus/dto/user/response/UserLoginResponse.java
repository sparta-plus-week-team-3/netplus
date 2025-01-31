package com.example.com.netplus.dto.user.response;

public record UserLoginResponse(String accessToken) {

    public static UserLoginResponse toDto(String accessToken) {
        return new UserLoginResponse(accessToken);
    }
}
