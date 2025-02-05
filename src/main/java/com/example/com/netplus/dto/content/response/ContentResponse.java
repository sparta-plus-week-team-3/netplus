package com.example.com.netplus.dto.content.response;

import com.example.com.netplus.common.Category;
import com.example.com.netplus.entity.Content;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentResponse {
    private Long contentId;
    private String title;
    private String description;
    private Category category;

    public ContentResponse(Content content, int viewCount) {
        this.contentId = content.getContentId();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.category = content.getCategory();
    }
}
