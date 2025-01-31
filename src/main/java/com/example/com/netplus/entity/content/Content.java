package com.example.com.netplus.entity.content;

import com.example.com.netplus.common.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;
    private String title;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Category category;

    public static Content createContent(String title, String description, Category category) {
        return new Content(null, title, description, category);
    }

    public void updateContent(String title, String description, Category category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }
}
