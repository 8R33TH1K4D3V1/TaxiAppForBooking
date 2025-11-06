package com.taxibooking.model;

/**
 * Represents a driver in the taxi booking system.
 * Stores driver details like ID, name, phone number, and rating.
 */
public class Driver {

    private int id;
    private String phoneNo;
    private double rating;
    private String name;

    /** Default constructor */
    public Driver() {}

    /** Getters */
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

    /** Setters */
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
