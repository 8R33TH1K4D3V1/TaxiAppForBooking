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
        this.fareController = FareController.getInstance();
    }

    /** Runs the customer menu loop */
    public void run() {
        final Customer currentCustomer = login();
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

        if (taxis.isEmpty()) {
            System.out.println("No taxis registered yet.");
            return;
        }

        final StringBuilder taxisInfo = new StringBuilder("\n--- ALL TAXIS ---\n");

        for (final Taxi taxi : taxis) {
            final Driver driver = taxi.getDriver();
            final String driverName = Objects.nonNull(driver) ? driver.getName() : "Not assigned";
            final String driverPhone = Objects.nonNull(driver) ? driver.getPhoneNo() : "N/A";
            final double driverRating = Objects.nonNull(driver) ? driver.getRating() : 0.0;

            taxisInfo.append(String.format(
                    "Taxi ID: %d | Driver: %s | Phone: %s | Rating: %.1f | Seater: %d | AC: %s | Available: %s%n",
                    taxi.getId(),
                    driverName,
                    driverPhone,
                    driverRating,
                    taxi.getSeater(),
                    taxi.isAcAvailable() ? "Yes" : "No",
                    taxi.isAvailable() ? "Yes" : "No"
            ));
        }

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
            System.out.print("Enter number of seats (4, 6, or 7): ");
            seatCount = input.nextInt();

            if (seatCount == 4 || seatCount == 6 || seatCount == 7) {
                break;
            }
            System.out.println("Invalid input. Enter 4, 6, or 7.");
        }

        input.nextLine();
        System.out.print("Is AC available? (Yes/No): ");
        final String acInput = input.nextLine().trim().toLowerCase();
        final boolean acRequired = acInput.equals("yes");
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
                    Objects.nonNull(driver) ? driver.getName() : "Not assigned",
                    Objects.nonNull(driver) ? driver.getRating() : 0.0);
        });

        System.out.print("Enter Taxi ID to book: ");
        final int taxiId = input.nextInt();

        input.nextLine();
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
            final Driver driver = booking.getTaxi().getDriver();
            final String driverName = Objects.nonNull(driver) ? driver.getName() : "Not assigned";

            bookingsInfo.append(String.format(
                    "Booking ID: %d | Taxi ID: %d | Driver: %s | Fare: ₹%.2f | Status: %s%n",
                    booking.getId(),
                    booking.getTaxi().getId(),
                    driverName,
                    booking.getFare(),
                    booking.isActive() ? "Active" : "Completed"
            ));
        });

        System.out.print(bookingsInfo);
    }

    /** Ends an active booking */
    private void endBooking(final Customer customer) {
        System.out.print("Enter Booking ID to end: ");
        final int bookingId = input.nextInt();

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
                    Objects.nonNull(driver) ? driver.getName() : "Not assigned"
            ));
        });

        System.out.print(completedInfo);
        System.out.print("Enter Taxi ID to rate: ");
        final int taxiId = input.nextInt();
        final boolean validTaxi = completedBookings.stream()
                .anyMatch(booking -> booking.getTaxi().getId() == taxiId);

        if (!validTaxi) {
            System.out.println("Invalid Taxi ID. You can only rate completed bookings.");
            return;
        }

        System.out.print("Enter rating (0.0 - 5.0): ");
        final double rating = input.nextDouble();

        if (rating < 0.0 || rating > 5.0) {
            System.out.println("Invalid rating. Please enter a value between 0.0 and 5.0.");
            return;
        }

        bookingController.rate(taxiId, rating);
        System.out.println("Thank you for rating your driver!");
    }

    /** Calculates fare estimate */
    private void calculateFare() {
        System.out.print("Enter distance (km): ");
        final double distance = input.nextDouble();

        input.nextLine();
        System.out.print("AC required? (yes/no): ");
        final boolean acRequired = input.nextLine().equalsIgnoreCase("yes");

        System.out.print("Number of seats: ");
        final int seater = input.nextInt();

        final double fare = fareController.calculate(distance, acRequired, seater);
        System.out.printf("Estimated Fare: ₹%.2f%n", fare);
    }
}
