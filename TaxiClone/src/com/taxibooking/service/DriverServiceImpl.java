package com.taxibooking.service;

import com.taxibooking.model.Driver;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Collection;
import java.util.Collections;

/**
 * Singleton implementation of DriverService.
 * Uses Map for O(1) access and update operations.
 * Ensures efficient and safe driver management.
 */
public class DriverServiceImpl implements DriverService {

    /** Stores drivers by their ID for quick access */
    private final Map<Integer, Driver> driverMap = new HashMap<>();

    /** Private constructor */
    private DriverServiceImpl() {}

    /** Holder class for singleton instance */
    private static final class Instance {
        private static final DriverServiceImpl SERVICE = new DriverServiceImpl();
    }

    /** Returns singleton instance */
    public static DriverServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    /** Add or update driver details safely */
    @Override
    public void update(final Driver driver) {

        if (Objects.nonNull(driver)) {
            driverMap.put(driver.getId(), driver);
        }
    }

    /** Get driver by ID (null if not found) */
    @Override
    public Driver get(final int id) {
        return driverMap.get(id);
    }

    /** Get all drivers (read-only collection) */
    @Override
    public Collection<Driver> get() {
        return Collections.unmodifiableCollection(driverMap.values());
    }

    /** Add new taxi safely */
    public void addDriver(final Driver driver) {

        if (Objects.nonNull(driver)) {
            driverMap.put(driver.getId(), driver);
        }
    }

    public boolean removeDriver(final int id) {
        return Objects.nonNull(driverMap.remove(id));
    }


}
