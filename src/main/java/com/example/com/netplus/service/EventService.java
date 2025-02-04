package com.example.com.netplus.service;

import com.example.com.netplus.dto.event.request.CreateEventRequest;
import com.example.com.netplus.dto.event.request.UpdateEventRequest;
import com.example.com.netplus.dto.event.response.EventResponse;
import com.example.com.netplus.entity.Event;
import com.example.com.netplus.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        Event event = new Event(request);
        Event savedEvent = eventRepository.save(event);
        return EventResponse.toDto(savedEvent);
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
    public EventResponse updateEvent(Long contentId, UpdateEventRequest request) {
        // TODO: 쿠폰이 발급됐을 경우 수정 불가능

        existById(contentId);
        Event foundEvent = eventRepository.getReferenceById(contentId);
        foundEvent.update(request);
        return EventResponse.toDto(foundEvent);
    }

    @Transactional
    public void deleteEvent(Long contentId) {
        // TODO: 쿠폰이 발급됐을 경우 삭제 불가능

        existById(contentId);
        eventRepository.deleteById(contentId);
    }

    public void existById(Long contentId) {
        if (!eventRepository.existsById(contentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such content id: " + contentId);
        }
    }
}
