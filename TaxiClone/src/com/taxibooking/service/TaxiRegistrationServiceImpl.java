package com.taxibooking.service;

import com.taxibooking.model.Taxi;

import java.util.Objects;

/**
 * Singleton implementation of TaxiRegistrationService.
 * Delegates operations to TaxiServiceImpl.
 * No console output — only core business logic.
 */
public class TaxiRegistrationServiceImpl implements TaxiRegistrationService {

    private final TaxiServiceImpl taxiService;

    private TaxiRegistrationServiceImpl() {
        this.taxiService = TaxiServiceImpl.getInstance();
    }

    private static final class Instance {
        private static final TaxiRegistrationServiceImpl SERVICE = new TaxiRegistrationServiceImpl();
    }

    public static TaxiRegistrationServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    @Override
    public void register(final Taxi taxi) {

        if (Objects.nonNull(taxi)) {
            taxiService.addTaxi(taxi);
        }
    }

    @Override
    public boolean unregister(final int taxiId) {
        return taxiService.removeTaxi(taxiId);
    }


}
