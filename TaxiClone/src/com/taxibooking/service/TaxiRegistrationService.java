package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/**
 * Service interface for taxi registration operations.
 * Defines methods to register, unregister, and retrieve taxis.
 * Includes a static factory method for instance creation.
 */
public interface TaxiRegistrationService {

    /** Registers a new taxi. */
    void register(final Taxi taxi);

    /** Unregisters a taxi by its ID. */
    void unregister(final int id);

    /** Retrieves all registered taxis. */
    Collection<Taxi> get();

    /** Factory method to obtain a TaxiRegistrationService instance. */
    static TaxiRegistrationService getInstance(final Collection<Taxi> taxis) {
        return new TaxiRegistrationServiceImpl(taxis);
    }
}
