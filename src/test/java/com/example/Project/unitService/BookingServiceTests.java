package com.example.Project.unitService;

import com.example.Project.model.entities.Booking;
import com.example.Project.model.entities.Coupon;
import com.example.Project.model.entities.User;
import com.example.Project.repositories.BookingRepository;
import com.example.Project.repositories.CouponRepository;
import com.example.Project.services.BookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTests {

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final CouponRepository couponRepository = mock(CouponRepository.class);
    private final BookingService bookingService = new BookingService(bookingRepository, couponRepository);

    @Test
    @DisplayName("Create booking with coupon applied")
    void testCreateBooking_WithCoupon() {
        User client = new User();
        client.setId(1);

        Coupon coupon = new Coupon();
        coupon.setDiscountPercentage(10);
        coupon.setUser(client);
        coupon.setFlight(null);

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setNrOfBaggages(2);
        booking.setBasePrice(200);

        when(couponRepository.findUnusedCouponsByUserId(client.getId())).thenReturn(Arrays.asList(coupon));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(234, result.getFinalPrice());
        verify(couponRepository, times(1)).save(coupon);
        verify(bookingRepository, times(1)).save(booking);
    }


    @Test
    @DisplayName("Create booking without coupon")
    void testCreateBooking_WithoutCoupon() {
        User client = new User();
        client.setId(1);

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setNrOfBaggages(3);
        booking.setBasePrice(200);

        when(couponRepository.findUnusedCouponsByUserId(client.getId())).thenReturn(Arrays.asList());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(couponRepository.save(any(Coupon.class))).thenReturn(new Coupon());

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(290, result.getFinalPrice());

        verify(couponRepository, times(1)).save(any(Coupon.class));  // Expecting save to be called for a new coupon
        verify(bookingRepository, times(1)).save(booking);
    }


    @Test
    @DisplayName("Create booking with less than three coupons")
    void testCreateBooking_LessThanThreeCoupons() {
        User client = new User();
        client.setId(1);

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setNrOfBaggages(2);
        booking.setBasePrice(200);

        Coupon existingCoupon = new Coupon();
        existingCoupon.setDiscountPercentage(10);
        List<Coupon> existingCoupons = Arrays.asList(existingCoupon);

        when(couponRepository.findUnusedCouponsByUserId(client.getId())).thenReturn(existingCoupons);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Coupon newCoupon = new Coupon();
        when(couponRepository.save(any(Coupon.class))).thenReturn(newCoupon);

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(234, result.getFinalPrice());

        verify(couponRepository, times(2)).save(any(Coupon.class));
        verify(bookingRepository, times(1)).save(booking);
    }



    @Test
    @DisplayName("Create booking without any unused coupons and no new coupons generated")
    void testCreateBooking_NoCouponsToGenerate() {
        User client = new User();
        client.setId(1);

        Coupon coupon = new Coupon();
        coupon.setDiscountPercentage(10);
        coupon.setUser(client);
        coupon.setFlight(null);

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setNrOfBaggages(3);
        booking.setBasePrice(200);

        when(couponRepository.findUnusedCouponsByUserId(client.getId())).thenReturn(Arrays.asList(coupon));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(261, result.getFinalPrice());
        verify(couponRepository, times(1)).save(coupon);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    @DisplayName("Get all bookings")
    void testGetAllBookings() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }
}
