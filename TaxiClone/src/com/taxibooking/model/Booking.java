package com.taxibooking.model;

import java.util.Objects;

/**
 * Represents a taxi booking in the system.
 * Contains booking details and active status.
 */
public class Booking {

    private int id;
    private Taxi taxi;
    private Customer customer;
    private String pickupLocation;
    private String dropLocation;
    private double fare;
    private boolean isActive;

    /** No-argument constructor */
    public Booking() {
    }

    /** Getters and Setters */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Taxi getTaxi() { return taxi; }
    public void setTaxi(Taxi taxi) {
        this.taxi = Objects.requireNonNull(taxi, "Taxi cannot be null");
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) {
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
    }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = Objects.requireNonNullElse(pickupLocation, "Unknown Pickup");
    }

    public String getDropLocation() { return dropLocation; }
    public void setDropLocation(String dropLocation) {
        this.dropLocation = Objects.requireNonNullElse(dropLocation, "Unknown Drop");
    }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}
