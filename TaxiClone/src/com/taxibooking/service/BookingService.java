package com.taxibooking.service;

import com.taxibooking.model.Booking;
import java.util.Collection;

/**
 * Service interface for booking operations.
 */
public interface BookingService {

    /** Book a taxi. */
    void book(final Booking booking);

    /** Get all bookings. */
    Collection<Booking> get();

    /** Get bookings for a specific customer. */
    Collection<Booking> get(final int customerId);

    /** End a booking by its ID. */
    void end(final int bookingId);

    /** Rate a driver or taxi. */
    void rate(final int customerId, final int taxiId, final double rating);

    /** Singleton access to BookingService instance. */
    static BookingService getInstance() {
        return BookingServiceImpl.getInstance();
    }
}
