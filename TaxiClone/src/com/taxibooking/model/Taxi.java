package com.taxibooking.model;

/**
 * Represents a taxi in the taxi booking system.
 * Stores ID, assigned driver, seater count, AC availability, and current availability.
 */
public class Taxi {

    private int id;
    private Driver driver;
    private int seater;
    private boolean isAcAvailable;
    private boolean isAvailable;

    /** Default constructor */
    public Taxi() {}

    /** Getters */
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

    /** Setters */
    public void setId(int id) {
        this.id = id;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setSeater(int seater) {
        this.seater = seater;
    }

    public void setAcAvailable(boolean acAvailable) {
        this.isAcAvailable = acAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}
