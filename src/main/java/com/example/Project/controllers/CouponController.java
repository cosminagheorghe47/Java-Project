package com.example.Project.controllers;

import com.example.Project.model.entities.Coupon;
import com.example.Project.services.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/coupons")
@Tag(name = "Coupon", description = "Operations for managing coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "Get coupons for a user",
            description = "Fetches the list of coupons for a specific user based on their ID and an optional filter. Only CLIENT role can access this.")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Coupon>> getCouponsForUser(@PathVariable int userId, @RequestParam int filter) {
        List<Coupon> coupons = couponService.getCouponsForUser(userId, filter);
        return ResponseEntity.ok(coupons);
    }

    @Operation(summary = "Delete a coupon",
            description = "Deletes a coupon based on its ID. Only ADMIN role can access this.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCoupon(@PathVariable int id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
