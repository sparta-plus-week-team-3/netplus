package com.example.com.netplus.controller;

import com.example.com.netplus.dto.content.request.ContentCreateRequest;
import com.example.com.netplus.dto.content.request.UpdateRequest;
import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.dto.content.response.ContentWithViewCountResponse;
import com.example.com.netplus.service.ContentService;
import com.example.com.netplus.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final RankingService rankingService;

    /**
     * 컨텐츠 생성 API
     * CreateRequest 데이터를 받아 새로운 컨텐츠를 생성하고 저장
     *
     * @param request 생성 요청 DTO
     * @return 생성된 컨텐츠 정보를 ContentResponse 형태로 반환
     */
    @PostMapping
    public ResponseEntity<ContentResponse> createContent(@RequestBody ContentCreateRequest request) {
        return ResponseEntity.ok(contentService.createContent(request));
    }

    /**
     * 모든 컨텐츠 조회 API
     * DB에 저장된 모든 컨텐츠 조회
     *
     * @return 컨텐츠 목록을 List로 반환
     */
    @GetMapping
    public ResponseEntity<List<ContentResponse>> getAllContents() {
        return ResponseEntity.ok(contentService.getAllContents());
    }

    /**
     * 특정 컨텐츠 조회 API
     * contentId를 기반으로 특정 컨텐츠 조회
     *
     * @param contentId 조회할 컨텐츠 ID
     * @return 해당 컨텐츠 정보를 ContentResponse로 반환
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable Long contentId) {
        return ResponseEntity.ok(contentService.getContentById(contentId));
    }

    /**
     * 컨텐츠 업데이트 API
     * contentId를 기반으로 컨텐츠 정보 수정
     *
     * @param contentId 수정할 컨텐츠 ID
     * @param request   업데이트할 컨텐츠의 새로운 데이터
     * @return 업데이트된 컨텐츠 정보를 ContentResponse로 반환
     */
    @PutMapping("/{contentId}")
    public ResponseEntity<ContentResponse> updateContent(@PathVariable Long contentId, @RequestBody UpdateRequest request) {
        return ResponseEntity.ok(contentService.updateContent(contentId, request));
    }

    /**
     * 컨텐츠 삭제 API
     * contentId를 기반으로 컨텐츠를 삭제
     *
     * @param contentId 삭제할 컨텐츠 ID
     * @return HTTP 204 No Content 반환
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long contentId) {
        contentService.deleteContent(contentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 페이징 처리된 모든 컨텐츠를 조회하는 API
     *
     * @param page 페이지 번호
     * @param size 페이지 당 컨텐츠 수
     * @return 페이징 처리된 ContentResponse DTO 목록
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<ContentResponse>> getPagedContents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(contentService.getPagedContents(page, size));
    }

    @GetMapping("/{contentId}/details")
    public ResponseEntity<ContentWithViewCountResponse> getContent(@PathVariable Long contentId, @AuthenticationPrincipal Long userId) {
        // ContentService에서 조회한 컨텐츠 정보와 조회수를 반환
        ContentWithViewCountResponse content = contentService.getContentWithViewCount(contentId, userId);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContentWithViewCountResponse>> searchContents(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 사용자 쿼리에 맞춰 페이징 처리된 검색 결과를 반환
        Page<ContentWithViewCountResponse> contents = contentService.searchContents(query, PageRequest.of(page, size));
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ContentResponse>> getPopularContents() {
        // 랭킹 서비스에서 인기 컨텐츠 목록을 반환
        List<ContentResponse> popularContents = rankingService.getTopContents(LocalDate.now());
        return ResponseEntity.ok(popularContents);
    }
}
