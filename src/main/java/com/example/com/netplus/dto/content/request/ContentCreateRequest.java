package com.example.com.netplus.dto.content.request;

import com.example.com.netplus.common.Category;

public record ContentCreateRequest(
        String title,
        String description,
        Category category
) {
}
