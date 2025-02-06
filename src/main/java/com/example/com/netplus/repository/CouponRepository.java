package com.example.com.netplus.repository;

import com.example.com.netplus.entity.Coupon;
import com.example.com.netplus.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByUserUserIdAndEvent(Long userId, Event event);

    List<Coupon> findAllByUserUserId(Long userId);

    void deleteCouponsByEvent(Event event);

    Optional<Coupon> findByCode(String code);
}