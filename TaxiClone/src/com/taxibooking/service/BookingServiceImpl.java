package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Enhanced Singleton implementation of BookingService.
 * Uses nested Map for efficient grouping by customer.
 */
public class BookingServiceImpl implements BookingService {

    private final Map<Integer, Map<Integer, Booking>> bookingMap = new HashMap<>();
    private final AtomicInteger bookingIdGenerator = new AtomicInteger(1);

    /** Private constructor for Singleton */
    private BookingServiceImpl() {}

    /** Inner static class for thread-safe Singleton initialization */
    private static final class Instance {
        private static final BookingServiceImpl SERVICE = new BookingServiceImpl();
    }

    /** Returns the Singleton instance */
    public static BookingServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    /** Books a taxi if available */
    @Override
    public void book(final Booking booking) {

        if (Objects.isNull(booking)
                || Objects.isNull(booking.getTaxi())
                || Objects.isNull(booking.getCustomer())) {
            return;
        }

        final Taxi taxi = booking.getTaxi();

        if (!taxi.isAvailable()) {
            return;
        }

        // Assign ID and mark as active
        booking.setId(bookingIdGenerator.getAndIncrement());
        booking.setActive(true);
        final int customerId = booking.getCustomer().getId();

        // Add booking grouped by customer
        bookingMap
                .computeIfAbsent(customerId, id -> new HashMap<>())
                .put(booking.getId(), booking);

        // Mark taxi unavailable
        taxi.setAvailable(false);
    }

    /** Returns all bookings (read-only) */
    @Override
    public Collection<Booking> get() {
        return bookingMap.values().stream()
                .flatMap(inner -> inner.values().stream())
                .toList();
    }

    /** Returns all bookings for a specific customer (read-only) */
    @Override
    public Collection<Booking> get(final int customerId) {

        if (customerId <= 0 || !bookingMap.containsKey(customerId)) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableCollection(bookingMap.get(customerId).values());
    }

    /** Ends an active booking and makes taxi available again */
    @Override
    public void end(final int bookingId) {

        for (final Map<Integer, Booking> customerBookings : bookingMap.values()) {
            final Booking booking = customerBookings.get(bookingId);

            if (Objects.nonNull(booking) && booking.isActive()) {
                booking.setActive(false);
                final Taxi taxi = booking.getTaxi();

                if (Objects.nonNull(taxi)) {
                    taxi.setAvailable(true);
                }

                break;
            }
        }
    }

    /** Rates a taxi’s driver if valid */
    @Override
    public void rate(final int  customerId, final int taxiId, final double rating) {

        if (customerId <= 0 || taxiId <= 0 || rating < 0.0 || rating > 5.0) {
            return;
        }

        final Map<Integer, Booking> customerBookings = bookingMap.get(customerId);

        if (Objects.isNull(customerBookings)) {
            return;
        }

        customerBookings.values().forEach(booking -> {
            final Taxi taxi = booking.getTaxi();

            if (Objects.nonNull(taxi) && taxi.getId() == taxiId) {
                final Driver driver = taxi.getDriver();

                if (Objects.nonNull(driver)) {
                    driver.setRating(rating);
                }
            }
        });
    }
}