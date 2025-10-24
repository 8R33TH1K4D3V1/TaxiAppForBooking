package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Service interface for managing drivers.
 * Provides operations to update and retrieve driver details.
 */
public interface DriverService {

    /** Updates an existing driver. */
    void update(final Driver driver);

    /** Retrieves a driver by ID. */
    Driver get(final int id);

    /** Retrieves all drivers. */
    Collection<Driver> get();

    /** Factory method to get a DriverService instance with a driver list. */
    static DriverService getInstance(final Collection<Driver> drivers) {
        return DriverServiceImpl.getInstance(drivers);
    }
}
