package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Singleton implementation of DriverRegistrationService.
 * Hidden from external classes.
 */
class DriverRegistrationServiceImpl implements DriverRegistrationService {

    private final Collection<Driver> drivers;
    private static DriverRegistrationService instance;

    private DriverRegistrationServiceImpl(Collection<Driver> drivers) {
        this.drivers = drivers;
    }

    public static DriverRegistrationService getInstance(Collection<Driver> drivers) {
        if (instance == null) {
            instance = new DriverRegistrationServiceImpl(drivers);
        }
        return instance;
    }

    @Override
    public void register(final Driver driver) {
        drivers.add(driver);
        System.out.println("Driver registered successfully: " + driver.getName());
    }

    @Override
    public void unregister(final int id) {
        Driver found = drivers.stream()
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
