package com.example.Project.unitService;

import com.example.Project.model.entities.Coupon;
import com.example.Project.model.entities.Flight;
import com.example.Project.model.entities.User;
import com.example.Project.repositories.CouponRepository;
import com.example.Project.repositories.FlightRepository;
import com.example.Project.services.CouponService;
import com.example.Project.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CouponServiceTests {

    private  CouponRepository couponRepository;
    private  CouponService couponService ;

    @BeforeEach
    void setUp() {
        couponRepository = mock(CouponRepository.class);
        couponService = new CouponService(couponRepository);
    }

    @Test
    @DisplayName("Get unused coupons for a user")
    void testGetUnusedCouponsForUser() {
        int userId = 1;
        Coupon coupon1 = new Coupon(1, new User(), 15, "2025-12-31", new Flight());
        Coupon coupon2 = new Coupon(1, new User(), 15, "2025-12-31", null);
        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponRepository.findUnusedCouponsByUserId(userId)).thenReturn(coupons);

        List<Coupon> result = couponService.getCouponsForUser(userId, 1);

        assertEquals(2, result.size());
        verify(couponRepository, times(1)).findUnusedCouponsByUserId(userId);
    }

    @Test
    @DisplayName("Get used coupons for a user")
    void testGetUsedCouponsForUser() {
        int userId = 1;
        Coupon coupon1 = new Coupon(1, new User(), 15, "2025-12-31", new Flight());
        Coupon coupon2 = new Coupon(1, new User(), 15, "2025-12-31", null);
        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponRepository.findUsedCouponsByUserId(userId)).thenReturn(coupons);

        List<Coupon> result = couponService.getCouponsForUser(userId, 0);

        assertEquals(2, result.size());
        verify(couponRepository, times(1)).findUsedCouponsByUserId(userId);
    }

    @Test
    @DisplayName("Get all coupons for a user")
    void testGetAllCouponsForUser() {
        int userId = 1;
        Coupon coupon1 = new Coupon(1, new User(), 15, "2025-12-31", new Flight());
        Coupon coupon2 = new Coupon(1, new User(), 15, "2025-12-31", null);
        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponRepository.findCouponsByUserId(userId)).thenReturn(coupons);

        List<Coupon> result = couponService.getCouponsForUser(userId, 2);

        assertEquals(2, result.size());
        verify(couponRepository, times(1)).findCouponsByUserId(userId);
    }

    @Test
    @DisplayName("Delete a coupon")
    void testDeleteCoupon() {
        int couponId = 1;

        doNothing().when(couponRepository).deleteById(couponId);

        couponService.deleteCoupon(couponId);

        verify(couponRepository, times(1)).deleteById(couponId);
    }
}
