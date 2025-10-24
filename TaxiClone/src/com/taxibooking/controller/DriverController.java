package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverService;
import java.util.Collection;

/**
 * Controller for driver operations.
 * Delegates all logic to DriverService.
 */
public class DriverController {

    private final DriverService driverService;

    /** Constructor injection of DriverService */
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /** Get a driver by ID */
    public Driver get(int driverId) {
        return driverService.get(driverId);
    }

    /** Get all drivers */
    public Collection<Driver> get() {
        return driverService.get();
    }

    /** Update driver details */
    public void update(Driver driver) {
        driverService.update(driver);
    }
}
