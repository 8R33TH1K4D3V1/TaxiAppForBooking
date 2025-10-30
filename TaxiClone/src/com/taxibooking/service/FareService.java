package com.taxibooking.service;

/**
 * Service interface for fare calculation.
 * Provides method to compute fare based on trip details.
 */
public interface FareService {

    /** Calculates fare using distance, AC requirement, and seater count. */
    double calculate(final double distance, final boolean ac, final int seater);

    /** Singleton access method. */
    static FareService getInstance() {
        return FareServiceImpl.getInstance();
    }
}
