package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/**
 * Service interface for taxi registration operations.
 */
public interface TaxiRegistrationService {

    /** Register a new taxi */
    void register(final Taxi taxi);

    /** Unregister a taxi by ID */
    void unregister(final int id);

    /** Retrieve all registered taxis */
    Collection<Taxi> get();

    /** Factory method to obtain instance */
    static TaxiRegistrationService getInstance(Collection<Taxi> taxis) {
        return new TaxiRegistrationServiceImpl(taxis);
    }
}
