package com.taxibooking.controller;

import com.taxibooking.service.FareService;
import com.taxibooking.service.FareServiceImpl;

/**
 * Controller that handles fare calculation requests.
 * Delegates computation to the FareService layer.
 */
public class FareController {

    private final FareService fareService = new FareServiceImpl();

    /**
     * Calculates the fare based on distance, AC preference, and seater type.
     */
    public double calculate(final double distance, final boolean ac, final int seater) {
        return fareService.calculate(distance, ac, seater);
    }
}
