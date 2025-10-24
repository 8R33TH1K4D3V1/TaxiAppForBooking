package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Implementation of DriverService.
 * Manages updating and retrieving drivers from a shared driver collection.
 */
public class DriverServiceImpl implements DriverService {

    private final Collection<Driver> drivers;

    public DriverServiceImpl(final Collection<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public void update(final Driver updatedDriver) {
        Driver existingDriver = get(updatedDriver.getId());
        if (existingDriver != null) {
            drivers.remove(existingDriver);
            drivers.add(updatedDriver);
        }
    }

    @Override
    public Driver get(final int id) {
        return drivers.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Driver> get() {
        return drivers;
    }
}
