package com.example.com.netplus.entity;

import com.example.com.netplus.common.CouponStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private String code;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;

    public Coupon(String couponCode, Event event) {
        this.code = couponCode;
        this.event = event;
        this.status = CouponStatus.UNUSED;
    }

    public void changeUser(User user) {
        this.user = user;
    }
}
