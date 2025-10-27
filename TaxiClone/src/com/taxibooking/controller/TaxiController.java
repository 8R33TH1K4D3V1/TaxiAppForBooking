package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiService;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Controller for Taxi operations.
 * Delegates all logic to TaxiService and hides the service from other classes.
 */
public class TaxiController {

    private final TaxiService taxiService;
    private static TaxiController instance;

    private TaxiController() {
        this.taxiService = TaxiService.getInstance(new ArrayList<>());
    }

    public static TaxiController getInstance() {
        if (instance == null) {
            instance = new TaxiController();
        }
        return instance;
    }

    /** Get all taxis */
    public Collection<Taxi> get() {
        return taxiService.get();
    }

    /** Get a taxi by ID */
    public Taxi get(final int id) {
        for (Taxi taxi : taxiService.get()) {
            if (taxi.getId() == id) return taxi;
        }
        return null;
    }

    /** Add a taxi */
    public void get(final Taxi taxi) {
        taxiService.get(taxi);
    }

    /** Register demo taxis */
    public void registerDemoTaxis() {
        taxiService.registerDemoTaxis();
    }
}
