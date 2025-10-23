package com.taxibooking.service;

import com.taxibooking.model.Driver;

/**
 * Service interface for driver registration operations.
 * Defines methods to register and unregister drivers.
 */
public interface DriverRegistrationService {

    /**
     * Registers a new driver.
     */
    void register(final Driver driver);

    /**
     * Unregisters a driver by their ID.
     */
    void unregister(final int id);
}
