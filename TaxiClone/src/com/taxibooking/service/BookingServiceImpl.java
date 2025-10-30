package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Customer;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton implementation of BookingService.
 * Contains only core business logic.
 */
public class BookingServiceImpl implements BookingService {

    private final Map<Integer, Booking> bookingMap = new HashMap<>();
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

        if (Objects.isNull(booking)) {
            return;
        }

        final Taxi taxi = booking.getTaxi();
        final Customer customer = booking.getCustomer();

        if (Objects.isNull(taxi) || Objects.isNull(customer)) {
            return;
        }

        if (!taxi.isAvailable()) {
            return;
        }

        // Assign ID and mark booking as active
        booking.setId(bookingIdGenerator.getAndIncrement());
        booking.setActive(true);

        // Save booking and update taxi status
        bookingMap.put(booking.getId(), booking);
        taxi.setAvailable(false);
    }

    /** Returns all bookings (read-only) */
    @Override
    public Collection<Booking> get() {
        return Collections.unmodifiableCollection(bookingMap.values());
    }

    /** Returns all bookings for a specific customer (read-only) */
    @Override
    public Collection<Booking> get(final int customerId) {

        if (customerId <= 0) {
            return Collections.emptyList();
        }

        final Collection<Booking> customerBookings = new ArrayList<>();

        for (final Booking booking : bookingMap.values()) {
            final Customer customer = booking.getCustomer();

            if (Objects.nonNull(customer) && customer.getId() == customerId) {
                customerBookings.add(booking);
            }
        }

        return Collections.unmodifiableCollection(customerBookings);
    }

    /** Ends an active booking and makes taxi available again */
    @Override
    public void end(final int bookingId) {
        final Booking booking = bookingMap.get(bookingId);

        if (Objects.nonNull(booking) && booking.isActive()) {
            booking.setActive(false);
            final Taxi taxi = booking.getTaxi();

            if (Objects.nonNull(taxi)) {
                taxi.setAvailable(true);
            }
        }
    }

    /** Rates a taxi’s driver if valid */
    @Override
    public void rate(final int taxiId, final double rating) {

        for (final Booking booking : bookingMap.values()) {
            final Taxi taxi = booking.getTaxi();

            if (Objects.nonNull(taxi) && taxi.getId() == taxiId) {
                final Driver driver = taxi.getDriver();

                if (Objects.nonNull(driver)) {
                    driver.setRating(rating);
                }

                break;
            }
        }
    }
}
