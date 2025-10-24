package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;

/**
 * Implementation of TaxiRegistrationService.
 * Handles registering and unregistering taxis.
 * Hidden from outside access — package-private.
 */
final class TaxiRegistrationServiceImpl implements TaxiRegistrationService {

    private final Collection<Taxi> taxis;

    TaxiRegistrationServiceImpl(final Collection<Taxi> taxis) {
        this.taxis = taxis;
    }

    @Override
    public void register(final Taxi taxi) {
        taxis.add(taxi);
        System.out.println("Taxi with ID " + taxi.getId() + " registered successfully.");
    }

    @Override
    public void unregister(final int id) {
        final Taxi toRemove = taxis.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (toRemove != null) {
            taxis.remove(toRemove);
            System.out.println("Taxi with ID " + id + " unregistered successfully.");
        } else {
            System.out.println("Taxi ID not found.");
        }
    }

    @Override
    public Collection<Taxi> get() {
        return taxis;
    }
}
