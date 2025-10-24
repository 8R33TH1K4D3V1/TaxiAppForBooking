package com.taxibooking.controller;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;
import com.taxibooking.service.BookingService;
import java.util.Collection;

/**
 * Controller for taxi bookings.
 * Delegates all operations to BookingService.
 */
public class BookingController {

    private final BookingService bookingService = BookingService.getInstance();

    /** Book a taxi for a customer */
    public void book(final Taxi taxi, final Customer customer, final String pickup, final String drop, final double fare) {
        bookingService.book(taxi, customer, pickup, drop, fare);
    }

    /** Get all bookings */
    public Collection<Booking> get() {
        return bookingService.get();
    }

    /** Get bookings for a specific customer */
    public Collection<Booking> get(final int customerId) {
        return bookingService.get(customerId);
    }

    /** End an active booking */
    public void end(final int bookingId) {
        bookingService.end(bookingId);
    }

    /** Rate a driver/taxi */
    public void rate(final int taxiId, final double rating) {
        bookingService.rate(taxiId, rating);
    }
}
