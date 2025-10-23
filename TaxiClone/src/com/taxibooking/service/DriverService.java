package com.taxibooking.service;

import com.taxibooking.model.Driver;

import java.util.Collection;

/**
 * Service interface for managing drivers.
 * Defines operations to update and retrieve driver information.
 */
public interface DriverService {

    /**
     * Updates the details of a driver.
     */
    void update(final Driver driver);

    /**
     * Retrieves a driver by their ID.
     */
    Driver get(final int id);

    /**
     * Retrieves all drivers as a collection.
     */
    Collection<Driver> get();
}
