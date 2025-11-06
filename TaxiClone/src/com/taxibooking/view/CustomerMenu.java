package com.taxibooking.view;

import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.FareController;
import com.taxibooking.controller.TaxiController;
import com.taxibooking.model.Booking;
import com.taxibooking.model.Customer;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.Scanner;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles all customer interactions in the taxi booking system.
 */
public class CustomerMenu {

    private final TaxiController taxiController;
    private final BookingController bookingController;
    private final FareController fareController;

    private final Collection<Customer> customerList = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);

    public CustomerMenu(final BookingController bookingController) {
        this.taxiController = TaxiController.getInstance();
        this.bookingController = bookingController;
        this.fareController = FareController.getInstance();
    }

    /** Runs the customer menu loop */
    public void run() {
        final Customer currentCustomer = login();

        int choice;

        do {
            menu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> viewTaxis();
                case 2 -> bookTaxi(currentCustomer);
                case 3 -> viewBookingHistory(currentCustomer);
                case 4 -> endBooking(currentCustomer);
                case 5 -> rateDriver(currentCustomer);
                case 6 -> calculateFare();
                case 0 -> System.out.println("Back to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    /** Handles customer login or registration */
    private Customer login() {

        while (true) {
            final int customerId = readInt("Enter your Customer ID: ");
            final Customer existingCustomer = customerList.stream()
                    .filter(c -> c.getId() == customerId)
                    .findFirst()
                    .orElse(null);

            if (existingCustomer != null) {
                System.out.println("\nWelcome back, " + existingCustomer.getName() + "!\n");
                return existingCustomer;
            }

            System.out.print("Enter your Name: ");
            final String customerName = input.nextLine().trim();
            final Customer newCustomer = new Customer();

            newCustomer.setId(customerId);
            newCustomer.setName(customerName);
            customerList.add(newCustomer);

            System.out.println("\nWelcome, " + newCustomer.getName() + "!\n");
            return newCustomer;
        }
    }

    /** Displays customer menu options */
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

    /** Displays all taxis with assigned drivers */
    private void viewTaxis() {
        final Collection<Taxi> taxis = taxiController.get();

        if (taxis.isEmpty()) {
            System.out.println("No taxis registered yet.");
            return;
        }

        final StringBuilder taxisInfo = new StringBuilder("\n--- AVAILABLE TAXIS WITH DRIVERS ---\n");

        taxis.stream()
                .filter(taxi -> Objects.nonNull(taxi.getDriver()))
                .forEach(taxi -> {
                    final Driver driver = taxi.getDriver();

                    taxisInfo.append(String.format(
                            "Taxi ID: %d | Driver: %s | Phone: %s | Rating: %.1f | Seater: %d | AC: %s | Available: %s%n",
                            taxi.getId(),
                            driver.getName(),
                            driver.getPhoneNo(),
                            driver.getRating(),
                            taxi.getSeater(),
                            taxi.isAcAvailable() ? "Yes" : "No",
                            taxi.isAvailable() ? "Yes" : "No"
                    ));
                });

        System.out.print(taxisInfo);
    }

    /** Books a taxi for the given customer */
    private void bookTaxi(final Customer customer) {
        final Collection<Taxi> taxis = taxiController.get();

        if (taxis.isEmpty()) {
            System.out.println("No taxis available to book.");
            return;
        }

        int seatCount;

        while (true) {
            seatCount = readInt("Enter number of seats (4, 6, or 7): ");

            if (seatCount == 4 || seatCount == 6 || seatCount == 7) {
                break;
            }

            System.out.println("Invalid input. Enter 4, 6, or 7.");
        }

        final boolean acRequired = readYesNo("Is AC available? (Yes/No): ");
        final int finalSeatCount = seatCount;
        final Collection<Taxi> availableTaxis = taxis.stream()
                .filter(taxi -> taxi.isAvailable()
                        && taxi.getSeater() == finalSeatCount
                        && (acRequired ? taxi.isAcAvailable() : !taxi.isAcAvailable()))
                .toList();

        if (availableTaxis.isEmpty()) {
            System.out.println("No taxis match your requirement.");
            return;
        }

        System.out.println("\nAvailable taxis:");
        availableTaxis.forEach(taxi -> {
            final Driver driver = taxi.getDriver();

            System.out.printf("Taxi ID: %d | Driver: %s | Rating: %.1f%n",
                    taxi.getId(),
                    Objects.isNull(driver) ? "Not assigned" : driver.getName(),
                    Objects.isNull(driver) ? 0.0 : driver.getRating());
        });

        final int taxiId = readInt("Enter Taxi ID to book: ");
        final Taxi selectedTaxi = availableTaxis.stream()
                .filter(taxi -> taxi.getId() == taxiId)
                .findFirst()
                .orElse(null);

        if (Objects.isNull(selectedTaxi)) {
            System.out.println("Invalid Taxi ID.");
            return;
        }

        System.out.print("Pickup location: ");
        final String pickupLocation = input.nextLine();

        System.out.print("Drop location: ");
        final String dropLocation = input.nextLine();
        final double distance = readDouble("Distance (km): ");
        final double fare = fareController.get(distance, selectedTaxi.isAcAvailable(), selectedTaxi.getSeater());
        final Booking booking = new Booking();

        booking.setTaxi(selectedTaxi);
        booking.setCustomer(customer);
        booking.setPickupLocation(pickupLocation);
        booking.setDropLocation(dropLocation);
        booking.setFare(fare);
        booking.setActive(true);
        bookingController.book(booking);
        System.out.printf("Taxi booked successfully! Fare: ₹%.2f%n", fare);
    }

    /** Displays customer's past bookings */
    private void viewBookingHistory(final Customer customer) {
        final Collection<Booking> bookings = bookingController.get(customer.getId());

        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found.");
            return;
        }

        final StringBuilder bookingsInfo = new StringBuilder("\n--- YOUR BOOKING HISTORY ---\n");

        bookings.forEach(booking -> {
            final Taxi taxi = booking.getTaxi();
            final Driver driver = taxi.getDriver();
            final String driverName = Objects.isNull(driver) ? "Not assigned" : driver.getName();

            bookingsInfo.append(String.format(
                    "Booking ID: %d | Taxi ID: %d | Driver: %s | Fare: ₹%.2f | Status: %s%n",
                    booking.getId(),
                    taxi.getId(),
                    driverName,
                    booking.getFare(),
                    booking.isActive() ? "Active" : "Completed"
            ));
        });

        System.out.print(bookingsInfo);
    }

    /** Ends an active booking */
    private void endBooking(final Customer customer) {
        final int bookingId = readInt("Enter Booking ID to end: ");

        bookingController.end(bookingId);
        System.out.println("Booking ended successfully.");
    }

    /** Allows customer to rate a driver */
    private void rateDriver(final Customer customer) {
        final Collection<Booking> bookings = bookingController.get(customer.getId());
        final Collection<Booking> completedBookings = bookings.stream()
                .filter(booking -> !booking.isActive())
                .toList();

        if (completedBookings.isEmpty()) {
            System.out.println("No completed bookings available to rate.");
            return;
        }

        final StringBuilder completedInfo = new StringBuilder("\n--- COMPLETED BOOKINGS ---\n");

        completedBookings.forEach(booking -> {
            final Driver driver = booking.getTaxi().getDriver();

            completedInfo.append(String.format(
                    "Booking ID: %d | Taxi ID: %d | Driver: %s%n",
                    booking.getId(),
                    booking.getTaxi().getId(),
                    Objects.isNull(driver) ? "Not assigned" : driver.getName()
            ));
        });

        System.out.print(completedInfo);
        final int taxiId = readInt("Enter Taxi ID to rate: ");
        final boolean validTaxi = completedBookings.stream()
                .anyMatch(booking -> booking.getTaxi().getId() == taxiId);

        if (!validTaxi) {
            System.out.println("Invalid Taxi ID. You can only rate completed bookings.");
            return;
        }

        double rating;

        while (true) {
            rating = readDouble("Enter rating (0.0 - 5.0): ");

            if (rating >= 0.0 && rating <= 5.0){
                break;
            }
            System.out.println("Invalid rating. Please enter between 0.0 and 5.0.");
        }

        bookingController.rate(customer.getId(), taxiId, rating);
        System.out.println("Thank you for rating your driver!");
    }

    /** Calculates fare estimate */
    private void calculateFare() {
        final double distance = readDouble("Enter distance (km): ");
        final boolean acRequired = readYesNo("AC required? (Yes/No): ");

        int seater;

        while (true) {
            seater = readInt("Enter number of seats (4, 6, or 7): ");

            if (seater == 4 || seater == 6 || seater == 7) {
                break;
            }

            System.out.println("Invalid input. Enter 4, 6, or 7.");
        }
        final double fare = fareController.get(distance, acRequired, seater);

        System.out.printf("Estimated Fare: ₹%.2f%n", fare);
    }

    /* ======== SAFE INPUT HELPERS ======== */

    private int readInt(final String message) {

        while (true) {
            System.out.print(message);

            if (input.hasNextInt()) {
                final int value = input.nextInt();

                input.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
    }

    private double readDouble(final String message) {

        while (true) {
            System.out.print(message);

            if (input.hasNextDouble()) {
                final double value = input.nextDouble();

                input.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.nextLine();
            }
        }
    }

    private boolean readYesNo(final String message) {

        while (true) {
            System.out.print(message);
            final String response = input.nextLine().trim().toLowerCase();

            if (Objects.equals(response, "yes")) {
                return true;
            }

            if (Objects.equals(response, "no")){
                return false;
            }

            System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
        }
    }
}
