package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiRegistrationService;
import java.util.Objects;

/**
 * Controller for Taxi Registration.
 * Exposes only necessary methods and hides the service.
 */
public class TaxiRegistrationController {

    private final TaxiRegistrationService service;
    private static TaxiRegistrationController instance;

    /** Private constructor hides service */
    private TaxiRegistrationController() {
        this.service = TaxiRegistrationService.getInstance();
    }

    /** Singleton access */
    public static TaxiRegistrationController getInstance() {

        if (Objects.isNull(instance)) {
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


}
