package com.example.com.netplus.dto.event.response;

import com.example.com.netplus.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EventResponse {
    private final Long eventId;
    private final String name;
    private final String description;
    private final Integer max;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final LocalDateTime couponIssuedDateTime;
    private final LocalDateTime couponExpirationDateTime;

    public static EventResponse toDto(Event event) {
        return new EventResponse(
                event.getEventId(),
                event.getName(),
                event.getDescription(),
                event.getMax(),
                event.getStartDateTime(),
                event.getEndDateTime(),
                event.getCouponIssuedDateTime(),
                event.getCouponExpirationDateTime()
        );
    }
}
