package com.taxibooking.service;

import com.taxibooking.model.Driver;

/** Defines driver registration operations */
public interface DriverRegistrationService {

    /** Register a driver */
    void register(final Driver driver);

    /** Unregister a driver by ID */
    boolean unregister(final int id);

    /** Singleton access */
    static DriverRegistrationService getInstance() {
        return DriverRegistrationServiceImpl.getInstance();
    }
}
