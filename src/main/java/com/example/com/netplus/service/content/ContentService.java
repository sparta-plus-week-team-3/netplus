package com.example.com.netplus.service.content;

import com.example.com.netplus.dto.content.request.CreateRequest;
import com.example.com.netplus.dto.content.response.Response;
import com.example.com.netplus.entity.content.Content;
import com.example.com.netplus.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    /**
     * 컨텐츠 생성
     * 엔티티의 정적 메서드를 사용해 새로운 컨텐츠 객체를 생성하고 DB에 저장
     *
     * @param request 클라이언트 요청 객체
     * @return 생성된 컨텐츠 정보를 Response 형태로 반환
     */
    public Response createContent(CreateRequest request) {
        Content content = Content.createContent(request.getTitle(), request.getDescription(), request.getCategory());
        Content savedContent = contentRepository.save(content); // 저장소에 저장
        return toResponse(savedContent); // DTO 변환 후 반환
    }

    /**
     * Content 엔티티를 Response DTO로 변환하는 메서드
     *
     * @param content 변환할 Content 엔티티
     * @return 변환된 Response DTO
     */
    private Response toResponse(Content content) {
        return Response.builder()
                .contentId(content.getContentId())
                .title(content.getTitle())
                .description(content.getDescription())
                .category(content.getCategory())
                .build();
    }
}
