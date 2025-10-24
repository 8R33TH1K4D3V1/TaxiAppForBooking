package com.taxibooking.model;

/**
 * Represents a customer in the taxi booking system.
 */
public class Customer {

    private int id;
    private final String name;

    /** Constructor to create a customer with id and name. */
    public Customer(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /** Getters */
    public int getId() { return id; }

    public String getName() { return name; }

    /** Setter*/
    public void setId(final int id) {
        this.id = id;
    }
}
