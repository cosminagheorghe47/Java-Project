package com.example.Project.services;

import com.example.Project.model.entities.Coupon;
import com.example.Project.repositories.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<Coupon> getCouponsForUser(int userId, int filter) {
        if (filter == 1) {
            return couponRepository.findUnusedCouponsByUserId(userId);
        } else if (filter == 0) {
            return couponRepository.findUsedCouponsByUserId(userId);
        } else {
            return couponRepository.findCouponsByUserId(userId);
        }
    }

    public void deleteCoupon(int id) {
        couponRepository.deleteById(id);
    }
}
