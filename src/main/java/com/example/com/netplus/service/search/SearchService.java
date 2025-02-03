package com.example.com.netplus.service.search;

import com.example.com.netplus.entity.search.SearchQuery;
import com.example.com.netplus.repository.search.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final QueryRepository queryRepository;
//    private final RedisTemplate<String, List<String>> redisTemplate;
//    private final ContentRepository contentRepository;

    @Transactional
    public void search(String queryString) {
        saveQuery(queryString);

//        Pageable pageable = PageRequest.of(0, 10);
//        Page<ContentResponse> contentResponsePage = contentRepository.findContentLike(queryString, pageable);
//        return contentResponsePage;
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

    @Cacheable(value = "topTenQueries")
    @Transactional(readOnly = true)
    public List<String> findTopTenQueriesV2() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<String> topTenQueryList = queryRepository.findTopTenQueries(pageRequest);

        return topTenQueryList;
    }
}
