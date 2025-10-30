package com.taxibooking.controller;

import com.taxibooking.service.FareService;

/**
 * Controller for fare calculation.
 * Delegates calculation logic to FareService.
 */
public class FareController {

    private final FareService fareService;

    /** Private constructor */
    private FareController() {
        this.fareService = FareService.getInstance();
    }

    /** Internal static class for Singleton instance */
    private static final class Instance {
        private static final FareController CONTROLLER = new FareController();
    }

    /** Returns singleton instance */
    public static FareController getInstance() {
        return Instance.CONTROLLER;
    }

    /** Calculate fare based on distance, AC requirement, and number of seats. */
    public double calculate(final double distance, final boolean ac, final int seater) {
        return fareService.calculate(distance, ac, seater);
    }
}
