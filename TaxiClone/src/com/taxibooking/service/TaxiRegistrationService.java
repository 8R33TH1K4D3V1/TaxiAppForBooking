package com.taxibooking.service;

import com.taxibooking.model.Taxi;

/** Handles taxi registration tasks */
public interface TaxiRegistrationService {

    /** Add taxi */
    void register(final Taxi taxi);

    /** Remove taxi by ID */
    void unregister(final int id);

}
