package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;
import java.util.Objects;

/**
 * Package-private implementation of DriverRegistrationService.
 * Hidden from external access.
 */
class DriverRegistrationServiceImpl implements DriverRegistrationService {

    private static DriverRegistrationServiceImpl instance;
    private final Collection<Driver> drivers;

    /** Private constructor — only accessible through getInstance() */
    private DriverRegistrationServiceImpl() {
        this.drivers = DriverService.getInstance().get();
    }

    /** Singleton access — called from interface factory method */
    static DriverRegistrationServiceImpl getInstance() {

        if (Objects.isNull(instance)) {
            instance = new DriverRegistrationServiceImpl();
        }
        return instance;
    }

    @Override
    public void register(final Driver driver) {
        drivers.add(driver);

        System.out.println("Driver registered successfully: " + driver.getName());
    }

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
