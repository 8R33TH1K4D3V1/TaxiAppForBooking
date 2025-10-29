package com.taxibooking.controller;

import com.taxibooking.model.Taxi;
import com.taxibooking.service.TaxiServiceImpl;
import java.util.Collection;

/** Handles taxi-related operations (Singleton). */
public class TaxiController {

    private final TaxiServiceImpl taxiService;

    /** Private constructor */
    private TaxiController() {
        this.taxiService = TaxiServiceImpl.getInstance();
    }

    /** Singleton instance holder */
    private static final class Instance {
        private static final TaxiController CONTROLLER = new TaxiController();
    }

    /** Returns the singleton instance */
    public static TaxiController getInstance() {
        return Instance.CONTROLLER;
    }

    /** Returns all taxis */
    public Collection<Taxi> get() {
        return taxiService.get();
    }

    /** Returns taxi by ID */
    public Taxi get(final int id) {
        return taxiService.get(id);
    }

    /** Registers demo taxis */
    public void registerDemoTaxis() {
        taxiService.registerDemoTaxis();
    }
}
