package com.example.com.netplus.dto.event.request;

import java.time.LocalDateTime;

public record CreateEventRequest(
        String name,
        String description,
        Integer max,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDateTime couponExpirationDateTime
) {
}
