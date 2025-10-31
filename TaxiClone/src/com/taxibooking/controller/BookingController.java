package com.taxibooking.controller;

import com.taxibooking.model.Booking;
import com.taxibooking.service.BookingService;
import java.util.Collection;

/**
 * Controller for handling taxi bookings (Singleton).
 * Uses BookingService interface — implementation hidden.
 */
public class BookingController {

    private final BookingService bookingService;

    /** Private constructor — hides implementation. */
    private BookingController() {
        this.bookingService = BookingService.getInstance();
    }

    /** Internal static class for singleton instance. */
    private static final class Instance {
        private static final BookingController CONTROLLER = new BookingController();
    }

    /** Returns singleton instance. */
    public static BookingController getInstance() {
        return Instance.CONTROLLER;
    }

    /** Book a taxi. */
    public void book(final Booking booking) {
        bookingService.book(booking);
    }

    /** Get all bookings. */
    public Collection<Booking> get() {
        return bookingService.get();
    }

    /** Get bookings for a specific customer. */
    public Collection<Booking> get(final int customerId) {
        return bookingService.get(customerId);
    }

    /** End an active booking. */
    public void end(final int bookingId) {
        bookingService.end(bookingId);
    }

    /** Rate a driver or taxi. */
    public void rate(final int customerId, final int taxiId, final double rating) {
        bookingService.rate(customerId, taxiId, rating);
    }
}
