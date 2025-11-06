package com.taxibooking.model;

/**
 * Represents a taxi in the taxi booking system.
 * Stores ID, assigned driver, seater count, AC availability, and current availability.
 */


public class Taxi {
    private int id;
    private int seater;
    private boolean acAvailable;
    private boolean available;

    private Driver driver = new Driver();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSeater() { return seater; }
    public void setSeater(int seater) { this.seater = seater; }

    public boolean isAcAvailable() { return acAvailable; }
    public void setAcAvailable(boolean acAvailable) { this.acAvailable = acAvailable; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
}
