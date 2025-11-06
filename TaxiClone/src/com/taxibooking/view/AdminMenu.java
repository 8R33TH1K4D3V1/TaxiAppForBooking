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
import java.util.ArrayList;

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
                     final TaxiRegistrationController taxiRegistrationController,
                     final DriverRegistrationController driverRegistrationController,
                     final DriverController driverController) {

        this.taxiController = taxiController;
        this.taxiRegistrationController = taxiRegistrationController;
        this.driverRegistrationController = driverRegistrationController;
        this.driverController = driverController;
    }

    /** Runs the main admin menu loop */
    public void run() {

        if (!adminLogin()) {
            System.out.println("Access denied. Returning to main menu.");
            return;
        }

        while (true) {
            displayAdminMenu();
            final int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> addDriverWithTaxi();
                case 2 -> viewAllTaxis();
                case 3 -> removeTaxi();
                case 4 -> removeDriver();
                case 0 -> {
                    System.out.println("Exiting Admin Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    /** Displays admin menu options */
    private void displayAdminMenu() {
        System.out.println("""
                \n--- Admin Menu ---
                1. Register Taxi
                2. View All Taxis
                3. Remove Taxi
                4. Remove Driver
                0. Exit""");
    }

    /** Verifies admin credentials */
    private boolean adminLogin() {
        System.out.print("Enter Admin username: ");
        final String username = input.nextLine().trim();

        System.out.print("Enter Admin password: ");
        final String password = input.nextLine().trim();

        return Objects.equals(username, "admin") && Objects.equals(password, "admin123");
    }

    /** Adds a Driver first, then assigns a Taxi (Aggregation) */
    private void addDriverWithTaxi() {
        System.out.println("\n--- Register Driver and Assign Taxi ---");
        final Driver driver = new Driver();

        driver.setId(getIntInput("Enter Driver ID: "));
        System.out.print("Enter Driver Name: ");
        driver.setName(input.nextLine());
        String phoneNo;
        while (true) {
            System.out.print("Enter Driver Phone: ");
            phoneNo = input.nextLine().trim();
            final String phoneToCheck = phoneNo;

            boolean exists = taxiController.get().stream()
                    .map(Taxi::getDriver)
                    .filter(Objects::nonNull)
                    .anyMatch(existingDriver -> existingDriver.getPhoneNo().equals(phoneToCheck));

            if (exists) {
                System.out.println("Phone number already registered. Please enter a different one.");
            } else {
                break;
            }
        }

        driver.setPhoneNo(phoneNo);
        driver.setRating(0.0);
        final int driverAdded = driverRegistrationController.add(driver);

        if (driverAdded < 0) {
            System.out.println("Driver registration failed or already exists.");
            return;
        }

        System.out.println("Driver registered successfully!");
        final Taxi taxi = new Taxi();

        taxi.setId(getIntInput("Enter Taxi ID: "));
        int seatCount;

        while (true) {
            seatCount = getIntInput("Enter No of Seats (4 / 6 / 7): ");

            if (seatCount == 4 || seatCount == 6 || seatCount == 7) {
                break;
            }
            System.out.println("Invalid seat count. Please enter 4, 6, or 7.");
        }

        taxi.setSeater(seatCount);
        taxi.setAcAvailable(getYesNoInput("Is AC available? (Yes/No): "));
        taxi.setAvailable(true);
        taxi.setDriver(driver);
        final int addedId = taxiRegistrationController.add(taxi);

        System.out.println(addedId > 0
                ? "Driver and Taxi registered successfully."
                : "Failed to register Taxi. Please try again.");
    }

    /** Displays all taxis */
    private void viewAllTaxis() {
        System.out.println("\n--- All Taxis ---");
        final Collection<Taxi> allTaxis = taxiController.get();

        if (Objects.isNull(allTaxis) || allTaxis.isEmpty()) {
            System.out.println("No taxis available.");
            return;
        }

        allTaxis.stream()
                .filter(Objects::nonNull)
                .forEach(taxi -> {
                    final Driver driver = taxi.getDriver();

                    System.out.printf(
                            "Taxi ID: %d | Driver: %s | Phone: %s | Rating: %.1f | Seater: %d | AC: %s | Available: %s%n",
                            taxi.getId(),
                            Objects.isNull(driver) ? "Not Assigned" : driver.getName(),
                            Objects.isNull(driver) ? "N/A" : driver.getPhoneNo(),
                            Objects.isNull(driver) ? 0.0 : driver.getRating(),
                            taxi.getSeater(),
                            taxi.isAcAvailable() ? "Yes" : "No",
                            taxi.isAvailable() ? "Yes" : "No"
                    );
                });
    }

    /** Removes a taxi (and its driver — composition) */
    private void removeTaxi() {
        final int taxiId = getIntInput("Enter Taxi ID to remove: ");
        final boolean removed = taxiRegistrationController.remove(taxiId);

        if (removed) {
            System.out.printf("Taxi ID %d and its driver removed successfully.%n", taxiId);
        } else {
            System.out.printf("Taxi ID %d not found.%n", taxiId);
        }
    }
    private void removeDriver() {
        final int driverId = getIntInput("Enter Driver ID to remove: ");

        boolean driverFound = false;

        Collection<Taxi> allTaxis = new ArrayList<>(taxiController.get());

        for (final Taxi taxi : allTaxis) {

            if (taxi.getDriver() != null && taxi.getDriver().getId() == driverId) {
                taxiRegistrationController.remove(taxi.getId());
                driverFound = true;
            }
        }


        boolean removedFromRegistration = driverRegistrationController.remove(driverId);

        if (driverFound || removedFromRegistration) {
            System.out.printf("Driver ID %d and Taxi Details removed successfully.%n", driverId);
        } else {
            System.out.printf("Driver ID %d not found.%n", driverId);
        }
    }

    // 🔹 Helper Methods
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
