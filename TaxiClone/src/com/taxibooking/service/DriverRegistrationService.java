package com.taxibooking.service;

import com.taxibooking.model.Driver;

/** Defines driver registration operations */
public interface DriverRegistrationService {

    /** Register a driver */
    int add(final Driver driver);

    /** Unregister a driver by ID */
    boolean remove(final int id);

    /** Singleton access */
    static DriverRegistrationService getInstance() {
        return DriverRegistrationServiceImpl.getInstance();
    }
}
