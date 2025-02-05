package com.example.com.netplus.service;

import com.example.com.netplus.dto.content.response.ContentResponse;
import com.example.com.netplus.entity.Content;
import com.example.com.netplus.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ContentRepository contentRepository;

    private static final String RANKING_KEY_PREFIX = "ranking:";

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    public void updateRanking() {
        // 자정에 랭킹을 업데이트하여 최신 조회수 기준으로 컨텐츠 랭킹을 저장
        String key = RANKING_KEY_PREFIX + LocalDate.now();
        redisTemplate.delete(key);

        Set<String> contentKeys = redisTemplate.keys("content:view:*");
        if (contentKeys == null || contentKeys.isEmpty()) return;

        // 각 컨텐츠의 조회수를 확인하고, 랭킹에 추가
        for (String contentKey : contentKeys) {
            Long contentId = Long.parseLong(contentKey.replace("content:view:", ""));
            Integer viewCount = Optional.ofNullable(redisTemplate.opsForValue().get(contentKey))
                    .map(Object::toString)
                    .map(Integer::valueOf)
                    .orElse(0);

            if (viewCount > 0) { // 조회수가 0 초과일 때만 랭킹에 추가
                redisTemplate.opsForZSet().add(key, contentId.toString(), viewCount);
            }
        }

        // 상위 10개 유지
        redisTemplate.opsForZSet().removeRange(key, 10, -1);
    }

    @Cacheable(value = "topContents", key = "#date")
    public List<ContentResponse> getTopContents(LocalDate date) {
        // 날짜별 인기 컨텐츠 조회, Redis에서 랭킹을 가져옴
        String key = RANKING_KEY_PREFIX + date;
        Set<String> topContentIds = redisTemplate.opsForZSet().reverseRange(key, 0, 9);
        if (topContentIds == null || topContentIds.isEmpty()) return List.of();

        List<Long> contentIds = topContentIds.stream().map(Long::parseLong).collect(Collectors.toList());
        List<Content> contents = contentRepository.findAllById(contentIds);

        // 조회수를 포함한 컨텐츠 목록 반환
        return contents.stream()
                .map(content -> new ContentResponse(content, getViewCount(content.getContentId())))
                .collect(Collectors.toList());
    }

    private int getViewCount(Long contentId) {
        // 조회수를 Redis에서 가져옴
        return Optional.ofNullable(redisTemplate.opsForValue().get("content:view:" + contentId))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElse(0);
    }
}