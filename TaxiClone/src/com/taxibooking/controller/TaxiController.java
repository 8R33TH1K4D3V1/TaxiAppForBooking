package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiService;

import java.util.Collection;
import java.util.Objects;

/**
 * Controller for Taxi operations.
 * Delegates logic to TaxiService and hides service details.
 */
public class TaxiController {

    private final TaxiService taxiService;
    private static TaxiController instance;

    private TaxiController() {
        this.taxiService = TaxiService.getInstance();
    }

    public static TaxiController getInstance() {

        if (Objects.isNull(instance)) {
            instance = new TaxiController();
        }
        return instance;
    }

    /** Get all taxis*/
    public Collection<Taxi> get() {
        return taxiService.get();
    }

    /** Get taxi by ID */
    public Taxi get(final int id) {
        return taxiService.get(id);
    }


    /** Register demo taxis */
    public void registerDemoTaxis() {
        taxiService.registerDemoTaxis();
    }
}
