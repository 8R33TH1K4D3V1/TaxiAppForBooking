package com.taxibooking.model;

/**
 * Represents a taxi booking.
 * Stores taxi, customer, pickup/drop locations, fare, and active status.
 */
public class Booking {

    private int id;
    private Taxi taxi;
    private Customer customer;
    private String pickupLocation;
    private String dropLocation;
    private double fare;
    private boolean isActive;

    public Booking() {}

    /**
     * Full constructor to create a booking with all details.
     */
    public Booking(final int id, final Taxi taxi, final Customer customer,
                   final String pickupLocation, final String dropLocation, final double fare) {
        this.id = id;
        this.taxi = taxi;
        this.customer = customer;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.fare = fare;
        this.isActive = true;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public double getFare() {
        return fare;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status of the booking.
     */
    public void setActive(final boolean active) {
        this.isActive = active;
    }
}
