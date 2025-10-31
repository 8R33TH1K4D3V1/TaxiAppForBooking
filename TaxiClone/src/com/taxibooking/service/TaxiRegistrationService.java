package com.taxibooking.service;

import com.taxibooking.model.Taxi;

/** Handles taxi registration tasks */
public interface TaxiRegistrationService {

    /** Add taxi */
    void register(final Taxi taxi);

    /** Remove taxi by ID */
    boolean unregister(final int id);

    /** Singleton access to implementation */
    static TaxiRegistrationService getInstance() {
        return TaxiRegistrationServiceImpl.getInstance();
    }
}
