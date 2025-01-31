package com.example.com.netplus.repository.search;

import com.example.com.netplus.entity.search.SearchQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QueryRepository extends JpaRepository<SearchQuery, Long> {
    @Query("""
            SELECT q.content
            FROM SearchQuery q
            GROUP BY q.content
            ORDER BY COUNT(q) DESC
            """)
    List<String> findTopTenQueries(Pageable pageable);
}
