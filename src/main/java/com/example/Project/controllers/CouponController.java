package com.example.Project.controllers;

import com.example.Project.model.entities.Coupon;
import com.example.Project.services.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupons")
@AllArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Coupon>> getCouponsForUser(@PathVariable int userId, @RequestParam int filter) {
        List<Coupon> coupons = couponService.getCouponsForUser(userId, filter);
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCoupon(@PathVariable int id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
