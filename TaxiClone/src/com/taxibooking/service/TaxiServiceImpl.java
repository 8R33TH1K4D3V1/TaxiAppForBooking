package com.taxibooking.service;

import com.taxibooking.model.Taxi;
import com.taxibooking.model.Driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Singleton implementation of TaxiService.
 * Restricts direct modification of the taxi list.
 */
class TaxiServiceImpl implements TaxiService {

    private final Collection<Taxi> taxis = new ArrayList<>();
    private static TaxiServiceImpl instance;

    private TaxiServiceImpl() { }

    /** Singleton instance accessor */
    public static TaxiServiceImpl getInstance() {

        if (Objects.isNull(instance)) {
            instance = new TaxiServiceImpl();
        }
        return instance;
    }


    @Override
    public Collection<Taxi> get() {
        return taxis;
    }


    /** Get taxi by ID */
    @Override
    public Taxi get(final int id) {

        for (Taxi taxi : taxis) {

            if (taxi.getId() == id) {
                return taxi;
            }
        }

        return null;
    }

    /** Keep registerDemoTaxis unchanged */
    @Override
    public void registerDemoTaxis() {

        Driver d1 = new Driver();
        d1.setId(101);
        d1.setName("Ramesh");
        d1.setPhoneNo("7373771722");
        d1.setRating(4.5);

        Taxi t1 = new Taxi();
        t1.setId(1);
        t1.setDriver(d1);
        t1.setSeater(4);
        t1.setAcAvailable(true);
        t1.setAvailable(true);
        taxis.add(t1);

        Driver d2 = new Driver();
        d2.setId(102);
        d2.setName("Nick");
        d2.setPhoneNo("8484998446");
        d2.setRating(4.7);

        Taxi t2 = new Taxi();
        t2.setId(2);
        t2.setDriver(d2);
        t2.setSeater(6);
        t2.setAcAvailable(true);
        t2.setAvailable(true);
        taxis.add(t2);

        Driver d3 = new Driver();
        d3.setId(103);
        d3.setName("Mike");
        d3.setPhoneNo("9442371722");
        d3.setRating(4.2);

        Taxi t3 = new Taxi();
        t3.setId(3);
        t3.setDriver(d3);
        t3.setSeater(6);
        t3.setAcAvailable(false);
        t3.setAvailable(true);
        taxis.add(t3);

        Driver d4 = new Driver();
        d4.setId(104);
        d4.setName("Danny");
        d4.setPhoneNo("9443701100");
        d4.setRating(4.8);

        Taxi t4 = new Taxi();
        t4.setId(4);
        t4.setDriver(d4);
        t4.setSeater(4);
        t4.setAcAvailable(false);
        t4.setAvailable(true);
        taxis.add(t4);
    }
}
