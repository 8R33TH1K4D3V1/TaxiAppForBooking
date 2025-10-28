package com.taxibooking.model;

/**
 * Represents a customer in the taxi booking system.
 */
public class Customer {

    private int id;
    private String name;

    /** Default constructor */
    public Customer() {}

    /** Getters */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** Setters */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
