package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/**
 * Singleton implementation of TaxiRegistrationService.
 * Uses internal static class for instance management.
 */
public class TaxiRegistrationServiceImpl implements TaxiRegistrationService {

    private final Collection<Taxi> taxis;

    /** Private constructor — only accessible within this class */
    private TaxiRegistrationServiceImpl() {
        this.taxis = TaxiServiceImpl.getInstance().get();
    }

    /** Internal static class for Singleton instance */
    private static final class Instance {
        private static final TaxiRegistrationServiceImpl SERVICE = new TaxiRegistrationServiceImpl();
    }

    /** Returns the Singleton instance */
    public static TaxiRegistrationServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    /** Registers a taxi */
    @Override
    public void register(final Taxi taxi) {
        taxis.add(taxi);

        System.out.println("Taxi registered successfully: ID " + taxi.getId());
    }

    /** Removes a taxi by ID */
    @Override
    public void unregister(final int taxiId) {
        boolean removed = taxis.removeIf(taxi -> taxi.getId() == taxiId);

        if (removed) {
            System.out.println("Taxi with ID " + taxiId + " unregistered successfully.");
        } else {
            System.out.println("Taxi ID not found.");
        }
    }
}
