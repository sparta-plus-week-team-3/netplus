package com.example.com.netplus.repository;

import com.example.com.netplus.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    List<Coupon> findAllByUserId(Long userId);

    void deleteCouponsByEventId(Long eventId);

    Optional<Coupon> findByCode(String code);
}