package com.taxibooking.service;


import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;
import com.taxibooking.model.Driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Non-thread-safe implementation of BookingService.
 * Suitable for single-user or sequential use.
 */
class BookingServiceImpl implements BookingService {

    private final Collection<Booking> bookings = new ArrayList<>();
    private final AtomicInteger bookingIdCounter = new AtomicInteger(1);
    private static BookingServiceImpl instance;


    /** Private constructor — only accessible through getInstance() */
    private BookingServiceImpl() { }


    /** Singleton instance accessor */
    public static BookingServiceImpl getInstance() {

        if (Objects.isNull(instance)) {
            instance = new BookingServiceImpl();
        }
        return instance;
    }


    @Override
    public void book(final Booking booking) {

        if (Objects.isNull(booking) ||
                Objects.isNull(booking.getTaxi()) ||
                Objects.isNull(booking.getCustomer())) {
            System.out.println("Invalid booking details.");
            return;
        }

        final Taxi taxi = booking.getTaxi();

        if (!taxi.isAvailable()) {
            System.out.println("Taxi ID " + taxi.getId() + " is not available.");
            return;
        }

        booking.setId(bookingIdCounter.getAndIncrement());
        bookings.add(booking);
        taxi.setAvailable(false);

        System.out.println("Booking successful! Booking ID: " + booking.getId() + ", Fare: ₹" + booking.getFare());
    }


    @Override
    public Collection<Booking> get() {
        return new ArrayList<>(bookings);
    }


    @Override
    public Collection<Booking> get(final int customerId) {
        final Collection<Booking> customerBookings = new ArrayList<>();

        for (final Booking record : bookings) {
            final Customer customer = record.getCustomer();

            if (Objects.nonNull(customer) && customer.getId() == customerId) {
                customerBookings.add(record);
            }
        }

        return customerBookings;
    }


    @Override
    public void end(final int bookingId) {
        final Booking bookingRecord = bookings.stream()
                .filter(record -> record.getId() == bookingId && record.isActive())
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(bookingRecord)) {
            Taxi taxi = bookingRecord.getTaxi();
            bookingRecord.setActive(false);

            if (Objects.nonNull(taxi)) {
                taxi.setAvailable(true);
            }

            System.out.println("Booking ID " + bookingId + " has been ended successfully.");
        } else {
            System.out.println("Booking ID not found or already completed.");
        }
    }


    @Override
    public void rate(final int taxiId, final double rating) {
        final Booking bookingRecord = bookings.stream()
                .filter(record -> {
                    final Taxi taxi = record.getTaxi();
                    return Objects.nonNull(taxi) && taxi.getId() == taxiId;
                })
                .findFirst()
                .orElse(null);

        if (Objects.isNull(bookingRecord)) {
            System.out.println("Taxi not found for rating.");
            return;
        }

        final Taxi taxi = bookingRecord.getTaxi();
        final Driver driver = taxi.getDriver();

        if (Objects.nonNull(driver)) {
            driver.setRating(rating);

            System.out.println("Driver " + driver.getName() + " rated " + rating + " successfully.");
        } else {
            System.out.println("No driver assigned to this taxi.");
        }
    }

}
