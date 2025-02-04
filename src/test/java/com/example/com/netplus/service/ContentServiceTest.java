package com.example.com.netplus.service;

import com.example.com.netplus.common.Category;
import com.example.com.netplus.dto.content.request.CreateRequest;
import com.example.com.netplus.dto.content.request.UpdateRequest;
import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.entity.Content;
import com.example.com.netplus.exception.ContentNotFoundException;
import com.example.com.netplus.repository.ContentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {
    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private ContentService contentService;

    @Test
    @DisplayName("컨텐츠 생성 - 성공 케이스")
    void createContent_success() {
        // given
        CreateRequest request = CreateRequest.builder()
                .title("테스트 제목")
                .description("테스트 설명")
                .category(Category.ACTION)
                .build();

        Content savedContent = new Content(1L, "테스트 제목", "테스트 설명", Category.ACTION);
        when(contentRepository.save(any(Content.class))).thenReturn(savedContent);

        // when
        ContentResponse result = contentService.createContent(request);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getContentId());
        assertEquals("테스트 제목", result.getTitle());
        assertEquals("테스트 설명", result.getDescription());
        assertEquals(Category.ACTION, result.getCategory());
    }

    @Test
    @DisplayName("모든 컨텐츠 조회 - 성공 케이스")
    void getAllContents_success() {
        // given
        List<Content> contentList = Arrays.asList(
                new Content(1L, "제목1", "설명1", Category.ACTION),
                new Content(2L, "제목2", "설명2", Category.SF)
        );
        when(contentRepository.findAll()).thenReturn(contentList);

        // when
        List<ContentResponse> result = contentService.getAllContents();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("제목1", result.get(0).getTitle());
        assertEquals("제목2", result.get(1).getTitle());
    }

    @Test
    @DisplayName("특정 컨텐츠 조회 - 성공 케이스")
    void getContentById_success() {
        // given
        Long contentId = 1L;
        Content content = new Content(contentId, "테스트 제목", "테스트 설명", Category.ACTION);
        when(contentRepository.findById(contentId)).thenReturn(Optional.of(content));

        // when
        ContentResponse result = contentService.getContentById(contentId);

        // then
        assertNotNull(result);
        assertEquals(contentId, result.getContentId());
        assertEquals("테스트 제목", result.getTitle());
    }

    @Test
    @DisplayName("특정 컨텐츠 조회 - 실패 케이스 (컨텐츠 없음)")
    void getContentById_notFound() {
        // given
        Long contentId = 1L;
        when(contentRepository.findById(contentId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ContentNotFoundException.class, () -> contentService.getContentById(contentId));
    }

    @Test
    @DisplayName("컨텐츠 업데이트 - 성공 케이스")
    void updateContent_success() {
        // given
        Long contentId = 1L;
        UpdateRequest request = UpdateRequest.builder()
                .title("수정된 제목")
                .description("수정된 설명")
                .category(Category.SF)
                .build();

        Content existingContent = new Content(contentId, "원래 제목", "원래 설명", Category.ACTION);
        when(contentRepository.findById(contentId)).thenReturn(Optional.of(existingContent));

        // when
        ContentResponse result = contentService.updateContent(contentId, request);

        // then
        assertNotNull(result);
        assertEquals(contentId, result.getContentId());
        assertEquals("수정된 제목", result.getTitle());
        assertEquals("수정된 설명", result.getDescription());
        assertEquals(Category.SF, result.getCategory());
    }

    @Test
    @DisplayName("컨텐츠 삭제 - 성공 케이스")
    void deleteContent_success() {
        // given
        Long contentId = 1L;
        when(contentRepository.existsById(contentId)).thenReturn(true);

        // when
        assertDoesNotThrow(() -> contentService.deleteContent(contentId));

        // then
        verify(contentRepository, times(1)).deleteById(contentId);
    }

    @Test
    @DisplayName("컨텐츠 삭제 - 실패 케이스 (컨텐츠 없음)")
    void deleteContent_notFound() {
        // given
        Long contentId = 1L;
        when(contentRepository.existsById(contentId)).thenReturn(false);

        // when & then
        assertThrows(ContentNotFoundException.class, () -> contentService.deleteContent(contentId));
    }

    @Test
    @DisplayName("페이징된 컨텐츠 조회 - 성공 케이스")
    void getPagedContents_success() {
        // given
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Content> contentList = Arrays.asList(
                new Content(1L, "제목1", "설명1", Category.ACTION),
                new Content(2L, "제목2", "설명2", Category.SF)
        );
        Page<Content> contentPage = new PageImpl<>(contentList, pageRequest, contentList.size());
        when(contentRepository.findAll(any(Pageable.class))).thenReturn(contentPage);

        // when
        Page<ContentResponse> result = contentService.getPagedContents(page, size);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("제목1", result.getContent().get(0).getTitle());
        assertEquals("제목2", result.getContent().get(1).getTitle());
        assertEquals(page, result.getNumber());
        assertEquals(size, result.getSize());
        assertEquals(contentList.size(), result.getTotalElements());
    }

    @Test
    @DisplayName("페이징된 컨텐츠 조회 - 빈 페이지")
    void getPagedContents_emptyPage() {
        // given
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Content> emptyPage = new PageImpl<>(List.of(), pageRequest, 0);
        when(contentRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        // when
        Page<ContentResponse> result = contentService.getPagedContents(page, size);

        // then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(page, result.getNumber());
        assertEquals(size, result.getSize());
        assertEquals(0, result.getTotalElements());
    }
}