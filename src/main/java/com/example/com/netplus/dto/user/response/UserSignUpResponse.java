package com.example.com.netplus.dto.user.response;

public record UserSignUpResponse() {

    public static UserSignUpResponse toDto() {
        return new UserSignUpResponse();
    }
}
