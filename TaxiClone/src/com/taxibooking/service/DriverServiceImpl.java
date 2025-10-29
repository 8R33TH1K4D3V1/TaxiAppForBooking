package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Singleton implementation of DriverService using internal static class.
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

    /** Update driver details */
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

    /** Get driver by ID */
    @Override
    public Driver get(final int id) {
        return driverList.stream()
                .filter(driver -> driver.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** Get all drivers */
    @Override
    public Collection<Driver> get() {
        return driverList;
    }
}
