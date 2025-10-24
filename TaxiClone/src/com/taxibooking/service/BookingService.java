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
    void book(Taxi taxi, Customer customer, String pickup, String drop, double fare);

    /** Retrieves all bookings. */
    Collection<Booking> get();

    /** Retrieves bookings for a specific customer by ID. */
    Collection<Booking> get(int customerId);

    /** Ends a booking by its ID. */
    void end(int bookingId);

    /** Rates a taxi after trip completion. */
    void rate(int taxiId, double rating);

    /** Factory method to get a BookingService instance. */
    static BookingService getInstance() {
        return new BookingServiceImpl();
    }
}
