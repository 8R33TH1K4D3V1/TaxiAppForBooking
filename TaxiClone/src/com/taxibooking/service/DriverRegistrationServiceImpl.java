package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Implementation of DriverRegistrationService.
 * Handles registering and unregistering drivers.
 * Hidden from external access — package-private.
 */
final class DriverRegistrationServiceImpl implements DriverRegistrationService {

    private final Collection<Driver> drivers;

    DriverRegistrationServiceImpl(final Collection<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public void register(final Driver driver) {
        drivers.add(driver);
        System.out.println("Driver registered successfully: " + driver.getName());
    }

    @Override
    public void unregister(final int id) {
        final Driver found = drivers.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);

        if (found != null) {
            drivers.remove(found);
            System.out.println("Driver removed successfully: " + found.getName());
        } else {
            System.out.println("Driver ID not found.");
        }
    }
}
