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
    private final Long eventId;
    private final CouponStatus status;

    public static CouponResponse toDto(Coupon coupon) {
        return new CouponResponse(
                coupon.getCouponId(),
                coupon.getCode(),
// TODO: Event 엔티티에서 필요한 정보 불러오기
                coupon.getEventId(),
                coupon.getStatus()
        );
    }
}
