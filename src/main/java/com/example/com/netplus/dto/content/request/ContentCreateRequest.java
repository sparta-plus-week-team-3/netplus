package com.example.com.netplus.dto.content.request;

import com.example.com.netplus.common.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentCreateRequest {
    private String title;
    private String description;
    private Category category;
}
