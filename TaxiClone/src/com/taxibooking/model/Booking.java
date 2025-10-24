package com.taxibooking.model;

/**
 * Represents a taxi booking in the system.
 * Contains booking details and active status.
 */
public class Booking {

    private final int id;
    private final Taxi taxi;
    private final Customer customer;
    private final String pickupLocation;
    private final String dropLocation;
    private final double fare;
    private boolean isActive;

    /** Constructor to create a booking. Booking is active by default. */
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

    /** Getters */
    public int getId() { return id; }

    public Taxi getTaxi() { return taxi; }

    public Customer getCustomer() { return customer; }

    public String getPickupLocation() { return pickupLocation; }

    public String getDropLocation() { return dropLocation; }

    public double getFare() { return fare; }

    public boolean isActive() { return isActive; }

    /** Setter */
    public void setActive(final boolean active) {
        this.isActive = active;
    }
}
