package com.example.com.netplus.service.search;

import com.example.com.netplus.entity.search.SearchQuery;
import com.example.com.netplus.repository.search.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final QueryRepository queryRepository;
//    private final ContentRepository contentRepository;

    @Transactional
    public void search(String queryString) {
//        contentRepository.findContentLike()

        saveQuery(queryString);
    }

    private void saveQuery(String queryString) {
        SearchQuery query = new SearchQuery(null, queryString);
        queryRepository.save(query);
    }

    @Transactional(readOnly = true)
    public List<String> findTopTenQueries() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<String> topTenQueries = queryRepository.findTopTenQueries(pageRequest);
        return topTenQueries;
    }
}
