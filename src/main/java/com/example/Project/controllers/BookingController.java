package com.example.Project.controllers;

import com.example.Project.model.entities.Booking;
import com.example.Project.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Booking", description = "Operations for managing bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Create a new booking",
            description = "Creates a new booking with the provided booking details. Only CLIENT role can access this.")
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @Operation(summary = "Get all bookings",
            description = "Fetches all bookings in the system. Only ADMIN role can access this.")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}
