package com.taxibooking.service;

import com.taxibooking.model.Driver;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of DriverService.
 * Manages updating and retrieving drivers from a shared driver list.
 */
public class DriverServiceImpl implements DriverService {

    private final List<Driver> drivers;

    public DriverServiceImpl(final List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public void update(final Driver driver) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId() == driver.getId()) {
                drivers.set(i, driver);
                return;
            }
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

    /**
     * Retrieves all drivers as a List.
     */
    public List<Driver> getAllDrivers() {
        return drivers;
    }
}
