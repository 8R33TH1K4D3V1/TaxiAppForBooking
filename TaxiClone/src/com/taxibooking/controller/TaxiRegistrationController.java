package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiRegistrationService;

/**
 * Controller for handling taxi registration and unregistration.
 * Delegates logic to the TaxiRegistrationService layer.
 */
public class TaxiRegistrationController {

    private final TaxiRegistrationService service;

    public TaxiRegistrationController(final TaxiRegistrationService service) {
        this.service = service;
    }

    /**
     * Registers a new taxi.
     */
    public void register(final Taxi taxi) {
        service.register(taxi);
    }

    /**
     * Unregisters a taxi by its ID.
     */
    public void unregister(final int id) {
        service.unregister(id);
    }
}
