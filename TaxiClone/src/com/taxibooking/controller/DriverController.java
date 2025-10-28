package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverService;
import java.util.Collection;
import java.util.Objects;

/** Handles driver-related actions */
public class DriverController {

    private final DriverService driverService;
    private static DriverController instance;

    /** Private constructor */
    private DriverController() {
        this.driverService = DriverService.getInstance();
    }

    /** Singleton access */
    public static DriverController getInstance() {

        if (Objects.isNull(instance)) {
            instance = new DriverController();
        }
        return instance;
    }

    /** Get driver by ID */
    public Driver get(final int id) {
        return driverService.get(id);
    }

    /** Update driver details */
    public void update(final Driver driver) {
        driverService.update(driver);
    }

    /** Get all drivers */
    public Collection<Driver> get() {
        return driverService.get();
    }
}



