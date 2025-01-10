package com.example.Project.unitEndpoints;

import com.example.Project.controllers.CouponController;
import com.example.Project.model.entities.Coupon;
import com.example.Project.model.entities.Flight;
import com.example.Project.services.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CouponControllerTests {

    private CouponService couponService;
    private CouponController couponController;

    @BeforeEach
    void setUp() {
        couponService = mock(CouponService.class);
        couponController = new CouponController(couponService);
    }

    @Test
    void testGetCouponsForUser_Unused() {
        int userId = 1;
        Coupon coupon1 = new Coupon(1, null, 15, "2025-12-31", new Flight());
        Coupon coupon2 = new Coupon(2, null, 15, "2025-12-31", null);
        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponService.getCouponsForUser(userId, 1)).thenReturn(coupons);

        ResponseEntity<List<Coupon>> response = couponController.getCouponsForUser(userId, 1);

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(couponService, times(1)).getCouponsForUser(userId, 1);
    }

    @Test
    void testGetCouponsForUser_Used() {
        int userId = 1;
        Coupon coupon1 = new Coupon(1, null, 15, "2025-12-31", new Flight());
        Coupon coupon2 = new Coupon(2, null, 15, "2025-12-31", null);
        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponService.getCouponsForUser(userId, 0)).thenReturn(coupons);

        ResponseEntity<List<Coupon>> response = couponController.getCouponsForUser(userId, 0);

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(couponService, times(1)).getCouponsForUser(userId, 0);
    }

    @Test
    void testGetCouponsForUser_All() {
        int userId = 1;
        Coupon coupon1 = new Coupon(1, null, 15, "2025-12-31", new Flight());
        Coupon coupon2 = new Coupon(2, null, 15, "2025-12-31", null);
        List<Coupon> coupons = Arrays.asList(coupon1, coupon2);

        when(couponService.getCouponsForUser(userId, 2)).thenReturn(coupons);

        ResponseEntity<List<Coupon>> response = couponController.getCouponsForUser(userId, 2);

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(couponService, times(1)).getCouponsForUser(userId, 2);
    }

    @Test
    void testDeleteCoupon() {
        int couponId = 1;

        doNothing().when(couponService).deleteCoupon(couponId);

        ResponseEntity<Void> response = couponController.deleteCoupon(couponId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(couponService, times(1)).deleteCoupon(couponId);
    }
}
