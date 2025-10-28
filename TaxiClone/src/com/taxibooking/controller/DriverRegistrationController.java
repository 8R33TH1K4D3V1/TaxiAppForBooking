package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverRegistrationService;

import java.util.Objects;

/**
 * Handles driver registration operations (Singleton).
 */
public class DriverRegistrationController {

    private final DriverRegistrationService driverRegistrationService;
    private static DriverRegistrationController instance;

    /** Private constructor for Singleton */
    private DriverRegistrationController() {
        this.driverRegistrationService = DriverRegistrationService.getInstance();
    }

    /** Returns Singleton instance */
    public static DriverRegistrationController getInstance() {

        if (Objects.isNull(instance)) {
            instance = new DriverRegistrationController();
        }
        return instance;
    }

    /** Registers a driver */
    public void register(final Driver driver) {
        driverRegistrationService.register(driver);
    }

    /** Removes a driver by ID */
    public void unregister(final int id) {
        driverRegistrationService.unregister(id);
    }
}
