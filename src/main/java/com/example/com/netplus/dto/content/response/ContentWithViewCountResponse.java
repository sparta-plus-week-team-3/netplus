package com.example.com.netplus.dto.content.response;

public record ContentWithViewCountResponse(
        ContentResponse content,
        int viewCount
) {
}
