package com.taxibooking.model;

/**
 * Represents a taxi in the taxi booking system.
 * Stores id, assigned driver, seater count, AC availability, and current availability.
 */
public class Taxi {

    private int id;
    private Driver driver;
    private int seater;
    private boolean isAcAvailable;
    private boolean isAvailable;

    public Taxi() {}

    /**
     * Full constructor to create a taxi with all details.
     */
    public Taxi(final int id, final Driver driver, final int seater,
                final boolean isAcAvailable, final boolean isAvailable) {
        this.id = id;
        this.driver = driver;
        this.seater = seater;
        this.isAcAvailable = isAcAvailable;
        this.isAvailable = isAvailable;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getSeater() {
        return seater;
    }

    public boolean isAcAvailable() {
        return isAcAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Setters
    public void setId(final int id) {
        this.id = id;
    }

    public void setDriver(final Driver driver) {
        this.driver = driver;
    }

    public void setSeater(final int seater) {
        this.seater = seater;
    }

    public void setAcAvailable(final boolean acAvailable) {
        this.isAcAvailable = acAvailable;
    }

    public void setAvailable(final boolean available) {
        this.isAvailable = available;
    }
}
