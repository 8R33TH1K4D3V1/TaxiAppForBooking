package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiRegistrationService;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller for Taxi Registration.
 * Exposes only necessary methods and hides the service.
 */
public class TaxiRegistrationController {

    private final TaxiRegistrationService service;
    private static TaxiRegistrationController instance;

    /** Private constructor hides service */
    private TaxiRegistrationController() {
        this.service = TaxiRegistrationService.getInstance(new ArrayList<>());
    }

    /** Singleton access */
    public static TaxiRegistrationController getInstance() {
        if (instance == null) {
            instance = new TaxiRegistrationController();
        }
        return instance;
    }

    /** Register a taxi */
    public void register(final Taxi taxi) {
        service.register(taxi);
    }

    /** Unregister a taxi */
    public void unregister(final int id) {
        service.unregister(id);
    }

    /** Get all taxis */
    public Collection<Taxi> get() {
        return service.get();
    }
}
