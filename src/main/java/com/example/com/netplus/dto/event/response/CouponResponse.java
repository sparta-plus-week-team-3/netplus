package com.example.com.netplus.dto.event.response;

import com.example.com.netplus.common.CouponStatus;
import com.example.com.netplus.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CouponResponse {
    private final Long couponId;
    private final String code;
    private final CouponStatus status;
    private final String nName;
    private final String description;
    private final LocalDateTime expirationDateTime;

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
