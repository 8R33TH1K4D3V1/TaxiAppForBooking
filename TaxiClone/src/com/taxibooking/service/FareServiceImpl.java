package com.taxibooking.service;

/**
 * Singleton implementation of FareService.
 * Calculates taxi fares based on distance, AC preference, and seater type.
 */
public class FareServiceImpl implements FareService {

    private static final double BASE_FARE = 50;
    private static final double AC_RATE_PER_KM = 20;
    private static final double NON_AC_RATE_PER_KM = 15;

    /** Private constructor */
    private FareServiceImpl() {}

    /** Internal static class for Singleton instance */
    private static final class Instance {
        private static final FareServiceImpl SERVICE = new FareServiceImpl();
    }

    /** Returns singleton instance */
    public static FareServiceImpl getInstance() {
        return Instance.SERVICE;
    }

    @Override
    public double get(final double distance, final boolean ac, final int seater) {
        final double rate = ac ? AC_RATE_PER_KM : NON_AC_RATE_PER_KM;

        final double seaterMultiplier = switch (seater) {
            case 6 -> 1.3;
            case 7 -> 1.6;
            default -> 1.0;
        };

        return (BASE_FARE + (distance * rate)) * seaterMultiplier;
    }
}
