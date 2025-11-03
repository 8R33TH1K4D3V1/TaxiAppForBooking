package com.taxibooking.view;

import com.taxibooking.controller.DriverController;
import com.taxibooking.controller.DriverRegistrationController;
import com.taxibooking.controller.TaxiController;
import com.taxibooking.controller.TaxiRegistrationController;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

/**
 * Admin menu for managing taxis and drivers.
 * Handles user interaction and delegates business logic to controllers.
 */
public class AdminMenu {

    private final Scanner input = new Scanner(System.in);
    private final TaxiController taxiController;
    private final DriverController driverController;
    private final TaxiRegistrationController taxiRegistrationController;
    private final DriverRegistrationController driverRegistrationController;

    public AdminMenu(final TaxiController taxiController,
                     final DriverController driverController,
                     final TaxiRegistrationController taxiRegistrationController,
                     final DriverRegistrationController driverRegistrationController) {

        this.taxiController = taxiController;
        this.driverController = driverController;
        this.taxiRegistrationController = taxiRegistrationController;
        this.driverRegistrationController = driverRegistrationController;
    }

    /** Runs the main admin menu loop */
    public void run() {

        if (!adminLogin()) {
            System.out.println("Access denied. Returning to main menu.");
            return;
        }

        while (true) {
            displayMenu();
            final int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> addTaxi();
                case 2 -> addDriver();
                case 3 -> viewAllTaxis();
                case 4 -> viewUnassignedTaxis();
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
                4. View Unassigned Taxis
                5. View Unassigned Drivers
                6. Remove Taxi
                7. Remove Driver
                8. Assign Driver to Taxi
                9. Exit""");
    }

    /** Verifies admin credentials */
    private boolean adminLogin() {
        System.out.print("Enter Admin username: ");
        final String username = input.nextLine().trim();

        System.out.print("Enter Admin password: ");
        final String password = input.nextLine().trim();

        return Objects.equals(username, "admin") && Objects.equals(password, "admin123");
    }

    /** Adds a new taxi */
    private void addTaxi() {
        final int id = getIntInput("Enter Taxi ID: ");

        int seatCount;

        while (true) {
            seatCount = getIntInput("Enter No of Seats (4 / 6 / 7): ");

            if (seatCount == 4 || seatCount == 6 || seatCount == 7){
                break;
            }
            System.out.println("Invalid seat count. Please enter 4, 6, or 7.");
        }

        final boolean isAcAvailable = getYesNoInput("Is AC available? (Yes/No): ");
        final boolean isAvailable = getYesNoInput("Is Taxi Available? (Yes/No): ");
        final Taxi taxi = new Taxi();

        taxi.setId(id);
        taxi.setSeater(seatCount);
        taxi.setAcAvailable(isAcAvailable);
        taxi.setAvailable(isAvailable);
        final int addedId = taxiRegistrationController.add(taxi);

        System.out.println(addedId > 0
                ? "Taxi ID " + addedId + " registered successfully."
                : "Failed to register taxi. Please try again.");
    }

    /** Adds a new driver */
    private void addDriver() {
        final int id = getIntInput("Enter Driver ID: ");

        System.out.print("Enter Driver Name: ");
        final String name = input.nextLine();

        System.out.print("Enter Driver Phone: ");
        final String phoneNumber = input.nextLine();
        final Driver driver = new Driver();

        driver.setId(id);
        driver.setName(name);
        driver.setPhoneNo(phoneNumber);
        driver.setRating(0.0);
        final int addedId = driverRegistrationController.add(driver);

        System.out.println(addedId > 0
                ? "Driver ID " + addedId + " registered successfully."
                : "Failed to register driver. Please try again.");
    }

    /** Displays all taxis */
    private void viewAllTaxis() {
        System.out.println("\n--- All Taxis ---");
        final Collection<Taxi> allTaxis = taxiController.get();

        if (allTaxis.isEmpty()) {
            System.out.println("No taxis available.");
            return;
        }

        allTaxis.stream()
                .filter(Objects::nonNull)
                .map(taxi -> {
                    final Driver driver = taxi.getDriver();

                    return String.format(
                            "Taxi ID: %d | Driver: %s | Phone: %s | Rating: %s | Seater: %d | AC: %s | Available: %s",
                            taxi.getId(),
                            Objects.isNull(driver) ? "Not Assigned" : driver.getName(),
                            Objects.isNull(driver) ? "N/A" : driver.getPhoneNo(),
                            Objects.isNull(driver) ? "N/A" : driver.getRating(),
                            taxi.getSeater(),
                            taxi.isAcAvailable() ? "Yes" : "No",
                            taxi.isAvailable() ? "Yes" : "No"
                    );
                })
                .forEach(System.out::println);
    }

    /** Displays unassigned taxis */
    private void viewUnassignedTaxis() {
        System.out.println("\n--- Unassigned Taxis ---");
        final Collection<Taxi> taxis = taxiController.get();
        final StringBuilder info = new StringBuilder();

        taxis.stream()
                .filter(taxi -> Objects.nonNull(taxi) && taxi.getDriver() == null)
                .forEach(taxi -> info.append(String.format(
                        "Taxi ID: %d | Seats: %d | AC: %s | Available: %s%n",
                        taxi.getId(),
                        taxi.getSeater(),
                        taxi.isAcAvailable() ? "Yes" : "No",
                        taxi.isAvailable() ? "Yes" : "No")));

        System.out.print(info.isEmpty() ? "All taxis are assigned.\n" : info);
    }

    /** Displays unassigned drivers */
    private void viewUnassignedDrivers() {
        System.out.println("\n--- Unassigned Drivers ---");
        final Collection<Driver> drivers = driverController.get();
        final Collection<Taxi> taxis = taxiController.get();
        final StringBuilder info = new StringBuilder();

        for (final Driver driver : drivers) {
            final boolean assigned = taxis.stream()
                    .anyMatch(taxi -> {
                        final Driver assignedDriver = taxi.getDriver();

                        return Objects.nonNull(assignedDriver)
                                && assignedDriver.getId() == driver.getId();
                    });

            if (!assigned) {
                info.append(String.format(
                        "Driver ID: %d | Name: %s | Phone: %s | Rating: %.1f%n",
                        driver.getId(), driver.getName(), driver.getPhoneNo(), driver.getRating()));
            }
        }

        System.out.print(info.isEmpty() ? "No unassigned drivers available.\n" : info);
    }

    /** Removes a taxi */
    private void removeTaxi() {
        final int taxiId = getIntInput("Enter Taxi ID to remove: ");
        final boolean removed = taxiRegistrationController.remove(taxiId);

        System.out.printf("Taxi ID %d %s.%n", taxiId, removed ? "removed successfully" : "not found");
    }

    /** Removes a driver */
    private void removeDriver() {
        final int driverId = getIntInput("Enter Driver ID to remove: ");
        final boolean removed = driverRegistrationController.remove(driverId);

        if (removed) {
            final Collection<Taxi> taxis = taxiController.get();

            taxis.stream()
                    .filter(taxi -> {
                        final Driver driver = taxi.getDriver();

                        return Objects.nonNull(driver) && driver.getId() == driverId;
                    })
                    .forEach(taxi -> taxi.setDriver(null));
            System.out.printf("Driver ID %d removed successfully.%n", driverId);
        } else {
            System.out.printf("Driver ID %d not found.%n", driverId);
        }
    }

    /** Assigns a driver to a taxi */
    private void assignDriverToTaxi() {
        final int taxiId = getIntInput("Enter Taxi ID: ");
        final int driverId = getIntInput("Enter Driver ID: ");
        final Collection<Taxi> taxis = taxiController.get();
        final Collection<Driver> drivers = driverController.get();

        final Taxi taxi = taxis.stream()
                .filter(t -> t.getId() == taxiId)
                .findFirst()
                .orElse(null);

        final Driver driver = drivers.stream()
                .filter(d -> d.getId() == driverId)
                .findFirst()
                .orElse(null);

        if (Objects.isNull(taxi)) {
            System.out.println("Taxi not found!");
            return;
        }

        if (Objects.isNull(driver)) {
            System.out.println("Driver not found!");
            return;
        }

        taxi.setDriver(driver);
        System.out.printf("Driver '%s' assigned to Taxi ID %d.%n",
                driver.getName(), taxi.getId());
    }

    //Helper Methods

    private int getIntInput(final String prompt) {

        while (true) {
            System.out.print(prompt);

            if (input.hasNextInt()) {
                final int value = input.nextInt();

                input.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a valid number.");
            input.nextLine();
        }
    }

    private boolean getYesNoInput(final String prompt) {

        while (true) {
            System.out.print(prompt);
            final String response = input.nextLine().trim().toLowerCase();

            if (Objects.equals(response, "yes")){
                return true;
            }

            if (Objects.equals(response, "no")){
                return false;
            }

            System.out.println("Invalid input. Please enter Yes or No.");
        }
    }
}
