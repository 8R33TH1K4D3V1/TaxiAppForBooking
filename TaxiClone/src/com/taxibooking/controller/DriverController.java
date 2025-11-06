package com.taxibooking.controller;

import com.taxibooking.model.Driver;
import com.taxibooking.service.DriverService;
import java.util.Collection;

/**
 * Controller for managing driver operations (Singleton).
 * Delegates all operations to DriverService.
 */
public class DriverController {

    private final DriverService driverService;

    /** Private constructor — hides implementation */
    private DriverController() {
        this.driverService = DriverService.getInstance();
    }

    /** Internal static class for singleton instance */
    private static final class Instance {
        private static final DriverController CONTROLLER = new DriverController();
    }

    /** Returns singleton instance */
    public static DriverController getInstance() {
        return Instance.CONTROLLER;
    }

    /** Update driver details */
    public void update(final Driver driver) {
        driverService.update(driver);
    }

    /** Get driver by ID */
    public Driver get(final int id) {
        return driverService.get(id);
    }

    /** Get all drivers */
    public Collection<Driver> get() {
        return driverService.get();
    }
}
