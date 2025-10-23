package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverRegistrationService;

/**
 * Controller for handling driver registration and unregistration.
 * Delegates all logic to the DriverRegistrationService layer.
 */
public class DriverRegistrationController {

    private final DriverRegistrationService driverRegistrationService;

    public DriverRegistrationController(final DriverRegistrationService driverRegistrationService) {
        this.driverRegistrationService = driverRegistrationService;
    }

    /**
     * Registers a new driver.
     */
    public void register(final Driver driver) {
        driverRegistrationService.register(driver);
    }

    /**
     * Unregisters a driver by their ID.
     */
    public void unregister(final int id) {
        driverRegistrationService.unregister(id);
    }
}
