package com.taxibooking.service;

/**
 * Implementation of FareService.
 * Calculates taxi fares based on distance, AC preference, and seater type.
 */
public class FareServiceImpl implements FareService {

    private final double BASE_FARE = 50;
    private final double AC_RATE_PER_KM = 20;
    private final double NON_AC_RATE_PER_KM = 15;

    @Override
    public double calculate(final double distance, final boolean ac, final int seater) {
        final double rate = ac ? AC_RATE_PER_KM : NON_AC_RATE_PER_KM;

        // Adjust fare based on seater
        final double seaterMultiplier;
        switch (seater) {
            case 4 -> seaterMultiplier = 1.0;   // normal
            case 6 -> seaterMultiplier = 1.3;   // larger car
            case 8 -> seaterMultiplier = 1.6;   // SUV/van
            default -> seaterMultiplier = 1.0;  // default
        }

        return (BASE_FARE + (distance * rate)) * seaterMultiplier;
    }
}
