package com.example.com.netplus.dto;

import com.example.com.netplus.common.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRequestDto {
    private String title;
    private String description;
    private Category category;
}
