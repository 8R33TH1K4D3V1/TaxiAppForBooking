package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

class DriverServiceImpl implements DriverService {

    private final Collection<Driver> drivers;
    private static DriverService instance;

    private DriverServiceImpl(final Collection<Driver> drivers) {
        this.drivers = drivers;
    }

    public static DriverService getInstance(final Collection<Driver> drivers) {
        if (instance == null) {
            instance = new DriverServiceImpl(drivers);
        }
        return instance;
    }

    @Override
    public void update(final Driver updatedDriver) {
        final Driver existingDriver = get(updatedDriver.getId());
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
