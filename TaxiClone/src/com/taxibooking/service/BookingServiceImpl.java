package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BookingService.
 * Handles booking creation, retrieval, ending, and rating drivers.
 */
public class BookingServiceImpl implements BookingService {

    private final List<Booking> bookings = new ArrayList<>();
    private int bookingIdCounter = 1;

    @Override
    public void book(final Taxi taxi, final Customer customer, final String pickup, final String drop, final double fare) {
        if (!taxi.isAvailable()) {
            System.out.println("Taxi ID " + taxi.getId() + " is not available.");
            return;
        }

        final Booking booking = new Booking(bookingIdCounter++, taxi, customer, pickup, drop, fare);
        bookings.add(booking);
        taxi.setAvailable(false);
        System.out.println("Booking successful! Booking ID: " + booking.getId() + ", Fare: ₹" + fare);
    }

    @Override
    public List<Booking> get() {
        return bookings;
    }

    @Override
    public List<Booking> get(final int customerId) {
        final List<Booking> result = new ArrayList<>();
        for (final Booking b : bookings) {
            if (b.getCustomer().getId() == customerId) {
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public void end(final int bookingId) {
        final Booking booking = bookings.stream()
                .filter(b -> b.getId() == bookingId && b.isActive())
                .findFirst()
                .orElse(null);

        if (booking != null) {
            booking.setActive(false);
            booking.getTaxi().setAvailable(true);
            System.out.println("Booking ID " + bookingId + " has been ended successfully.");
        } else {
            System.out.println("Booking ID not found or already completed.");
        }
    }

    @Override
    public void rate(final int taxiId, final double rating) {
        final Booking booking = bookings.stream()
                .filter(b -> b.getTaxi().getId() == taxiId)
                .findFirst()
                .orElse(null);

        if (booking != null) {
            booking.getTaxi().getDriver().setRating(rating);
            System.out.println("Driver " + booking.getTaxi().getDriver().getName() + " rated " + rating + " successfully.");
        } else {
            System.out.println("Taxi not found for rating.");
        }
    }
}
