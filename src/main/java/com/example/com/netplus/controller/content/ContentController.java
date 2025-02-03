package com.example.com.netplus.controller.content;

import com.example.com.netplus.dto.content.request.CreateRequest;
import com.example.com.netplus.dto.content.response.Response;
import com.example.com.netplus.service.content.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    /**
     * 컨텐츠 생성 API
     * CreateRequest 데이터를 받아 새로운 컨텐츠를 생성하고 저장
     *
     * @param request 생성 요청 DTO
     * @return 생성된 컨텐츠 정보를 Response 형태로 반환
     */
    @PostMapping
    public ResponseEntity<Response> createContent(@RequestBody CreateRequest request) {
        return ResponseEntity.ok(contentService.createContent(request));
    }
}
