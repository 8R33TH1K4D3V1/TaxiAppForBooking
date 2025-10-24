package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverRegistrationService;

/**
 * Controller for driver registration operations.
 * Delegates all logic to the DriverRegistrationService layer.
 */
public class DriverRegistrationController {

    private final DriverRegistrationService driverRegistrationService;

    /** Constructor injection of DriverRegistrationService */
    public DriverRegistrationController(final DriverRegistrationService driverRegistrationService) {
        this.driverRegistrationService = driverRegistrationService;
    }

    /** Register a new driver */
    public void register(final Driver driver) {
        driverRegistrationService.register(driver);
    }

    /** Unregister a driver by ID */
    public void unregister(final int id) {
        driverRegistrationService.unregister(id);
    }
}
