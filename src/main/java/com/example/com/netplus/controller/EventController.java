package com.example.com.netplus.controller;

import com.example.com.netplus.dto.event.request.CreateEventRequest;
import com.example.com.netplus.dto.event.request.UpdateEventRequest;
import com.example.com.netplus.dto.event.response.EventResponse;
import com.example.com.netplus.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody CreateEventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponse>> findEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(eventService.findEvents(PageRequest.of(page, size)));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> findEventById(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.findEventById(eventId));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId, @RequestBody UpdateEventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(eventId, request));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}/participate")
    public ResponseEntity<String> participateEvent(
            @PathVariable Long eventId,
            @AuthenticationPrincipal Long userId
    ) {
        boolean isSuccess = eventService.participateEvent(eventId, userId);
        if (isSuccess) {
            return new ResponseEntity<>("쿠폰이 지급되었습니다. 쿠폰함을 확인해주세요.", HttpStatus.OK);
        }
        return new ResponseEntity<>("모든 쿠폰이 소진되었습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
