package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;

import java.util.List;

/**
 * Service interface for managing bookings.
 * Defines operations like booking, retrieving, ending, and rating trips.
 */
public interface BookingService {

    /**
     * Books a taxi for a customer with specified details.
     */
    void book(final Taxi taxi, final Customer customer, final String pickup, final String drop, final double fare);

    /**
     * Retrieves all bookings.
     */
    List<Booking> get();

    /**
     * Retrieves bookings for a specific customer.
     */
    List<Booking> get(final int customerId);

    /**
     * Ends a booking by its ID.
     */
    void end(final int bookingId);

    /**
     * Rates a taxi/driver after trip completion.
     */
    void rate(final int taxiId, final double rating);
}
