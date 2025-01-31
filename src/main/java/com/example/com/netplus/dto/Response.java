package com.example.com.netplus.dto;

import com.example.com.netplus.common.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {
    private Long contentId;
    private String title;
    private String description;
    private Category category;
}
