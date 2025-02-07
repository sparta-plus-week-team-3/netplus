package com.example.com.netplus.dto.event.response;

import com.example.com.netplus.common.CouponStatus;
import com.example.com.netplus.entity.Coupon;

import java.time.LocalDateTime;

public record CouponResponse(
        Long couponId,
        String code,
        CouponStatus status,
        String name,
        String description,
        LocalDateTime expirationDateTime
) {

    public static CouponResponse toDto(Coupon coupon) {
        return new CouponResponse(
                coupon.getCouponId(),
                coupon.getCode(),
                coupon.getStatus(),
                coupon.getEvent().getName(),
                coupon.getEvent().getDescription(),
                coupon.getEvent().getCouponExpirationDateTime()
        );
    }
}
