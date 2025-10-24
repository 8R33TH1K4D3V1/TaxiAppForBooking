package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;
import java.util.Collection;

/**
 * Service interface for booking operations.
 * Defines methods for booking taxis, retrieving bookings,
 * ending trips, and updating driver ratings.
 */
public interface BookingService {

    /** Books a taxi for a customer with trip details. */
    void book(final Taxi taxi, final Customer customer, final String pickup, final String drop, final double fare);

    /** Retrieves all bookings. */
    Collection<Booking> get();

    /** Retrieves bookings for a specific customer by ID. */
    Collection<Booking> get(final int customerId);

    /** Ends a booking by its ID. */
    void end(final int bookingId);

    /** Rates a taxi after trip completion. */
    void rate(final int taxiId, final double rating);

    /** Factory method to get a BookingService instance. */
    static BookingService getInstance() {
        return new BookingServiceImpl();
    }
}
