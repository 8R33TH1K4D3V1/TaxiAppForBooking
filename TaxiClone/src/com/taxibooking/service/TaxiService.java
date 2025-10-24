package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/**
 * Service interface for managing taxis.
 * Defines operations for retrieving taxis, registering demo taxis,
 * and adding a taxi to the system.
 */
public interface TaxiService {

    /** Retrieves all taxis in the system. */
    Collection<Taxi> get();

    /** Registers demo taxis for initial setup. */
    void registerDemoTaxis();

    /** Adds a taxi to the system (method name kept as 'get' to preserve original logic). */
    void get(Taxi taxi);

    /**
     * Factory method to obtain a TaxiService instance.
     */
    static TaxiService getInstance(Collection<Taxi> taxis) {
        return TaxiServiceImpl.getInstance(taxis);
    }
}
