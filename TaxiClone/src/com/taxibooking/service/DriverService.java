package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Service interface for managing drivers.
 */
public interface DriverService {

    /** Update an existing driver */
    void update(final Driver driver);

    /** Get driver by ID */
    Driver get(final int id);

    /** Get all drivers */
    Collection<Driver> get();

    /** Factory method to get singleton instance */
    static DriverService getInstance(Collection<Driver> drivers) {
        return DriverServiceImpl.getInstance(drivers);
    }
}
