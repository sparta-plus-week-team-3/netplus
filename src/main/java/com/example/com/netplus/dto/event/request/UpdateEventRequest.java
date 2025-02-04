package com.example.com.netplus.dto.event.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateEventRequest {
    private final String name;
    private final String description;
    private final Integer max;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final LocalDateTime couponExpirationDateTime;
}
