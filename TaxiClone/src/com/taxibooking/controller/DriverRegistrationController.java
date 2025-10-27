package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverRegistrationService;
import java.util.ArrayList;

/**
 * Controller for Driver Registration.
 * Uses Singleton pattern.
 */
public class DriverRegistrationController {

    private final DriverRegistrationService driverRegistrationService;
    private static DriverRegistrationController instance;

    /** Private constructor to prevent external instantiation */
    private DriverRegistrationController() {
        this.driverRegistrationService = DriverRegistrationService.getInstance(new ArrayList<>());
    }

    /** Returns the single shared instance */
    public static DriverRegistrationController getInstance() {
        if (instance == null) {
            instance = new DriverRegistrationController();
        }
        return instance;
    }

    /** Registers a new driver */
    public void register(final Driver driver) {
        driverRegistrationService.register(driver);
    }

    /** Removes a driver by ID */
    public void unregister(final int id) {
        driverRegistrationService.unregister(id);
    }
}
