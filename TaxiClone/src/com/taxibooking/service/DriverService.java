package com.taxibooking.service;

import com.taxibooking.model.Driver;
import java.util.Collection;

/** Handles driver data operations */
public interface DriverService {

    /** Update driver info */
    void update(final Driver driver);

    /** Get driver by ID */
    Driver get(final int id);

    /** Get all drivers */
    Collection<Driver> get();


}
