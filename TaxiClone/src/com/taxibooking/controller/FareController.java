package com.taxibooking.controller;

import com.taxibooking.service.FareService;

/**
 * Controller for fare calculation.
 * Delegates calculation logic to FareService.
 */
public class FareController {

    private final FareService fareService = FareService.getInstance();

    /**
     * Calculate fare based on distance, AC requirement, and number of seats.
     */
    public double calculate(double distance, boolean ac, int seater) {
        return fareService.calculate(distance, ac, seater);
    }
}
