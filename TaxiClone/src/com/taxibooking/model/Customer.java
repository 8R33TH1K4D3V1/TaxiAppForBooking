package com.taxibooking.model;

/**
 * Represents a customer in the taxi booking system.
 * Stores basic information: id and name.
 */
public class Customer {

    private int id;
    private String name;

    public Customer() {}

    /**
     * Full constructor to create a customer with id and name.
     */
    public Customer(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
