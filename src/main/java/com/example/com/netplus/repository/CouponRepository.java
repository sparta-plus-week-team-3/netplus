package com.example.com.netplus.repository;

import com.example.com.netplus.entity.Coupon;
import com.example.com.netplus.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query
    boolean existsByUserUserIdAndEvent(Long userId, Event event);

    @Query("""
                    SELECT c 
                    FROM Coupon c 
                    WHERE c.user.userId = :userId
            
            """)
    List<Coupon> findAllByUserUserId(@Param("userId") Long userId);

    void deleteCouponsByEvent(Event event);

    Optional<Coupon> findByCode(String code);
}