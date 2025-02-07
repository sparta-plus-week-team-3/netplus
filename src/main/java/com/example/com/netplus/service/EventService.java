package com.example.com.netplus.service;

import com.example.com.netplus.dto.event.request.CreateEventRequest;
import com.example.com.netplus.dto.event.request.UpdateEventRequest;
import com.example.com.netplus.dto.event.response.EventResponse;
import com.example.com.netplus.entity.Coupon;
import com.example.com.netplus.entity.Event;
import com.example.com.netplus.entity.User;
import com.example.com.netplus.repository.CouponRepository;
import com.example.com.netplus.repository.EventRepository;
import com.example.com.netplus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final CouponRepository couponRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final CouponService couponService;
    private final UserRepository userRepository;

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        // 시작시간 -1시간인지 확인
        validateUpdateDeleteDeadline(request.startDateTime());

        Event event = new Event(request);
        Event savedEvent = eventRepository.save(event);

        createCoupons(savedEvent, request.max(), request.couponExpirationDateTime());

        return EventResponse.toDto(savedEvent);
    }

    private static void validateUpdateDeleteDeadline(LocalDateTime startDateTime) {
        LocalDateTime updateDeleteDeadline = startDateTime.minusHours(1);
        if (updateDeleteDeadline.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이벤트 시작 시간이 1시간 이상 남아있어야 합니다.");
        }
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> findEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(EventResponse::toDto);
    }

    @Transactional(readOnly = true)
    public EventResponse findEventById(Long contentId) {
        Event event = eventRepository.findById(contentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such content id: " + contentId));
        return EventResponse.toDto(event);
    }

    @Transactional
    public EventResponse updateEvent(Long eventId, UpdateEventRequest request) {
        validateUpdateDeleteDeadline(request.startDateTime());

        existById(eventId);
        Event foundEvent = eventRepository.getReferenceById(eventId);
        foundEvent.update(request);

        couponRepository.deleteCouponsByEvent(foundEvent);
        createCoupons(foundEvent, request.max(), request.couponExpirationDateTime());

        return EventResponse.toDto(foundEvent);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        Event foundEvent = eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such event id: " + eventId));
        validateUpdateDeleteDeadline(foundEvent.getStartDateTime());
        couponRepository.deleteCouponsByEvent(foundEvent);
        eventRepository.deleteById(foundEvent.getEventId());
    }

    @Transactional
    public boolean participateEvent(Long eventId, Long userId) {

        // 이벤트 시작 시간과 끝 시간 검증
        Event foundEvent = eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such event id: " + eventId));
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(foundEvent.getStartDateTime()) || now.isAfter(foundEvent.getEndDateTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot participate in the event now.");
        }

        // 같은 이벤트에 참여했던 유저인지 확인
        if (couponRepository.existsByUserUserIdAndEvent(userId, foundEvent)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you already participated in the event.");
        }

        String couponCode = redisTemplate.opsForSet().pop("eventCoupons::" + eventId);
        if (couponCode == null) {
            return false;
        }
        Coupon coupon = couponRepository.findByCode(couponCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ""));
        User userRef = userRepository.getReferenceById(userId);
        coupon.changeUser(userRef);
        couponRepository.save(coupon);
        return true;
    }

    public void existById(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such content id: " + eventId);
        }
    }

    private void createCoupons(Event event, Integer max, LocalDateTime expirationDateTime) {
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        String key = "eventCoupons::" + event.getEventId();
        for (int i = 0; i < max; i++) {

            Coupon coupon = new Coupon(event.getEventId() + "-" + i, event);
            couponRepository.save(coupon);
            ops.add(key, coupon.getCode());
        }
        redisTemplate.expire(key, Duration.between(LocalDateTime.now(), expirationDateTime).plusMinutes(10));
    }
}
