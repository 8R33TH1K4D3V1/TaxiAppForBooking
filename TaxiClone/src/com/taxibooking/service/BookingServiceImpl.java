package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;

/**
 * Thread-safe implementation of BookingService.
 * Supports concurrent access for multiple users.
 */
class BookingServiceImpl implements BookingService {

    private final Collection<Booking> bookings = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger bookingIdCounter = new AtomicInteger(1);

    @Override
    public void book(final Taxi taxi, final Customer customer, final String pickup, final String drop, final double fare) {
        synchronized (taxi) {
            if (!taxi.isAvailable()) {
                System.out.println("Taxi ID " + taxi.getId() + " is not available.");
                return;
            }

            final int bookingId = bookingIdCounter.getAndIncrement();
            final Booking booking = new Booking(bookingId, taxi, customer, pickup, drop, fare);

            bookings.add(booking);
            taxi.setAvailable(false);

            System.out.println("Booking successful! Booking ID: " + booking.getId() + ", Fare: ₹" + fare);
        }
    }

    @Override
    public Collection<Booking> get() {
        synchronized (bookings) {
            return new ArrayList<>(bookings);
        }
    }

    @Override
    public Collection<Booking> get(final int customerId) {
        final Collection<Booking> result = new ArrayList<>();
        synchronized (bookings) {
            for (final Booking b : bookings) {
                if (b.getCustomer().getId() == customerId) {
                    result.add(b);
                }
            }
        }
        return result;
    }

    @Override
    public void end(final int bookingId) {
        synchronized (bookings) {
            final Booking booking = bookings.stream()
                    .filter(b -> b.getId() == bookingId && b.isActive())
                    .findFirst()
                    .orElse(null);

            if (booking != null) {
                synchronized (booking.getTaxi()) {
                    booking.setActive(false);
                    booking.getTaxi().setAvailable(true);
                    System.out.println("Booking ID " + bookingId + " has been ended successfully.");
                }
            } else {
                System.out.println("Booking ID not found or already completed.");
            }
        }
    }

    @Override
    public void rate(final int taxiId, final double rating) {
        synchronized (bookings) {
            final Booking booking = bookings.stream()
                    .filter(b -> b.getTaxi().getId() == taxiId)
                    .findFirst()
                    .orElse(null);

            if (booking != null) {
                synchronized (booking.getTaxi()) {
                    if (booking.getTaxi().getDriver() != null) {
                        booking.getTaxi().getDriver().setRating(rating);
                        System.out.println("Driver " + booking.getTaxi().getDriver().getName()
                                + " rated " + rating + " successfully.");
                    }
                }
            } else {
                System.out.println("Taxi not found for rating.");
            }
        }
    }
}
