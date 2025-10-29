package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverServiceImpl;
import java.util.Collection;

/** Handles driver-related actions */
public class DriverController {

    private final DriverServiceImpl driverService;

    /** Private constructor */
    private DriverController() {
        this.driverService = DriverServiceImpl.getInstance();
    }

    /** Internal static class for singleton instance */
    private static final class Instance {
        private static final DriverController CONTROLLER = new DriverController();
    }

    /** Returns singleton instance */
    public static DriverController getInstance() {
        return Instance.CONTROLLER;
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
