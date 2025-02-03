package com.example.com.netplus.controller;

import com.example.com.netplus.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @PostMapping
    public ResponseEntity<Void> search(@RequestParam("q") String queryString) {
        searchService.search(queryString);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<String>> findTopTenQueries() {
        return ResponseEntity.ok(searchService.findTopTenQueries());
    }
}
