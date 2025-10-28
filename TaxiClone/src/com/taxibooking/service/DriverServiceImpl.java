package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Singleton implementation of DriverService.
 */
class DriverServiceImpl implements DriverService {

    private final Collection<Driver> driverList = new ArrayList<>();
    private static DriverService instance;

    private DriverServiceImpl() {}

    /** Singleton instance — no parameters */
    public static DriverService getInstance() {

        if (Objects.isNull(instance)) {
            instance = new DriverServiceImpl();
        }
        return instance;
    }


    /** Update driver details */
    @Override
    public void update(final Driver updatedDriver) {

        if (Objects.isNull(updatedDriver)){
            return;
        }

        Driver existingDriver = get(updatedDriver.getId());

        if (Objects.nonNull(existingDriver)) {
            driverList.remove(existingDriver);
            driverList.add(updatedDriver);
        }
    }

    /** Get driver by ID */
    @Override
    public Driver get(final int id) {
        return driverList.stream()
                .filter(driver -> driver.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Driver> get() {
        return driverList;
    }

}
