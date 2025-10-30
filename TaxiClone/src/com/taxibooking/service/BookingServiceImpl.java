package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Customer;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton implementation of BookingService.
 * Contains only core business logic (no printing or exceptions).
 */
public class BookingServiceImpl implements BookingService {

    private final Collection<Booking> bookings = new ArrayList<>();
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

        if (Objects.nonNull(booking)
                && Objects.nonNull(booking.getTaxi())
                && Objects.nonNull(booking.getCustomer())) {
            final Taxi taxi = booking.getTaxi();

            booking.setId(bookingIdGenerator.getAndIncrement());
            bookings.add(booking);

            taxi.setAvailable(false);
        }
    }

    /** Returns all bookings (read-only) */
    @Override
    public Collection<Booking> get() {
        return Collections.unmodifiableCollection(bookings);
    }

    /** Returns all bookings for a specific customer (read-only) */
    @Override
    public Collection<Booking> get(final int customerId) {
        final Collection<Booking> customerBookings = new ArrayList<>();

        for (Booking booking : bookings) {
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

        for (Booking booking : bookings) {

            if (booking.getId() == bookingId && booking.isActive()) {
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
    public void rate(final int taxiId, final double rating) {

        for (Booking booking : bookings) {
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
