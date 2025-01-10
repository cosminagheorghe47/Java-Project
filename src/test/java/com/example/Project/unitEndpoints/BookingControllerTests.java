package com.example.Project.unitEndpoints;

import com.example.Project.controllers.BookingController;
import com.example.Project.model.entities.Booking;
import com.example.Project.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingControllerTests {

    private BookingService bookingService;
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        bookingService = mock(BookingService.class);
        bookingController = new BookingController(bookingService);
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        booking.setId(1);

        when(bookingService.createBooking(booking)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.createBooking(booking);

        assertNotNull(response);
        assertEquals(booking, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(bookingService, times(1)).createBooking(booking);
    }

    @Test
    void testGetAllBookings() {
        Booking booking1 = new Booking();
        booking1.setId(1);

        Booking booking2 = new Booking();
        booking2.setId(2);

        when(bookingService.getAllBookings()).thenReturn(Arrays.asList(booking1, booking2));

        ResponseEntity<List<Booking>> response = bookingController.getAllBookings();

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(bookingService, times(1)).getAllBookings();
    }
}
