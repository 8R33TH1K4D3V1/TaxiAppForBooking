package com.taxibooking.service;

/**
 * Implementation of FareService.
 * Calculates taxi fares based on distance, AC preference, and seater type.
 */
class FareServiceImpl implements FareService { // made package-private

    private static final double BASE_FARE = 50;
    private static final double AC_RATE_PER_KM = 20;
    private static final double NON_AC_RATE_PER_KM = 15;

    @Override
    public double calculate(double distance, boolean ac, int seater) {
        double rate = ac ? AC_RATE_PER_KM : NON_AC_RATE_PER_KM;
        double seaterMultiplier = switch (seater) {
            case 6 -> 1.3;
            case 8 -> 1.6;
            default -> 1.0;
        };
        return (BASE_FARE + (distance * rate)) * seaterMultiplier;
    }
}
