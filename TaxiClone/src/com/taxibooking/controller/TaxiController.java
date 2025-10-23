package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiService;

import java.util.List;
import java.util.ArrayList;

/**
 * Controller class that manages Taxi operations.
 * Delegates logic to the TaxiService layer.
 */
public class TaxiController {

    private final TaxiService taxiService;

    public TaxiController(final TaxiService taxiService) {
        this.taxiService = taxiService;
    }

    /**
     * Retrieves all taxis as a new list.
     */
    public List<Taxi> get() {
        return new ArrayList<>(taxiService.get());
    }

    /**
     * Retrieves a taxi by its ID.
     */
    public Taxi get(final int id) {
        for (final Taxi taxi : get()) {
            if (taxi.getId() == id) {
                return taxi;
            }
        }
        return null;
    }
}
