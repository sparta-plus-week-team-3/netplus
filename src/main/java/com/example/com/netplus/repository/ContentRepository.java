package com.example.com.netplus.repository;

import com.example.com.netplus.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Content 엔티티의 데이터 접근을 담당하는 Repository 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 기능 제공
 */
public interface ContentRepository extends JpaRepository<Content, Long> {
}
