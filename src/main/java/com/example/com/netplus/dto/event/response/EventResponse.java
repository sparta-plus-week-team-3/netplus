package com.example.com.netplus.dto.event.response;

import com.example.com.netplus.entity.Event;

import java.time.LocalDateTime;

public record EventResponse (
        Long eventId,
        String name,
        String description,
        Integer max,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDateTime couponExpirationDateTIme
){

    public static EventResponse toDto(Event event) {
        return new EventResponse(
                event.getEventId(),
                event.getName(),
                event.getDescription(),
                event.getMax(),
                event.getStartDateTime(),
                event.getEndDateTime(),
                event.getCouponExpirationDateTime()
        );
    }
}
