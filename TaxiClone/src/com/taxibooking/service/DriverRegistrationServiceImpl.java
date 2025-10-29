package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Singleton implementation of DriverRegistrationService.
 * Uses internal static class for instance management.
 */
public class DriverRegistrationServiceImpl implements DriverRegistrationService {

    private final Collection<Driver> drivers;

    /** Private constructor — only accessible within this class */
    private DriverRegistrationServiceImpl() {
        this.drivers = DriverServiceImpl.getInstance().get();
    }

    /** Internal static class for Singleton instance */
    private static final class Instance {
        private static final DriverRegistrationServiceImpl SERVICE = new DriverRegistrationServiceImpl();
    }

    /** Returns the Singleton instance */
    public static DriverRegistrationServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    /** Registers a driver */
    @Override
    public void register(final Driver driver) {
        drivers.add(driver);

        System.out.println("Driver registered successfully: " + driver.getName());
    }

    /** Removes a driver by ID */
    @Override
    public void unregister(final int driverId) {
        boolean removed = drivers.removeIf(driver -> driver.getId() == driverId);

        if (removed) {
            System.out.println("Driver with ID " + driverId + " unregistered successfully.");
        } else {
            System.out.println("Driver ID not found.");
        }
    }
}
