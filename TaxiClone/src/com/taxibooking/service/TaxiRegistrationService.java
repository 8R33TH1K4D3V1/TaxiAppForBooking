package com.taxibooking.service;

import com.taxibooking.model.Taxi;

/**
 * Service interface for taxi registration operations.
 * Defines methods to register and unregister taxis.
 */
public interface TaxiRegistrationService {

    /**
     * Registers a new taxi.
     */
    void register(final Taxi taxi);

    /**
     * Unregisters a taxi by its ID.
     */
    void unregister(final int id);
}
