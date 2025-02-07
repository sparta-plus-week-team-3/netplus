package com.example.com.netplus.repository;

import com.example.com.netplus.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Content 엔티티의 데이터 접근을 담당하는 Repository 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 기능 제공
 */
public interface ContentRepository extends JpaRepository<Content, Long> {
    /**
     * 페이징 처리된 모든 컨텐츠를 조회
     *
     * @param pageable 페이징 정보
     * @return 페이징 처리된 Content 엔티티 목록
     */
    Page<Content> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
