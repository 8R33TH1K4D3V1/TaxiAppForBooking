package com.taxibooking.controller;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;
import com.taxibooking.service.BookingService;
import com.taxibooking.service.BookingServiceImpl;

import java.util.Collection;


/**
 * Controller class that handles booking-related operations.
 * It delegates all business logic to the BookingService layer.
 */
public class BookingController {

    private final BookingService bookingService = new BookingServiceImpl();

    /**
     * Books a taxi for a customer with given trip details.
     */
    public void book(final Taxi taxi, final Customer customer, final String pickup, final String drop, final double fare) {
        bookingService.book(taxi, customer, pickup, drop, fare);
    }

    /**
     * Retrieves all bookings.
     */
    public Collection<Booking> get() {
        return bookingService.get();
    }

    /**
     * Retrieves bookings for a specific customer.
     */
    public Collection<Booking> get(final int customerId) {
        return bookingService.get(customerId);
    }

    /**
     * Ends a booking by its ID.
     */
    public void end(final int bookingId) {
        bookingService.end(bookingId);
    }

    /**
     * Updates the rating of a taxi after trip completion.
     */
    public void rate(final int taxiId, final double rating) {
        bookingService.rate(taxiId, rating);
    }
}
