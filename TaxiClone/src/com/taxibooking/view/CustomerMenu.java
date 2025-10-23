package com.taxibooking.view;

import com.taxibooking.controller.TaxiController;
import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.FareController;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Customer;
import com.taxibooking.model.Booking;
import com.taxibooking.service.TaxiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Customer menu to interact with taxi booking system.
 * Allows viewing taxis, booking, ending rides, rating drivers, and fare estimation.
 */
public class CustomerMenu {
    private final Scanner input = new Scanner(System.in);
    private final TaxiController taxiController;
    private final BookingController bookingController;
    private final FareController fareController;

    private Customer currentCustomer;
    private final List<Customer> customerList = new ArrayList<>();

    public CustomerMenu(final TaxiService taxiService, final BookingController bookingController) {
        this.taxiController = new TaxiController(taxiService);
        this.bookingController = bookingController;
        this.fareController = new FareController();
    }

    /**
     * Main customer menu loop
     */
    public void run() {
        login(); // Customer login or registration

        int choice;
        do {
            menu();
            choice = choice();
            switch (choice) {
                case 1 -> viewTaxis();
                case 2 -> bookTaxi();
                case 3 -> viewBookingHistory();
                case 4 -> endBooking();
                case 5 -> rateDriver();
                case 6 -> calculateFare();
                case 0 -> System.out.println("Back to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    /**
     * Customer login or registration
     */
    private void login() {
        System.out.print("Enter your Customer ID: ");
        final int id = input.nextInt();
        input.nextLine();

        currentCustomer = customerList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (currentCustomer == null) {
            System.out.print("Enter your Name: ");
            final String name = input.nextLine();
            currentCustomer = new Customer(id, name);
            customerList.add(currentCustomer);
        }

        System.out.println("Welcome, " + currentCustomer.getName() + "!");
    }

    /**
     * Display menu options
     */
    private void menu() {
        System.out.println("\n--- CUSTOMER MENU ---");
        System.out.println("1. View Taxis");
        System.out.println("2. Book Taxi");
        System.out.println("3. View Booking History");
        System.out.println("4. End Booking");
        System.out.println("5. Rate Driver");
        System.out.println("6. Fare Calculation");
        System.out.println("0. Back");
    }

    private int choice() {
        System.out.print("Enter your choice: ");
        return input.nextInt();
    }

    /**
     * Show all taxis (assigned or unassigned)
     */
    private void viewTaxis() {
        final List<Taxi> taxis = taxiController.get();
        System.out.println("\n--- ALL TAXIS ---");
        for (final Taxi t : taxis) {
            System.out.println("Taxi ID: " + t.getId()
                    + ", Driver: " + (t.getDriver() != null ? t.getDriver().getName() : "Not assigned")
                    + ", Phone: " + (t.getDriver() != null ? t.getDriver().getPhoneNo() : "N/A")
                    + ", Rating: " + (t.getDriver() != null ? t.getDriver().getRating() : 0.0)
                    + ", Seater: " + t.getSeater()
                    + ", AC: " + (t.isAcAvailable() ? "Yes" : "No")
                    + ", Available: " + (t.isAvailable() ? "Yes" : "No"));
        }
    }

    /**
     * Show only taxis with assigned drivers
     */
    private void viewAssignedTaxis() {
        final List<Taxi> taxis = taxiController.get();
        System.out.println("\n--- TAXIS WITH ASSIGNED DRIVERS ---");
        for (final Taxi t : taxis) {
            if (t.getDriver() != null) {
                System.out.println("Taxi ID: " + t.getId()
                        + ", Driver: " + t.getDriver().getName()
                        + ", Phone: " + t.getDriver().getPhoneNo()
                        + ", Rating: " + t.getDriver().getRating()
                        + ", Seater: " + t.getSeater()
                        + ", AC: " + (t.isAcAvailable() ? "Yes" : "No")
                        + ", Available: " + (t.isAvailable() ? "Yes" : "No"));
            }
        }
    }

    /**
     * Book a taxi matching customer requirements
     */
    private void bookTaxi() {
        final List<Taxi> taxis = taxiController.get();
        System.out.print("Enter number of seats: ");
        final int seater = input.nextInt();
        input.nextLine();
        System.out.print("AC required? (yes/no): ");
        final boolean ac = input.nextLine().equalsIgnoreCase("yes");

        final List<Taxi> filtered = taxis.stream()
                .filter(t -> t.isAvailable() && t.getSeater() == seater && (!ac || t.isAcAvailable()))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("No taxis match your requirement.");
            return;
        }

        System.out.println("Available taxis:");
        for (final Taxi t : filtered) {
            System.out.println("Taxi ID: " + t.getId()
                    + ", Driver: " + t.getDriver().getName()
                    + ", Phone: " + t.getDriver().getPhoneNo()
                    + ", Rating: " + t.getDriver().getRating()
                    + ", Availability: " + (t.isAvailable() ? "Yes" : "No"));
        }

        System.out.print("Enter Taxi ID to book: ");
        final int taxiId = input.nextInt();
        input.nextLine();
        final Taxi selected = filtered.stream().filter(t -> t.getId() == taxiId).findFirst().orElse(null);
        if (selected == null) {
            System.out.println("Invalid Taxi ID.");
            return;
        }

        System.out.print("Pickup location: ");
        final String pickup = input.nextLine();
        System.out.print("Drop location: ");
        final String drop = input.nextLine();
        System.out.print("Distance (km): ");
        final double distance = input.nextDouble();

        final double fare = fareController.calculate(distance, selected.isAcAvailable(), selected.getSeater());

        bookingController.book(selected, currentCustomer, pickup, drop, fare);
        System.out.println("Taxi booked successfully! Fare: ₹" + fare);
    }

    /**
     * View customer's booking history
     */
    private void viewBookingHistory() {
        final List<Booking> bookings = bookingController.get(currentCustomer.getId());
        System.out.println("\n--- YOUR BOOKING HISTORY ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (final Booking b : bookings) {
            System.out.println("Booking ID: " + b.getId()
                    + ", Taxi ID: " + b.getTaxi().getId()
                    + ", Driver: " + b.getTaxi().getDriver().getName()
                    + ", Fare: ₹" + b.getFare()
                    + ", Status: " + (b.isActive() ? "Active" : "Completed"));
        }
    }

    /**
     * End an active booking
     */
    private void endBooking() {
        System.out.print("Enter Booking ID to end: ");
        final int bookingId = input.nextInt();
        bookingController.end(bookingId);
        System.out.println("Booking ended.");
    }

    /**
     * Rate driver for completed booking
     */
    private void rateDriver() {
        final List<Booking> bookings = bookingController.get(currentCustomer.getId());
        final List<Booking> completed = bookings.stream().filter(b -> !b.isActive()).toList();

        if (completed.isEmpty()) {
            System.out.println("No completed bookings to rate.");
            return;
        }

        System.out.println("\n--- COMPLETED BOOKINGS ---");
        completed.forEach(b -> System.out.println(
                "Booking ID: " + b.getId() +
                        ", Taxi ID: " + b.getTaxi().getId() +
                        ", Driver: " + b.getTaxi().getDriver().getName()
        ));

        System.out.print("Enter Taxi ID of the driver to rate: ");
        final int taxiId = input.nextInt();

        final boolean canRate = completed.stream()
                .anyMatch(b -> b.getTaxi().getId() == taxiId);

        if (!canRate) {
            System.out.println("You can only rate drivers from your completed bookings.");
            return;
        }

        System.out.print("Enter rating (0.0 - 5.0): ");
        final double rating = input.nextDouble();
        // Update driver rating
        completed.stream()
                .filter(b -> b.getTaxi().getId() == taxiId)
                .forEach(b -> b.getTaxi().getDriver().setRating(rating));

        System.out.println("Thank you for rating your driver!");
    }

    /**
     * Estimate fare without booking
     */
    private void calculateFare() {
        System.out.print("Enter distance (km): ");
        final double distance = input.nextDouble();
        input.nextLine();
        System.out.print("AC required? (yes/no): ");
        final boolean ac = input.nextLine().equalsIgnoreCase("yes");
        System.out.print("Number of seats: ");
        final int seater = input.nextInt();

        final double fare = fareController.calculate(distance, ac, seater);
        System.out.println("Estimated Fare: ₹" + fare);
    }
}
