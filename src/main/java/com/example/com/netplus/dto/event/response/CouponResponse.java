package com.example.com.netplus.dto.event.response;

import com.example.com.netplus.common.CouponStatus;
import com.example.com.netplus.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponResponse {
    private final Long couponId;
    private final String code;
    private final Long userId;
    private final Long eventId;
    private final CouponStatus status;

    public static CouponResponse toDto(Coupon coupon) {
        return new CouponResponse(
                coupon.getCouponId(),
                coupon.getCode(),
                coupon.getUserId(),
                coupon.getEventId(),
                coupon.getStatus()
        );
    }
}
