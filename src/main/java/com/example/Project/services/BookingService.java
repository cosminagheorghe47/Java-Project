package com.example.Project.services;

import com.example.Project.model.entities.Booking;
import com.example.Project.model.entities.Coupon;
import com.example.Project.repositories.BookingRepository;
import com.example.Project.repositories.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CouponRepository couponRepository;

    public Booking createBooking(Booking booking) {

        int baggageCost = booking.getNrOfBaggages() * 30;
        int finalPrice = booking.getBasePrice() + baggageCost;

        List<Coupon> availableCoupons = couponRepository.findUnusedCouponsByUserId(booking.getClient().getId());
        if (!availableCoupons.isEmpty()) {
            Coupon coupon = availableCoupons.get(0);
            int discount = (finalPrice * coupon.getDiscountPercentage()) / 100;
            finalPrice -= discount;
            coupon.setFlight(booking.getFlight());
            couponRepository.save(coupon);
        }

        booking.setFinalPrice(finalPrice);

        List<Coupon> userCoupons = couponRepository.findUnusedCouponsByUserId(booking.getClient().getId());
        if (userCoupons.size() < 3) {
            Coupon newCoupon = new Coupon();
            newCoupon.setUser(booking.getClient());
            newCoupon.setDiscountPercentage(10);
            newCoupon.setExpirationDate("2025-12-31");
            newCoupon.setFlight(null);
            couponRepository.save(newCoupon);
        }

        return bookingRepository.save(booking);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
