package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/** Service interface for Taxi operations. */
public interface TaxiService {

    /** Returns all taxis (read-only). */
    Collection<Taxi> get();

    /** Get a single taxi by ID. */
    Taxi get(int id);


    /** Registers demo taxis. */
    void registerDemoTaxis();

}
