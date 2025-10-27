package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/** Service interface for Taxi operations. */
public interface TaxiService {

    /** Returns all taxis. */
    Collection<Taxi> get();

    /** Registers demo taxis. */
    void registerDemoTaxis();

    /** Gets details of a taxi. */
    void get(final Taxi taxi);

    /** Returns singleton instance. */
    static TaxiService getInstance(Collection<Taxi> taxis) {
        return TaxiServiceImpl.getInstance(taxis);
    }
}
