package com.example.com.netplus.service;

import com.example.com.netplus.dto.content.request.CreateRequest;
import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.entity.Content;
import com.example.com.netplus.exception.ContentNotFoundException;
import com.example.com.netplus.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    /**
     * 컨텐츠 생성
     * 엔티티의 정적 메서드를 사용해 새로운 컨텐츠 객체를 생성하고 DB에 저장
     *
     * @param request 클라이언트 요청 객체
     * @return 생성된 컨텐츠 정보를 ContentResponse 형태로 반환
     */
    public ContentResponse createContent(CreateRequest request) {
        Content content = Content.createContent(request.getTitle(), request.getDescription(), request.getCategory());
        Content savedContent = contentRepository.save(content); // 저장소에 저장
        return toResponse(savedContent); // DTO 변환 후 반환
    }

    /**
     * 모든 컨텐츠 조회
     * DB에서 모든 컨텐츠를 가져와서 ContentResponse DTO로 변환
     *
     * @return 컨텐츠 목록을 ContentResponse DTO로 반환
     */
    public List<ContentResponse> getAllContents() {
        return contentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 컨텐츠 조회
     * contentId를 이용해 컨텐츠를 조회하고 ContentResponse DTO로 변환
     * 존재하지 않으면 ContentNotFoundException 발생
     *
     * @param contentId 조회할 컨텐츠 ID
     * @return 조회된 컨텐츠 정보를 ContentResponse로 반환
     */
    public ContentResponse getContentById(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(contentId));
        return toResponse(content);
    }

    /**
     * Content 엔티티를 ContentResponse DTO로 변환하는 메서드
     *
     * @param content 변환할 Content 엔티티
     * @return 변환된 ContentResponse DTO
     */
    private ContentResponse toResponse(Content content) {
        return ContentResponse.builder()
                .contentId(content.getContentId())
                .title(content.getTitle())
                .description(content.getDescription())
                .category(content.getCategory())
                .build();
    }
}
