package com.taxibooking.view;

import com.taxibooking.controller.*;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.Scanner;

/**
 * Admin menu for managing taxis and drivers.
 * Supports adding, viewing, removing, and assigning drivers to taxis.
 */
public class AdminMenu {

    private final Scanner sc = new Scanner(System.in);
    private final TaxiController taxiController;
    private final DriverController driverController;
    private final TaxiRegistrationController taxiRegController;
    private final DriverRegistrationController driverRegController;

    public AdminMenu(final TaxiController taxiController,
                     final DriverController driverController,
                     final TaxiRegistrationController taxiRegController,
                     final DriverRegistrationController driverRegController) {
        this.taxiController = taxiController;
        this.driverController = driverController;
        this.taxiRegController = taxiRegController;
        this.driverRegController = driverRegController;
    }

    /**
     * Runs the admin menu loop.
     */
    public void run() {
        if (!adminLogin()) { // check admin credentials
            System.out.println("Access denied. Returning to main menu.");
            return;
        }

        while (true) {
            // Display menu options
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Taxi");
            System.out.println("2. Add Driver");
            System.out.println("3. View Taxis");
            System.out.println("4. View Assigned Taxis");
            System.out.println("5. View Unassigned Drivers");
            System.out.println("6. Remove Taxi");
            System.out.println("7. Remove Driver");
            System.out.println("8. Assign Driver to Taxi");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");

            final int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTaxi();
                case 2 -> addDriver();
                case 3 -> viewAllTaxis();
                case 4 -> viewAssignedTaxis();
                case 5 -> viewUnassignedDrivers();
                case 6 -> removeTaxi();
                case 7 -> removeDriver();
                case 8 -> assignDriverToTaxi();
                case 9 -> { // exit admin menu
                    System.out.println("Exiting Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    /**
     * Admin login check
     */
    private boolean adminLogin() {
        System.out.print("Enter Admin username: ");
        final String username = sc.nextLine().trim();
        System.out.print("Enter Admin password: ");
        final String password = sc.nextLine().trim();
        return "admin".equals(username) && "admin123".equals(password);
    }

    /**
     * Add new taxi
     */
    private void addTaxi() {
        System.out.print("Enter Taxi ID: ");
        final int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter No of Seats: ");
        final int seats = sc.nextInt(); sc.nextLine();
        System.out.print("Is AC available? (Yes/No): ");
        final boolean ac = "yes".equalsIgnoreCase(sc.nextLine().trim());
        System.out.print("Is Taxi Available? (Yes/No): ");
        final boolean available = "yes".equalsIgnoreCase(sc.nextLine().trim());

        final Taxi taxi = new Taxi(id, null, seats, ac, available);
        taxiRegController.register(taxi);
    }

    /**
     * Add new driver
     */
    private void addDriver() {
        System.out.print("Enter Driver ID: ");
        final int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Driver Name: ");
        final String name = sc.nextLine();
        System.out.print("Enter Driver Phone: ");
        final String phone = sc.nextLine();
        final double rating = 0.0;

        final Driver driver = new Driver(id, phone, rating, name);
        driverRegController.register(driver);
    }

    /**
     * View all taxis
     */
    private void viewAllTaxis() {
        System.out.println("\n--- All Taxis ---");
        for (final Taxi taxi : taxiController.get()) {
            final String driverName = taxi.getDriver() != null ? taxi.getDriver().getName() : "Not assigned";
            System.out.println("Taxi ID: " + taxi.getId() +
                    ", Seats: " + taxi.getSeater() +
                    ", AC: " + (taxi.isAcAvailable() ? "Yes" : "No") +
                    ", Available: " + (taxi.isAvailable() ? "Yes" : "No") +
                    ", Driver: " + driverName);
        }
    }

    /**
     * View only taxis with assigned drivers
     */
    private void viewAssignedTaxis() {
        System.out.println("\n--- Assigned Taxis ---");
        for (final Taxi taxi : taxiController.get()) {
            final Driver driver = taxi.getDriver();
            if (driver != null) {
                System.out.println("Taxi ID: " + taxi.getId() +
                        ", Seats: " + taxi.getSeater() +
                        ", Driver ID: " + driver.getId() +
                        ", Driver Name: " + driver.getName());
            }
        }
    }

    /**
     * View drivers not assigned to any taxi
     */
    private void viewUnassignedDrivers() {
        System.out.println("\n--- Unassigned Drivers ---");
        for (final Driver driver : driverController.get()) {
            final boolean assigned = taxiController.get().stream()
                    .anyMatch(t -> t.getDriver() != null && t.getDriver().getId() == driver.getId());
            if (!assigned) {
                System.out.println("Driver ID: " + driver.getId() +
                        ", Name: " + driver.getName() +
                        ", Phone: " + driver.getPhoneNo() +
                        ", Rating: " + driver.getRating());
            }
        }
    }

    /**
     * Remove taxi
     */
    private void removeTaxi() {
        System.out.print("Enter Taxi ID to remove: ");
        final int id = sc.nextInt(); sc.nextLine();
        taxiRegController.unregister(id);
    }

    /**
     * Remove driver and unassign from any taxi
     */
    private void removeDriver() {
        System.out.print("Enter Driver ID to remove: ");
        final int id = sc.nextInt(); sc.nextLine();
        driverRegController.unregister(id);

        for (final Taxi taxi : taxiController.get()) {
            if (taxi.getDriver() != null && taxi.getDriver().getId() == id) {
                taxi.setDriver(null);
            }
        }
    }

    /**
     * Assign a driver to a taxi
     */
    private void assignDriverToTaxi() {
        System.out.print("Enter Taxi ID: ");
        final int taxiId = sc.nextInt();
        System.out.print("Enter Driver ID: ");
        final int driverId = sc.nextInt(); sc.nextLine();

        final Taxi taxi = taxiController.get(taxiId);
        final Driver driver = driverController.get(driverId);

        if (taxi == null) {
            System.out.println("Taxi not found!");
            return;
        }
        if (driver == null) {
            System.out.println("Driver not found!");
            return;
        }

        taxi.setDriver(driver);
        System.out.println("Driver " + driver.getName() + " assigned to Taxi " + taxi.getId());
    }
}
