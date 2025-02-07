package com.example.com.netplus.dto.content.response;

import com.example.com.netplus.common.Category;
import com.example.com.netplus.entity.Content;

public record ContentResponse(
        Long contentId,
        String title,
        String description,
        Category category
) {
    // Content 엔티티를 받아서 ContentResponse 객체로 변환하는 정적 메서드
    public static ContentResponse fromEntity(Content content) {
        return new ContentResponse(
                content.getContentId(),
                content.getTitle(),
                content.getDescription(),
                content.getCategory()
        );
    }
}
