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
    public void book(Taxi taxi, Customer customer, String pickup, String drop, double fare) {
        bookingService.book(taxi, customer, pickup, drop, fare);
    }

    /** Get all bookings */
    public Collection<Booking> get() {
        return bookingService.get();
    }

    /** Get bookings for a specific customer */
    public Collection<Booking> get(int customerId) {
        return bookingService.get(customerId);
    }

    /** End an active booking */
    public void end(int bookingId) {
        bookingService.end(bookingId);
    }

    /** Rate a driver/taxi */
    public void rate(int taxiId, double rating) {
        bookingService.rate(taxiId, rating);
    }
}
