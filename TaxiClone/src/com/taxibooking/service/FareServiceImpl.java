package com.taxibooking.service;

/**
 * Implementation of FareService.
 * Calculates taxi fares based on distance, AC preference, and seater type.
 */
class FareServiceImpl implements FareService {

    private static final double BASE_FARE = 50;
    private static final double AC_RATE_PER_KM = 20;
    private static final double NON_AC_RATE_PER_KM = 15;

    @Override
    public double calculate(final double distance, final boolean ac, final int seater) {
        final double rate = ac ? AC_RATE_PER_KM : NON_AC_RATE_PER_KM;
        final double seaterMultiplier = switch (seater) {
            case 6 -> 1.3;
            case 8 -> 1.6;
            default -> 1.0;
        };
        return (BASE_FARE + (distance * rate)) * seaterMultiplier;
    }
}
