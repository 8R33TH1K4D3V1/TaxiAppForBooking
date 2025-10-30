package com.taxibooking.service;

import com.taxibooking.model.Driver;

import java.util.Objects;


/**
 * Singleton implementation of DriverRegistrationService.
 * Delegates data operations to DriverServiceImpl.
 * Business layer only — no console outputs here.
 */
public class DriverRegistrationServiceImpl implements DriverRegistrationService {

    private final DriverServiceImpl driverService;

    /** Private constructor */
    private DriverRegistrationServiceImpl() {
        this.driverService = DriverServiceImpl.getInstance();
    }

    /** Internal static class for Singleton instance */
    private static final class Instance {
        private static final DriverRegistrationServiceImpl SERVICE = new DriverRegistrationServiceImpl();
    }

    /** Returns Singleton instance */
    public static DriverRegistrationServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    /** Registers a new driver */
    @Override
    public void register(final Driver driver) {

        if (Objects.nonNull(driver)) {
            driverService.addDriver(driver);
        }
    }

    /** Unregisters a driver by ID */
    @Override
    public void unregister(final int driverId) {
        driverService.removeDriver(driverId);
    }


}
