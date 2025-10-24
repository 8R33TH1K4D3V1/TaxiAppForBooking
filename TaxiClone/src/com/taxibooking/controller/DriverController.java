package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverServiceImpl;

import java.util.Collection;


/**
 * Controller class that manages Driver operations.
 * Delegates all logic to the DriverService layer.
 */
public class DriverController {

    private final DriverServiceImpl driverService;

    public DriverController(final DriverServiceImpl driverService) {
        this.driverService = driverService;
    }

    /**
     * Retrieves a driver by their ID.
     */
    public Driver get(final int driverId) {
        return driverService.get(driverId);
    }



    /**
     * Retrieves all drivers as a Collection (for enhanced-for loops).
     */
    public Collection<Driver> get() {
        return driverService.get();
    }

    /**
     * Updates an existing driver's details.
     */
    public void update(final Driver driver) {
        driverService.update(driver);
    }
}
