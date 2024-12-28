package com.example.Project.repositories;

import com.example.Project.model.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Query("SELECT c FROM Coupon c WHERE c.user.id = :userId AND c.flight IS NULL")
    List<Coupon> findUnusedCouponsByUserId(int userId);

    @Query("SELECT c FROM Coupon c WHERE c.user.id = :userId AND c.flight IS NOT NULL")
    List<Coupon> findUsedCouponsByUserId(int userId);

    @Query("SELECT c FROM Coupon c WHERE c.user.id = :userId")
    List<Coupon> findCouponsByUserId(int userId);

    @Query("DELETE FROM Coupon c WHERE c.id = :id")
    void deleteCouponById(@Param("id") int id);
}
