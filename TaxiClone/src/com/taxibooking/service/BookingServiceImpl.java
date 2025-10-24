package com.taxibooking.service;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of BookingService.
 * Handles booking creation, retrieval, ending, and rating drivers.
 */
class BookingServiceImpl implements BookingService { // package-private (hidden)

    private final Collection<Booking> bookings = new ArrayList<>();
    private int bookingIdCounter = 1;

    @Override
    public void book(Taxi taxi, Customer customer, String pickup, String drop, double fare) {
        if (!taxi.isAvailable()) {
            System.out.println("Taxi ID " + taxi.getId() + " is not available.");
            return;
        }

        Booking booking = new Booking(bookingIdCounter++, taxi, customer, pickup, drop, fare);
        bookings.add(booking);
        taxi.setAvailable(false);
        System.out.println("Booking successful! Booking ID: " + booking.getId() + ", Fare: ₹" + fare);
    }

    @Override
    public Collection<Booking> get() {
        return bookings;
    }

    @Override
    public Collection<Booking> get(int customerId) {
        Collection<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getCustomer().getId() == customerId) {
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public void end(int bookingId) {
        Booking booking = bookings.stream()
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
    public void rate(int taxiId, double rating) {
        Booking booking = bookings.stream()
                .filter(b -> b.getTaxi().getId() == taxiId)
                .findFirst()
                .orElse(null);

        if (booking != null) {
            booking.getTaxi().getDriver().setRating(rating);
            System.out.println("Driver " + booking.getTaxi().getDriver().getName()
                    + " rated " + rating + " successfully.");
        } else {
            System.out.println("Taxi not found for rating.");
        }
    }
}
