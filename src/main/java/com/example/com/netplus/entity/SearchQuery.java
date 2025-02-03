package com.example.com.netplus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "query")
public class SearchQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queryId;
    private String content;
//    private Long count;
}
