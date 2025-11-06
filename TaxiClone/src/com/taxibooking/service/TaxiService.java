package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/** Service interface for Taxi operations. */
public interface TaxiService {

    /** Returns all taxis (read-only). */
    Collection<Taxi> get();

    /** Get a single taxi by ID. */
    Taxi get(final int id);

    /** Registers demo taxis. */
    void registerDemoTaxis();

    /** Singleton access */
    static TaxiService getInstance() {
        return TaxiServiceImpl.getInstance();
    }
}
