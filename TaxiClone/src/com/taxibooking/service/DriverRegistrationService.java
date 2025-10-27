package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Service interface for Driver Registration operations.
 */
public interface DriverRegistrationService {

    void register(final Driver driver);

    void unregister(final int id);

    /** Factory method to get singleton instance */
    static DriverRegistrationService getInstance(Collection<Driver> drivers) {
        return DriverRegistrationServiceImpl.getInstance(drivers);
    }
}
