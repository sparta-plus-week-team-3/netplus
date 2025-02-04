package com.example.com.netplus.controller;

import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Page<ContentResponse>> search(@RequestParam("q") String queryString) {
        Page<ContentResponse> contentResponsePage = searchService.search(queryString);
        return ResponseEntity.ok(contentResponsePage);
    }

    @GetMapping("/popular/v1")
    public ResponseEntity<List<String>> findTopTenQueriesV1() {
        return ResponseEntity.ok(searchService.findTopTenQueriesV1());
    }

    @GetMapping("/popular/v2")
    public ResponseEntity<List<String>> findTopTenQueriesV2() {
        return ResponseEntity.ok(searchService.findTopTenQueriesV2());
    }

    @GetMapping("/popular/v3")
    public ResponseEntity<List<String>> findTopTenQueriesV3() {
        return ResponseEntity.ok(searchService.findTopTenQueriesV3());
    }
}
