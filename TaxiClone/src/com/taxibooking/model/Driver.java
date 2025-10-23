package com.taxibooking.model;

/**
 * Represents a driver in the taxi booking system.
 * Stores id, name, phone number, and rating.
 */
public class Driver {

    private int id;
    private String phoneNo;
    private double rating;
    private String name;

    public Driver() {}

    /**
     * Full constructor to create a driver with all details.
     */
    public Driver(final int id, final String phoneNo, final double rating, final String name) {
        this.id = id;
        this.phoneNo = phoneNo;
        this.rating = rating;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    // Setters
    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
