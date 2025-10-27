package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverService;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller for driver operations.
 * Delegates all logic to DriverService.
 */
public class DriverController {
    private final DriverService driverService;
    private static DriverController instance;

    /** Private constructor to enforce Singleton pattern */
    private DriverController() {
        this.driverService = DriverService.getInstance(new ArrayList<>());
    }

    /** Returns the single shared instance */
    public static DriverController getInstance() {
        if (instance == null) {
            instance = new DriverController();
        }
        return instance;
    }

    /** Gets a driver by ID */
    public Driver get(final int driverId) {
        return driverService.get(driverId);
    }

    /** Updates driver details */
    public void update(final Driver driver) {
        driverService.update(driver);
    }

    /** Returns all drivers */
    public Collection<Driver> get() {
        return driverService.get();
    }
}
