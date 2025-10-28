package com.taxibooking.view;

import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.FareController;
import com.taxibooking.controller.TaxiController;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Customer;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
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

        if (Objects.isNull(customer)) {
            System.out.print("Enter your Name: ");
            final String name = input.nextLine();

            customer = new Customer();
            customer.setId(id);
            customer.setName(name);

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
        final StringBuilder taxiDetailsBuilder = new StringBuilder();

        for (final Taxi taxi : taxis) {
            final Driver driver = taxi.getDriver();
            final String driverName = Objects.nonNull(driver) ? driver.getName() : "Not assigned";
            final String driverPhone = Objects.nonNull(driver) ? driver.getPhoneNo() : "N/A";
            final double driverRating = Objects.nonNull(driver) ? driver.getRating() : 0.0;

            taxiDetailsBuilder.append("Taxi ID: ").append(taxi.getId())
                    .append(", Driver: ").append(driverName)
                    .append(", Phone: ").append(driverPhone)
                    .append(", Rating: ").append(driverRating)
                    .append(", Seater: ").append(taxi.getSeater())
                    .append(", AC: ").append(taxi.isAcAvailable() ? "Yes" : "No")
                    .append(", Available: ").append(taxi.isAvailable() ? "Yes" : "No")
                    .append("\n");
        }

        System.out.print(taxiDetailsBuilder);
    }


    /** Books a taxi for the given customer */
    private void bookTaxi(final Customer customer) {
        final Collection<Taxi> taxis = taxiController.get();
        int seatInput;

        while (true) {
            System.out.print("Enter number of seats (4, 6, or 7): ");
            seatInput = input.nextInt();

            if (seatInput == 4 || seatInput == 6 || seatInput == 7) {
                break;
            }

            System.out.println("Invalid choice. Please enter 4, 6, or 7 only.");
        }

        final int seater = seatInput;

        input.nextLine();
        System.out.print("AC required? (yes/no): ");
        final boolean acRequired = input.nextLine().trim().equalsIgnoreCase("yes");

        final Collection<Taxi> filteredTaxis = taxis.stream()
                .filter(taxi -> taxi.isAvailable()
                        && taxi.getSeater() == seater
                        && (acRequired ? taxi.isAcAvailable() : !taxi.isAcAvailable()))
                .toList();

        if (filteredTaxis.isEmpty()) {
            System.out.println("No taxis match your requirement.");
            return;
        }

        System.out.println("Available taxis:");
        final StringBuilder availableTaxiBuilder = new StringBuilder();

        for (final Taxi taxi : filteredTaxis) {
            final Driver driver = taxi.getDriver();
            final String driverName = Objects.nonNull(driver) ? driver.getName() : "Not assigned";
            final String driverPhone = Objects.nonNull(driver) ? driver.getPhoneNo() : "N/A";
            final double driverRating = Objects.nonNull(driver) ? driver.getRating() : 0.0;

            availableTaxiBuilder.append("Taxi ID: ").append(taxi.getId())
                    .append(", Driver: ").append(driverName)
                    .append(", Phone: ").append(driverPhone)
                    .append(", Rating: ").append(driverRating)
                    .append(", Availability: ").append(taxi.isAvailable() ? "Yes" : "No")
                    .append("\n");
        }

        System.out.print(availableTaxiBuilder);

        System.out.print("Enter Taxi ID to book: ");
        final int taxiId = input.nextInt();

        input.nextLine();

        final Taxi selectedTaxi = filteredTaxis.stream()
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

        System.out.print("Distance (km): ");
        final double distance = input.nextDouble();

        final double fare = fareController.calculate(distance, selectedTaxi.isAcAvailable(), selectedTaxi.getSeater());

        final Booking booking = new Booking();
        booking.setTaxi(selectedTaxi);
        booking.setCustomer(customer);
        booking.setPickupLocation(pickupLocation);
        booking.setDropLocation(dropLocation);
        booking.setFare(fare);
        booking.setActive(true);

        bookingController.book(booking);

        System.out.println("Taxi booked successfully! Fare: ₹" + fare);
    }


    /** Displays customer's past bookings */
    private void viewBookingHistory(final Customer customer) {
        final Collection<Booking> bookings = bookingController.get(customer.getId());

        System.out.println("\n--- YOUR BOOKING HISTORY ---");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        final StringBuilder bookingHistoryBuilder = new StringBuilder();

        for (final Booking booking : bookings) {
            final Driver driver = booking.getTaxi().getDriver();
            final String driverName = Objects.nonNull(driver) ? driver.getName() : "Not assigned";

            bookingHistoryBuilder.append("Booking ID: ").append(booking.getId())
                    .append(", Taxi ID: ").append(booking.getTaxi().getId())
                    .append(", Driver: ").append(driverName)
                    .append(", Fare: ₹").append(booking.getFare())
                    .append(", Status: ").append(booking.isActive() ? "Active" : "Completed")
                    .append("\n");
        }

        System.out.print(bookingHistoryBuilder);
    }


    /** Ends an active booking */
    private void endBooking(final Customer customer) {
        System.out.print("Enter Booking ID to end: ");
        final int bookingId = input.nextInt();

        bookingController.end(bookingId);

        System.out.println("Booking ended.");
    }


    /** Allows customer to rate a driver */
    private void rateDriver(final Customer customer) {
        final Collection<Booking> bookings = bookingController.get(customer.getId());

        final Collection<Booking> completedBookings = bookings.stream()
                .filter(booking -> !booking.isActive())
                .toList();

        if (completedBookings.isEmpty()) {
            System.out.println("No completed bookings to rate.");
            return;
        }

        System.out.println("\n--- COMPLETED BOOKINGS ---");
        final StringBuilder completedBookingsBuilder = new StringBuilder();

        completedBookings.forEach(booking -> {
            final Driver driver = booking.getTaxi().getDriver();
            final String driverName = Objects.nonNull(driver) ? driver.getName() : "Not assigned";

            completedBookingsBuilder.append("Booking ID: ").append(booking.getId())
                    .append(", Taxi ID: ").append(booking.getTaxi().getId())
                    .append(", Driver: ").append(driverName)
                    .append("\n");
        });

        System.out.print(completedBookingsBuilder);

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
