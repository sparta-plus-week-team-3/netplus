package com.example.com.netplus.dto.event.request;


import java.time.LocalDateTime;

public record UpdateEventRequest(
        String name,
        String description,
        Integer max,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDateTime couponExpirationDateTime
) {
}
