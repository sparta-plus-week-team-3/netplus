package com.example.com.netplus.service;

import com.example.com.netplus.dto.content.request.CreateRequest;
import com.example.com.netplus.dto.content.request.UpdateRequest;
import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.entity.Content;
import com.example.com.netplus.exception.ContentNotFoundException;
import com.example.com.netplus.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public ContentResponse getContentById(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(contentId));
        return toResponse(content);
    }

    /**
     * 컨텐츠 업데이트
     * contentId를 기반으로 컨테츠 내용 수정
     * 존재하지 않는 컨텐츠 ID일 때 ContentNotFoundException 발생
     *
     * @param contentId 업데이트할 컨텐츠 ID
     * @param request   업데이트할 컨텐츠의 새로운 데이터
     * @return 업데이트된 컨텐츠 정보를 ContentResponse로 반환
     */
    @Transactional
    public ContentResponse updateContent(Long contentId, UpdateRequest request) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(contentId));
        content.updateContent(request.getTitle(), request.getDescription(), request.getCategory());
        return toResponse(content);
    }

    /**
     * 컨텐츠 삭제
     * contentId를 기반으로 컨텐츠를 삭제
     * 존재하지 않는 컨텐츠 ID일 때 ContentNotFoundException 발생
     *
     * @param contentId 삭제할 컨텐츠 ID
     */
    @Transactional
    public void deleteContent(Long contentId) {
        if (!contentRepository.existsById(contentId)) {
            throw new ContentNotFoundException(contentId);
        }
        contentRepository.deleteById(contentId);
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
