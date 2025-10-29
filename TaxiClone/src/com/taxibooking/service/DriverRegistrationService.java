package com.taxibooking.service;

import com.taxibooking.model.Driver;

/** Defines driver registration operations */
public interface DriverRegistrationService {

    /** Register a driver */
    void register(final Driver driver);

    /** Unregister a driver by ID */
    void unregister(final int id);

}
