package com.example.com.netplus.service;

import com.example.com.netplus.entity.SearchQuery;
import com.example.com.netplus.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
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
    public List<String> findTopTenQueries() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<String> topTenQueryList = queryRepository.findTopTenQueries(pageRequest);

        return topTenQueryList;
    }
}
