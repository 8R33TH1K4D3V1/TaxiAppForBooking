package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Service interface for driver registration operations.
 * Defines methods to register and unregister drivers.
 */
public interface DriverRegistrationService {

    /** Registers a new driver. */
    void register(final Driver driver);

    /** Unregisters a driver by ID. */
    void unregister(final int id);

    /** Factory method to get a DriverRegistrationService instance. */
    static DriverRegistrationService getInstance(final Collection<Driver> drivers) {
        return new DriverRegistrationServiceImpl(drivers);
    }
}
