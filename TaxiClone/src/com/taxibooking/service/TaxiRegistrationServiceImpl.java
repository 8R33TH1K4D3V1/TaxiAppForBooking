package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import java.util.Collection;
import java.util.Objects;

/**
 * Package-private implementation of TaxiRegistrationService.
 * Hidden from external access.
 */
class TaxiRegistrationServiceImpl implements TaxiRegistrationService {

    private static TaxiRegistrationServiceImpl instance;
    private final Collection<Taxi> taxis;

    /**
     * Private constructor — only accessible via getInstance()
     */
    private TaxiRegistrationServiceImpl() {
        this.taxis = TaxiService.getInstance().get();
    }

    /**
     * Singleton access — called from interface factory method
     */
    static TaxiRegistrationServiceImpl getInstance() {

        if (Objects.isNull(instance)) {
            instance = new TaxiRegistrationServiceImpl();
        }
        return instance;
    }


    @Override
    public void register(final Taxi taxi) {
        taxis.add(taxi);

        System.out.println("Taxi with ID " + taxi.getId() + " registered successfully.");
    }

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
