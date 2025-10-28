package com.taxibooking.service;

import com.taxibooking.model.Booking;
import java.util.Collection;

/**
 * Service interface for booking operations.
 */
public interface BookingService {

    /**
     * Books a taxi using a Booking object
     */
    void book(final Booking booking);

    /**
     * Retrieves all bookings.
     */
    Collection<Booking> get();

    /**
     * Retrieves bookings for a specific customer by ID.
     */
    Collection<Booking> get(final int customerId);

    /**
     * Ends a booking by its ID.
     */
    void end(final int bookingId);

    /**
     * Rates a taxi after trip completion.
     */
    void rate(final int taxiId, final double rating);

    /**
     * Factory method to get a BookingService instance.
     */
    static BookingService getInstance() {
        return BookingServiceImpl.getInstance();
    }

}