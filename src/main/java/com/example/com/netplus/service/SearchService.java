package com.example.com.netplus.service;

import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.entity.SearchQuery;
import com.example.com.netplus.repository.ContentRepository;
import com.example.com.netplus.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final QueryRepository queryRepository;
    //    private final RedisTemplate<String, List<String>> redisTemplate;
    private final ContentRepository contentRepository;
    private final ContentService contentService;

    @Transactional
    public Page<ContentResponse> search(String queryString) {
        saveQuery(queryString);

        Pageable pageable = PageRequest.of(0, 10);
        Page<ContentResponse> contentResponsePage = contentRepository.findContentLike(queryString, pageable).map(contentService::toResponse);
//        Page<ContentResponse> contentResponsePage = Page.empty();
        return contentResponsePage;
    }

    private void saveQuery(String queryString) {
        SearchQuery query = new SearchQuery(null, queryString);
        queryRepository.save(query);
    }

    @Transactional(readOnly = true)
    public List<String> findTopTenQueriesV1() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<String> topTenQueryList = queryRepository.findTopTenQueries(pageRequest);

        return topTenQueryList;
    }

    @Cacheable(value = "topTenQueriesV2", cacheManager = "caffeineCacheManager")
    @Transactional(readOnly = true)
    public List<String> findTopTenQueriesV2() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<String> topTenQueryList = queryRepository.findTopTenQueries(pageRequest);

        return topTenQueryList;
    }

    @Cacheable(value = "topTenQueriesV3", cacheManager = "redisCacheManager")
    @Transactional(readOnly = true)
    public List<String> findTopTenQueriesV3() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<String> topTenQueryList = queryRepository.findTopTenQueries(pageRequest);

        return topTenQueryList;
    }
}
