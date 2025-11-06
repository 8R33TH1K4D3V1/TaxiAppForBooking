package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiRegistrationService;

/**
 * Handles taxi registration operations (Singleton).
 */
public class TaxiRegistrationController {

    private final TaxiRegistrationService taxiRegistrationService;

    /** Private constructor */
    private TaxiRegistrationController() {
        this.taxiRegistrationService = TaxiRegistrationService.getInstance();
    }

    /** Internal static class for singleton instance */
    private static final class Instance {
        private static final TaxiRegistrationController CONTROLLER = new TaxiRegistrationController();
    }

    /** Returns singleton instance */
    public static TaxiRegistrationController getInstance() {
        return Instance.CONTROLLER;
    }

    /** Registers a taxi */
    public int add(final Taxi taxi) {
        return taxiRegistrationService.add(taxi);
    }

    /** Removes a taxi by ID */
    public boolean remove(final int id) {
        return taxiRegistrationService.remove(id);
    }

}
