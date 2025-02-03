package com.example.com.netplus.dto.content.response;

import com.example.com.netplus.common.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentResponse {
    private Long contentId;
    private String title;
    private String description;
    private Category category;
}
