package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/**
 * Service interface for driver-related operations.
 */
public interface DriverService {

    /** Update driver info */
    void update(final Driver driver);

    /** Get driver by ID */
    Driver get(final int id);

    /** Get all drivers */
    Collection<Driver> get();

    /** Singleton access to DriverService instance */
    static DriverService getInstance() {
        return DriverServiceImpl.getInstance();
    }
}