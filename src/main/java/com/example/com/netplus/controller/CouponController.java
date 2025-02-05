package com.example.com.netplus.controller;

import com.example.com.netplus.dto.event.response.CouponResponse;
import com.example.com.netplus.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<List<CouponResponse>> findCoupons(@AuthenticationPrincipal(expression = "userId") Long userId) {
        return ResponseEntity.ok(couponService.findCoupons(userId));
    }
}
