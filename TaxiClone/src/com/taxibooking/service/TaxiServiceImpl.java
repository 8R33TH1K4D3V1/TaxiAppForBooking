package com.taxibooking.service;

import com.taxibooking.model.Taxi;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Singleton implementation of TaxiService.
 * Uses Map for O(1) access and update operations.
 * Ensures efficient taxi management.
 */
public class TaxiServiceImpl implements TaxiService {

    private final Map<Integer, Taxi> taxisById = new HashMap<>();

    private TaxiServiceImpl() {}

    private static final class Instance {
        private static final TaxiServiceImpl SERVICE = new TaxiServiceImpl();
    }

    public static TaxiServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    @Override
    public Collection<Taxi> get() {
        return Collections.unmodifiableCollection(taxisById.values());
    }

    @Override
    public Taxi get(final int id) {
        return taxisById.get(id);
    }

    public void addTaxi(final Taxi taxi) {
        if (Objects.nonNull(taxi)) {
            taxisById.put(taxi.getId(), taxi);
        }
    }

    public boolean removeTaxi(final int id) {
        return Objects.nonNull(taxisById.remove(id));
    }

    @Override
    public void registerDemoTaxis() {
        final Taxi t1 = new Taxi();

        t1.setId(1);
        t1.setSeater(4);
        t1.setAcAvailable(true);
        t1.setAvailable(true);
        t1.getDriver().setId(101);
        t1.getDriver().setName("Ramesh");
        t1.getDriver().setPhoneNo("7373771722");
        t1.getDriver().setRating(4.5);
        taxisById.put(t1.getId(), t1);

        final Taxi t2 = new Taxi();
        t2.setId(2);
        t2.setSeater(6);
        t2.setAcAvailable(false);
        t2.setAvailable(true);
        t2.getDriver().setId(102);
        t2.getDriver().setName("Suresh");
        t2.getDriver().setPhoneNo("9876543210");
        t2.getDriver().setRating(4.7);
        taxisById.put(t2.getId(), t2);

        final Taxi t3 = new Taxi();
        t3.setId(3);
        t3.setSeater(4);
        t3.setAcAvailable(true);
        t3.setAvailable(true);
        t3.getDriver().setId(103);
        t3.getDriver().setName("Anil");
        t3.getDriver().setPhoneNo("9123456789");
        t3.getDriver().setRating(4.6);
        taxisById.put(t3.getId(), t3);

        final Taxi t4 = new Taxi();
        t4.setId(4);
        t4.setSeater(7);
        t4.setAcAvailable(false);
        t4.setAvailable(true);
        t4.getDriver().setId(104);
        t4.getDriver().setName("Karthik");
        t4.getDriver().setPhoneNo("9988776655");
        t4.getDriver().setRating(4.4);
        taxisById.put(t4.getId(), t4);

        final Taxi t5 = new Taxi();
        t5.setId(5);
        t5.setSeater(4);
        t5.setAcAvailable(true);
        t5.setAvailable(true);
        t5.getDriver().setId(105);
        t5.getDriver().setName("Priya");
        t5.getDriver().setPhoneNo("9012345678");
        t5.getDriver().setRating(4.8);
        taxisById.put(t5.getId(), t5);

    }
}
