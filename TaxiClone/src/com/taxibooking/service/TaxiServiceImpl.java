package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import com.taxibooking.model.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of TaxiService.
 * Manages taxi list and registers demo taxis.
 */
public class TaxiServiceImpl implements TaxiService {

    private final List<Taxi> taxis = new ArrayList<>();

    @Override
    public List<Taxi> get() {
        return taxis;
    }

    @Override
    public void registerDemoTaxis() {
        taxis.add(new Taxi(1, new Driver(101, "7373771722", 4.5, "Ramesh"), 4, true, true));
        taxis.add(new Taxi(2, new Driver(102, "8484998446", 4.7, "Nick"), 6, false, true));
        taxis.add(new Taxi(3, new Driver(103, "9442371722", 4.2, "Mike"), 4, true, false));
        taxis.add(new Taxi(4, new Driver(104, "9443701100", 4.8, "Danny"), 4, false, true));
        taxis.add(new Taxi(5, new Driver(105, "9988776655", 4.3, "John"), 6, true, true));
        taxis.add(new Taxi(6, new Driver(106, "6787678790", 4.6, "Ram"), 4, false, true));
    }

    @Override
    public void get(final Taxi taxi) {
        taxis.add(taxi);
    }
}
