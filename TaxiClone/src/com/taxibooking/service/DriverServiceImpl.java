package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Singleton implementation of DriverService.
 * Handles all driver-related data operations.
 * (No exceptions thrown)
 */
public class DriverServiceImpl implements DriverService {

    private final Collection<Driver> driverList = new ArrayList<>();

    /** Private constructor */
    private DriverServiceImpl() {}

    /** Internal static class for singleton instance */
    private static final class Instance {
        private static final DriverServiceImpl SERVICE = new DriverServiceImpl();
    }

    /** Returns singleton instance */
    public static DriverServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    /** Update driver details safely */
    @Override
    public void update(final Driver updatedDriver) {

        if (Objects.isNull(updatedDriver)) {
            return;
        }

        Driver existingDriver = get(updatedDriver.getId());

        if (Objects.nonNull(existingDriver)) {
            driverList.remove(existingDriver);
        }

        driverList.add(updatedDriver);
    }

    /** Get driver by ID (null if not found) */
    @Override
    public Driver get(final int id) {

        for (Driver driver : driverList) {

            if (driver.getId() == id) {
                return driver;
            }
        }

        return null;
    }


    /** Get all drivers (read-only) */
    @Override
    public Collection<Driver> get() {
        return java.util.Collections.unmodifiableCollection(driverList);
    }

    /** Add driver safely */
    public void addDriver(final Driver driver) {

        if (Objects.nonNull(driver)) {
            driverList.add(driver);
        }
    }

    /** Remove driver safely (no error thrown) */
    public boolean removeDriver(final int driverId) {
        return driverList.remove(driverId);
    }
}
