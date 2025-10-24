package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiRegistrationService;

/**
 * Controller for taxi registration operations.
 * Delegates registration/unregistration to the TaxiRegistrationService.
 */
public class TaxiRegistrationController {

    private final TaxiRegistrationService service;

    /**
     * Constructor injecting the TaxiRegistrationService dependency.
     */
    public TaxiRegistrationController(final TaxiRegistrationService service) {
        this.service = service;
    }

    /**
     * Registers a new taxi via the service.
     */
    public void register(final Taxi taxi) {
        service.register(taxi);
    }

    /**
     * Unregisters a taxi by its ID via the service.
     */
    public void unregister(final int id) {
        service.unregister(id);
    }
}
