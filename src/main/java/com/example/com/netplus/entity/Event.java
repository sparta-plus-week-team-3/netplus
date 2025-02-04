package com.example.com.netplus.entity;

import com.example.com.netplus.dto.event.request.CreateEventRequest;
import com.example.com.netplus.dto.event.request.UpdateEventRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String name;
    private String description;
    private Integer max;
    @Column(name = "start_datetime")
    private LocalDateTime startDateTime;
    @Column(name = "end_datetime")
    private LocalDateTime endDateTime;
    @Column(name = "coupon_issued_datetime")
    private LocalDateTime couponIssuedDateTime;
    @Column(name = "coupon_expiration_datetime")
    private LocalDateTime couponExpirationDateTime;

    public Event(CreateEventRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.max = request.getMax();
        this.startDateTime = request.getStartDateTime();
        this.endDateTime = request.getEndDateTime();
        this.couponExpirationDateTime = request.getCouponExpirationDateTime();
    }

    public void update(UpdateEventRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.max = request.getMax();
        this.startDateTime = request.getStartDateTime();
        this.endDateTime = request.getEndDateTime();
        this.couponExpirationDateTime = request.getCouponExpirationDateTime();
    }
}
