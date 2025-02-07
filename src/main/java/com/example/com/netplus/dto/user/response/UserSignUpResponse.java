package com.example.com.netplus.dto.user.response;

import com.example.com.netplus.entity.User;

public record UserSignUpResponse(
        String password,
        String name,
        String email
) {

    public static UserSignUpResponse toDto(User user) {

        return new UserSignUpResponse(user.getPassword(), user.getName(), user.getEmail());
    }
}
