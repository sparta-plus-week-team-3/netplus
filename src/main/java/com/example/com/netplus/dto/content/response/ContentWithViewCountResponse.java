package com.example.com.netplus.dto.content.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentWithViewCountResponse {
    private final ContentResponse content;
    private final int viewCount;
}
