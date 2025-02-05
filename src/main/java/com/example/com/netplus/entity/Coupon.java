package com.example.com.netplus.entity;

import com.example.com.netplus.common.CouponStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    // TODO: 무분별한 세터 사용?
    @Setter
    private Long userId;
    private Long eventId;
    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;

    public Coupon(String couponCode, Long userId, Long eventId) {
        this.code = couponCode;
        this.userId = userId;
        this.eventId = eventId;
        this.status = CouponStatus.UNUSED;
    }
}
