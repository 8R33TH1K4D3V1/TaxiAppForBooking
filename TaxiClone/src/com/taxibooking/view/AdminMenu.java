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
            final int choice = input.nextInt();

            input.nextLine();

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
                9. Exit
                Choose option:""");
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
        System.out.print("Enter Taxi ID: ");
        final int id = input.nextInt();

        System.out.print("Enter No of Seats (4 / 6 / 7): ");
        int seatCount;

        while (true) {

            if (input.hasNextInt()) {
                seatCount = input.nextInt();

                if (seatCount == 4 || seatCount == 6 || seatCount == 7) {
                    break;
                }
                System.out.print("Invalid seat count. Please enter 4, 6, or 7: ");
            } else {
                System.out.print("Invalid input. Please enter a number (4, 6, or 7): ");
                input.next();
            }
        }

        input.nextLine();
        System.out.print("Is AC available? (Yes/No): ");
        String acOption;

        do {
            acOption = input.nextLine().trim().toLowerCase();

            if (!Objects.equals(acOption, "yes") && !Objects.equals(acOption, "no")) {
                System.out.print("Invalid input. Please enter Yes or No: ");
            }
        } while (!Objects.equals(acOption, "yes") && !Objects.equals(acOption, "no"));

        final boolean isAcAvailable = Objects.equals(acOption, "yes");

        System.out.print("Is Taxi Available? (Yes/No): ");
        String availabilityOption;

        do {
            availabilityOption = input.nextLine().trim().toLowerCase();

            if (!Objects.equals(availabilityOption, "yes") && !Objects.equals(availabilityOption, "no")) {
                System.out.print("Invalid input. Please enter Yes or No: ");
            }
        } while (!Objects.equals(availabilityOption, "yes") && !Objects.equals(availabilityOption, "no"));

        final boolean isAvailable = Objects.equals(availabilityOption, "yes");
        final Taxi taxi = new Taxi();

        taxi.setId(id);
        taxi.setSeater(seatCount);
        taxi.setAcAvailable(isAcAvailable);
        taxi.setAvailable(isAvailable);
        taxiRegistrationController.register(taxi);
        System.out.printf("Taxi ID %d registered successfully.%n", id);
    }

    /** Adds a new driver */
    private void addDriver() {
        System.out.print("Enter Driver ID: ");
        final int id = input.nextInt();

        input.nextLine();
        System.out.print("Enter Driver Name: ");
        final String name = input.nextLine();

        System.out.print("Enter Driver Phone: ");
        final String phoneNumber = input.nextLine();
        final Driver driver = new Driver();

        driver.setId(id);
        driver.setName(name);
        driver.setPhoneNo(phoneNumber);
        driver.setRating(0.0);
        driverRegistrationController.register(driver);
        System.out.printf("Driver '%s' registered successfully.%n", name);
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
                    final StringBuilder taxiDetails = new StringBuilder();

                    taxiDetails.append("Taxi ID: ").append(taxi.getId())
                            .append(" | Driver: ").append(Objects.isNull(driver) ? "Not Assigned" : driver.getName())
                            .append(" | Phone: ").append(Objects.isNull(driver) ? "N/A" : driver.getPhoneNo())
                            .append(" | Rating: ").append(Objects.isNull(driver) ? "N/A" : driver.getRating())
                            .append(" | Seater: ").append(taxi.getSeater())
                            .append(" | AC: ").append(taxi.isAcAvailable() ? "Yes" : "No")
                            .append(" | Available: ").append(taxi.isAvailable() ? "Yes" : "No");

                    return taxiDetails.toString();
                })

                .forEach(System.out::println);
    }

    /** Displays unassigned taxis */
    private void viewUnassignedTaxis() {
        System.out.println("\n--- Unassigned Taxis ---");
        final StringBuilder unassignedTaxiInfo = new StringBuilder();
        final Collection<Taxi> taxis = taxiController.get();

        taxis.stream()
                .filter(taxi -> taxi.getDriver() == null)
                .forEach(taxi -> unassignedTaxiInfo.append("Taxi ID: ").append(taxi.getId())
                        .append(" | Seats: ").append(taxi.getSeater())
                        .append(" | AC: ").append(taxi.isAcAvailable() ? "Yes" : "No")
                        .append(" | Available: ").append(taxi.isAvailable() ? "Yes" : "No")
                        .append("\n"));

        System.out.print(unassignedTaxiInfo.isEmpty() ? "All taxis are assigned.\n" : unassignedTaxiInfo);
    }

    /** Displays unassigned drivers */
    private void viewUnassignedDrivers() {
        System.out.println("\n--- Unassigned Drivers ---");
        final StringBuilder unassignedDriverInfo = new StringBuilder();
        final Collection<Driver> drivers = driverController.get();
        final Collection<Taxi> taxis = taxiController.get();

        for (final Driver driver : drivers) {
            final boolean isAssigned = taxis.stream()
                    .anyMatch(taxi -> {
                        final Driver assignedDriver = taxi.getDriver();
                        return Objects.nonNull(assignedDriver) && assignedDriver.getId() == driver.getId();
                    });

            if (!isAssigned) {
                unassignedDriverInfo.append("Driver ID: ").append(driver.getId())
                        .append(" | Name: ").append(driver.getName())
                        .append(" | Phone: ").append(driver.getPhoneNo())
                        .append(" | Rating: ").append(driver.getRating())
                        .append("\n");
            }
        }

        System.out.println(unassignedDriverInfo.isEmpty()
                ? "No unassigned drivers available."
                : unassignedDriverInfo);
    }

    /** Removes a taxi by ID */
    private void removeTaxi() {
        System.out.print("Enter Taxi ID to remove: ");
        final int taxiId = input.nextInt();

        input.nextLine();
        final boolean removed = taxiRegistrationController.unregister(taxiId);

        System.out.printf("Taxi ID %d %s.%n", taxiId, removed ? "removed successfully" : "not found");
    }

    /** Removes a driver and unassigns from taxis */
    private void removeDriver() {
        System.out.print("Enter Driver ID to remove: ");
        final int driverId = input.nextInt();

        input.nextLine();
        final boolean removed = driverRegistrationController.unregister(driverId);

        if (removed) {
            final Collection<Taxi> taxis = taxiController.get();

            taxis.stream()
                    .filter(Objects::nonNull)
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
        System.out.print("Enter Taxi ID: ");
        final int taxiId = input.nextInt();

        System.out.print("Enter Driver ID: ");
        final int driverId = input.nextInt();

        input.nextLine();
        final Collection<Taxi> taxis = taxiController.get();
        final Collection<Driver> drivers = driverController.get();
        final Taxi selectedTaxi = taxis.stream()
                .filter(taxi -> taxi.getId() == taxiId)
                .findFirst()
                .orElse(null);

        final Driver selectedDriver = drivers.stream()
                .filter(driver -> driver.getId() == driverId)
                .findFirst()
                .orElse(null);

        if (Objects.isNull(selectedTaxi)) {
            System.out.println("Taxi not found!");
            return;
        }

        if (Objects.isNull(selectedDriver)) {
            System.out.println("Driver not found!");
            return;
        }

        selectedTaxi.setDriver(selectedDriver);
        System.out.printf("Driver '%s' assigned to Taxi ID %d.%n",
                selectedDriver.getName(), selectedTaxi.getId());
    }
}
