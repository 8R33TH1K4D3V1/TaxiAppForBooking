package com.taxibooking.service;

import com.taxibooking.model.Taxi;

/** Handles taxi registration tasks */
public interface TaxiRegistrationService {

    /** Add taxi */
    int add(final Taxi taxi);

    /** Remove taxi by ID */
    boolean remove(final int id);

    /** Singleton access to implementation */
    static TaxiRegistrationService getInstance() {
        return TaxiRegistrationServiceImpl.getInstance();
    }
}
