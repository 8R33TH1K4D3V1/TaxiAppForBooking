package com.taxibooking.view;

import com.taxibooking.controller.*;
import com.taxibooking.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

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
        this.fareController = new FareController();
    }


    /** Runs the customer menu loop */
    public void run() {
        Customer currentCustomer = login();
        int choice;

        do {
            menu();
            choice = choice();

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
        System.out.print("Enter your Customer ID: ");
        final int id = input.nextInt();
        input.nextLine();

        Customer customer = customerList.stream()
                .filter(existingCustomer -> existingCustomer.getId() == id)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            System.out.print("Enter your Name: ");
            final String name = input.nextLine();
            customer = new Customer(id, name);
            customerList.add(customer);
        }

        System.out.println("Welcome, " + customer.getName() + "!");
        return customer;
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


    /** Reads user's menu choice */
    private int choice() {
        System.out.print("Enter your choice: ");
        return input.nextInt();
    }


    /** Displays all taxis and driver details */
    private void viewTaxis() {
        final Collection<Taxi> taxis = taxiController.get();
        System.out.println("\n--- ALL TAXIS ---");

        for (final Taxi taxi : taxis) {
            final var driver = taxi.getDriver();
            final String driverName = driver != null ? driver.getName() : "Not assigned";
            final String driverPhone = driver != null ? driver.getPhoneNo() : "N/A";
            final double driverRating = driver != null ? driver.getRating() : 0.0;

            System.out.println("Taxi ID: " + taxi.getId()
                    + ", Driver: " + driverName
                    + ", Phone: " + driverPhone
                    + ", Rating: " + driverRating
                    + ", Seater: " + taxi.getSeater()
                    + ", AC: " + (taxi.isAcAvailable() ? "Yes" : "No")
                    + ", Available: " + (taxi.isAvailable() ? "Yes" : "No"));
        }
    }


    /** Books a taxi for the given customer */
    private void bookTaxi(Customer customer) {
        final Collection<Taxi> taxis = taxiController.get();

        System.out.print("Enter number of seats: ");
        final int seater = input.nextInt();
        input.nextLine();

        System.out.print("AC required? (yes/no): ");
        final boolean ac = input.nextLine().equalsIgnoreCase("yes");

        final Collection<Taxi> filteredTaxis = taxis.stream()
                .filter(taxi -> taxi.isAvailable()
                        && taxi.getSeater() == seater
                        && (!ac || taxi.isAcAvailable()))
                .toList();

        if (filteredTaxis.isEmpty()) {
            System.out.println("No taxis match your requirement.");
            return;
        }

        System.out.println("Available taxis:");
        for (final Taxi taxi : filteredTaxis) {
            final var driver = taxi.getDriver();
            final String driverName = driver != null ? driver.getName() : "Not assigned";
            final String driverPhone = driver != null ? driver.getPhoneNo() : "N/A";
            final double driverRating = driver != null ? driver.getRating() : 0.0;

            System.out.println("Taxi ID: " + taxi.getId()
                    + ", Driver: " + driverName
                    + ", Phone: " + driverPhone
                    + ", Rating: " + driverRating
                    + ", Availability: " + (taxi.isAvailable() ? "Yes" : "No"));
        }

        System.out.print("Enter Taxi ID to book: ");
        final int taxiId = input.nextInt();
        input.nextLine();

        final Taxi selectedTaxi = filteredTaxis.stream()
                .filter(taxi -> taxi.getId() == taxiId)
                .findFirst()
                .orElse(null);

        if (selectedTaxi == null) {
            System.out.println("Invalid Taxi ID.");
            return;
        }

        System.out.print("Pickup location: ");
        final String pickupLocation = input.nextLine();

        System.out.print("Drop location: ");
        final String dropLocation = input.nextLine();

        System.out.print("Distance (km): ");
        final double distance = input.nextDouble();

        final double fare = fareController.calculate(distance, selectedTaxi.isAcAvailable(), selectedTaxi.getSeater());
        bookingController.book(selectedTaxi, customer, pickupLocation, dropLocation, fare);

        System.out.println("Taxi booked successfully! Fare: ₹" + fare);
    }


    /** Displays customer's past bookings */
    private void viewBookingHistory(Customer customer) {
        final Collection<Booking> bookings = bookingController.get(customer.getId());
        System.out.println("\n--- YOUR BOOKING HISTORY ---");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (final Booking booking : bookings) {
            final var driver = booking.getTaxi().getDriver();
            final String driverName = driver != null ? driver.getName() : "Not assigned";

            System.out.println("Booking ID: " + booking.getId()
                    + ", Taxi ID: " + booking.getTaxi().getId()
                    + ", Driver: " + driverName
                    + ", Fare: ₹" + booking.getFare()
                    + ", Status: " + (booking.isActive() ? "Active" : "Completed"));
        }
    }


    /** Ends an active booking */
    private void endBooking(Customer customer) {
        System.out.print("Enter Booking ID to end: ");
        final int bookingId = input.nextInt();
        bookingController.end(bookingId);
        System.out.println("Booking ended.");
    }


    /** Allows customer to rate a driver */
    private void rateDriver(Customer customer) {
        final Collection<Booking> bookings = bookingController.get(customer.getId());
        final Collection<Booking> completedBookings = bookings.stream()
                .filter(booking -> !booking.isActive())
                .toList();

        if (completedBookings.isEmpty()) {
            System.out.println("No completed bookings to rate.");
            return;
        }

        System.out.println("\n--- COMPLETED BOOKINGS ---");
        completedBookings.forEach(booking -> {
            final var driver = booking.getTaxi().getDriver();
            final String driverName = driver != null ? driver.getName() : "Not assigned";
            System.out.println("Booking ID: " + booking.getId()
                    + ", Taxi ID: " + booking.getTaxi().getId()
                    + ", Driver: " + driverName);
        });

        System.out.print("Enter Taxi ID of the driver to rate: ");
        final int taxiId = input.nextInt();

        final boolean canRate = completedBookings.stream()
                .anyMatch(booking -> booking.getTaxi().getId() == taxiId);

        if (!canRate) {
            System.out.println("You can only rate drivers from your completed bookings.");
            return;
        }

        System.out.print("Enter rating (0.0 - 5.0): ");
        final double rating = input.nextDouble();

        bookingController.rate(taxiId, rating);
        System.out.println("Thank you for rating your driver!");
    }


    /** Calculates and displays fare estimate */
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
