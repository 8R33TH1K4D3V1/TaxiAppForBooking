package com.taxibooking.view;

import com.taxibooking.controller.DriverController;
import com.taxibooking.controller.DriverRegistrationController;
import com.taxibooking.controller.TaxiController;
import com.taxibooking.controller.TaxiRegistrationController;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.Objects;
import java.util.Scanner;

/**
 * Admin menu for managing taxis and drivers.
 */
public class AdminMenu {

    private final Scanner input = new Scanner(System.in);

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

    /** Runs the main admin menu loop */
    public void run() {

        if (!adminLogin()) {
            System.out.println("Access denied. Returning to main menu.");
            return;
        }

        while (true) {
            displayMenu();

            final int choice = input.nextInt();

            input.nextLine();

            switch (choice) {
                case 1 -> addTaxi();
                case 2 -> addDriver();
                case 3 -> viewAllTaxis();
                case 4 -> viewUnAssignedTaxis();
                case 5 -> viewUnassignedDrivers();
                case 6 -> removeTaxi();
                case 7 -> removeDriver();
                case 8 -> assignDriverToTaxi();
                case 9 -> {
                    System.out.println("Exiting Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    /** Displays admin menu options */
    private void displayMenu() {
        System.out.println("""
                \n--- Admin Menu ---
                1. Add Taxi
                2. Add Driver
                3. View Taxis
                4. View UnAssigned Taxis
                5. View Unassigned Drivers
                6. Remove Taxi
                7. Remove Driver
                8. Assign Driver to Taxi
                9. Exit
                Choose option: """);
    }

    /** Verifies admin credentials */
    private boolean adminLogin() {
        System.out.print("Enter Admin username: ");
        final String username = input.nextLine().trim();

        System.out.print("Enter Admin password: ");
        final String password = input.nextLine().trim();

        return "admin".equals(username) && "admin123".equals(password);
    }

    /** Adds a new taxi */
    private void addTaxi() {
        System.out.print("Enter Taxi ID: ");
        final int id = input.nextInt();

        System.out.print("Enter No of Seats (4 / 6 / 7): ");
        int seats;

        while (true) {

            if (input.hasNextInt()) {
                seats = input.nextInt();

                if (seats == 4 || seats == 6 || seats == 7) {
                    break;
                }
                System.out.print("Invalid seat count. Please enter 4, 6, or 7: ");
            } else {
                System.out.print("Invalid input. Please enter a numeric value (4, 6, or 7): ");
                input.next();
            }
        }

        input.nextLine();
        System.out.print("Is AC available? (Yes/No): ");
        final boolean acAvailable = input.nextLine().trim().equalsIgnoreCase("yes");

        System.out.print("Is Taxi Available? (Yes/No): ");
        final boolean available = input.nextLine().trim().equalsIgnoreCase("yes");

        Taxi taxi = new Taxi();
        taxi.setId(id);
        taxi.setSeater(seats);
        taxi.setAcAvailable(acAvailable);
        taxi.setAvailable(available);

        taxiRegController.register(taxi);
    }

    /** Adds a new driver */
    private void addDriver() {
        System.out.print("Enter Driver ID: ");
        final int id = input.nextInt();

        input.nextLine();
        System.out.print("Enter Driver Name: ");
        final String name = input.nextLine();

        System.out.print("Enter Driver Phone: ");
        final String phone = input.nextLine();

        final double rating = 0.0;

        Driver driver = new Driver();
        driver.setId(id);
        driver.setName(name);
        driver.setPhoneNo(phone);
        driver.setRating(rating);

        driverRegController.register(driver);
    }

    /** Shows all taxis with driver, features, and availability details */
    private void viewAllTaxis() {
        System.out.println("\n--- All Taxis ---");
        StringBuilder taxiDetailsBuilder = new StringBuilder();
        var allTaxis = new java.util.LinkedHashSet<>(taxiController.get());
        allTaxis.addAll(taxiController.get());

        for (Taxi taxi : allTaxis) {
            Driver driver = taxi.getDriver();

            taxiDetailsBuilder.append("Taxi ID: ").append(taxi.getId())
                    .append(", Driver: ").append(Objects.nonNull(driver) ? driver.getName() : "Not Assigned")
                    .append(", Phone: ").append(Objects.nonNull(driver) ? driver.getPhoneNo() : "N/A")
                    .append(", Rating: ").append(Objects.nonNull(driver) ? driver.getRating() : "N/A")
                    .append(", Seater: ").append(taxi.getSeater())
                    .append(", AC: ").append(taxi.isAcAvailable() ? "Yes" : "No")
                    .append(", Available: ").append(taxi.isAvailable() ? "Yes" : "No")
                    .append("\n");
        }

        System.out.print(taxiDetailsBuilder);
    }

    /** Displays unassigned taxis */
    private void viewUnAssignedTaxis() {
        System.out.println("\n--- Unassigned Taxis ---");
        StringBuilder unassignedTaxiBuilder = new StringBuilder();

        for (final Taxi taxi : taxiController.get()) {
            if (taxi.getDriver() == null) {
                unassignedTaxiBuilder.append("Taxi ID: ").append(taxi.getId())
                        .append(", Seats: ").append(taxi.getSeater())
                        .append(", AC: ").append(taxi.isAcAvailable() ? "Yes" : "No")
                        .append(", Available: ").append(taxi.isAvailable() ? "Yes" : "No")
                        .append("\n");
            }
        }

        System.out.print(unassignedTaxiBuilder);
    }

    /** Displays drivers not assigned to any taxi */
    private void viewUnassignedDrivers() {
        System.out.println("\n--- Unassigned Drivers ---");
        StringBuilder unassignedDriverBuilder = new StringBuilder();

        for (final Driver driver : driverController.get()) {
            final boolean isAssigned = taxiController.get().stream()
                    .anyMatch(taxi -> {
                        final Driver assignedDriver = taxi.getDriver();
                        return Objects.nonNull(assignedDriver)
                                && assignedDriver.getId() == driver.getId();
                    });

            if (!isAssigned) {
                unassignedDriverBuilder.append("Driver ID: ").append(driver.getId())
                        .append(", Name: ").append(driver.getName())
                        .append(", Phone: ").append(driver.getPhoneNo())
                        .append(", Rating: ").append(driver.getRating())
                        .append("\n");
            }
        }

        System.out.print(unassignedDriverBuilder);
    }

    /** Removes a taxi by ID */
    private void removeTaxi() {
        System.out.print("Enter Taxi ID to remove: ");
        final int id = input.nextInt();

        input.nextLine();

        taxiRegController.unregister(id);
    }

    /** Removes a driver and unassigns from taxis */
    private void removeDriver() {
        System.out.print("Enter Driver ID to remove: ");
        final int id = input.nextInt();

        input.nextLine();

        driverRegController.unregister(id);

        taxiController.get().forEach(taxi -> {
            final Driver driver = taxi.getDriver();
            if (Objects.nonNull(driver) && driver.getId() == id) {
                taxi.setDriver(null);
            }
        });
    }

    /** Assigns a driver to a taxi */
    private void assignDriverToTaxi() {
        System.out.print("Enter Taxi ID: ");
        final int taxiId = input.nextInt();

        System.out.print("Enter Driver ID: ");
        final int driverId = input.nextInt();

        input.nextLine();

        final Taxi selectedTaxi = taxiController.get().stream()
                .filter(taxi -> taxi.getId() == taxiId)
                .findFirst()
                .orElse(null);

        final Driver selectedDriver = driverController.get().stream()
                .filter(driver -> driver.getId() == driverId)
                .findFirst()
                .orElse(null);

        if (selectedTaxi == null) {
            System.out.println("Taxi not found!");
            return;
        }

        if (selectedDriver == null) {
            System.out.println("Driver not found!");
            return;
        }

        selectedTaxi.setDriver(selectedDriver);

        System.out.println("Driver " + selectedDriver.getName() + " assigned to Taxi " + selectedTaxi.getId());
    }
}
