package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiService;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Controller class that manages Taxi operations.
 * Delegates logic to the TaxiService layer.
 */
public class TaxiController {

    private final TaxiService taxiService;

    /**
     * Constructor using singleton TaxiService.
     */
    public TaxiController() {
        this.taxiService = TaxiService.getInstance(new ArrayList<>()); // empty list passed
    }

    /**
     * Retrieves all taxis as a new list (to avoid external modifications).
     */
    public Collection<Taxi> get() {
        return new ArrayList<>(taxiService.get());
    }

    /**
     * Retrieves a taxi by its ID.
     */
    public Taxi get(int id) {
        for (Taxi taxi : get()) {
            if (taxi.getId() == id) {
                return taxi;
            }
        }
        return null;
    }

    /**
     * Registers demo taxis through the service.
     */
    public void registerDemoTaxis() {
        taxiService.registerDemoTaxis();
    }

    /**
     * Adds a taxi via the service.
     */
    public void get(Taxi taxi) {
        taxiService.get(taxi);
    }
}
