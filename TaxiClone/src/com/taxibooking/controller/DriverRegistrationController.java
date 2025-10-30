package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverRegistrationService;

/**
 * Handles driver registration operations (Singleton).
 */
public class DriverRegistrationController {

    private final DriverRegistrationService driverRegistrationService;

    /** Private constructor */
    private DriverRegistrationController() {
        this.driverRegistrationService = DriverRegistrationService.getInstance();
    }

    /** Internal static class for singleton instance */
    private static final class Instance {
        private static final DriverRegistrationController CONTROLLER = new DriverRegistrationController();
    }

    /** Returns singleton instance */
    public static DriverRegistrationController getInstance() {
        return Instance.CONTROLLER;
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
