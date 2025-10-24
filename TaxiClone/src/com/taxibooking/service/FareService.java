package com.taxibooking.service;

/**
 * Service interface for fare calculation.
 * Provides method to compute fare based on trip details.
 */
public interface FareService {

    /** Calculates fare using distance, AC requirement, and seater count. */
    double calculate(double distance, boolean ac, int seater);

    /** Factory method to obtain a FareService instance. */
    static FareService getInstance() {
        return new FareServiceImpl();
    }
}
