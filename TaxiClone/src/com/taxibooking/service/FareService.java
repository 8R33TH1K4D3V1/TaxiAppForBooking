package com.taxibooking.service;

/**
 * Service interface for fare calculation.
 */
public interface FareService {

    /**
     * Calculates the fare based on distance, AC preference, and seater type.
     *
     * @param distance Distance of the trip
     * @param ac       Whether AC is required
     * @param seater   Number of seats in the taxi
     * @return Calculated fare
     */
    double calculate(final double distance, final boolean ac, final int seater);
}
