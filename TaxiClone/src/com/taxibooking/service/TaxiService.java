package com.taxibooking.service;

import com.taxibooking.model.Taxi;

import java.util.List;

/**
 * Service interface for taxi management.
 * Defines operations to retrieve taxis and register demo taxis.
 */
public interface TaxiService {

    /**
     * Retrieves all taxis as a list.
     */
    List<Taxi> get();

    /**
     * Registers demo taxis for initial setup.
     */
    void registerDemoTaxis();

    /**
     * Processes or retrieves a specific taxi (implementation-specific).
     */
    void get(final Taxi taxi);
}
