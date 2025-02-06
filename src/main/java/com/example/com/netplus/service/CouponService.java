package com.example.com.netplus.service;

import com.example.com.netplus.dto.event.response.CouponResponse;
import com.example.com.netplus.entity.Coupon;
import com.example.com.netplus.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public List<CouponResponse> findCoupons(Long userId) {
        List<Coupon> coupons = couponRepository.findAllByUserUserId(userId);
        return coupons.stream().map(CouponResponse::toDto).toList();
    }
}
