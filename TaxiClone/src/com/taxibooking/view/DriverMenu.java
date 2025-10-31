package com.taxibooking.view;

import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.DriverController;
import com.taxibooking.controller.TaxiController;

import com.taxibooking.model.Booking;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;


/**
 * View class for managing driver-related actions.
 * Provides login, profile editing, and booking viewing features.
 */
public class DriverMenu {

    private final Scanner input = new Scanner(System.in);
    private final TaxiController taxiController;
    private final BookingController bookingController;
    private final DriverController driverController;

    /**
     * Constructs DriverMenu with required controllers.
     */
    public DriverMenu(final BookingController bookingController,
                      final DriverController driverController) {

        this.taxiController = TaxiController.getInstance();
        this.bookingController = bookingController;
        this.driverController = driverController;
    }

    /**
     * Runs the driver menu loop.
     */
    public void run() {
        final Driver currentDriver = login();

        if (Objects.isNull(currentDriver)) {
            return;
        }

        int choice;

        do {
            menu();
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> viewCurrentBookings(currentDriver);
                case 2 -> editProfile(currentDriver);
                case 3 -> viewBookingHistory(currentDriver);
                case 0 -> System.out.println("Back to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    /**
     * Displays driver menu options.
     */
    private void menu() {
        System.out.println("\n--- DRIVER MENU ---");
        System.out.println("1. Current Booking");
        System.out.println("2. Edit Profile");
        System.out.println("3. Booking History");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
    }

    /**
     * Handles driver login using driver ID.
     */
    private Driver login() {
        System.out.print("Enter your Driver ID: ");
        final int driverId = input.nextInt();

        input.nextLine();
        final Driver driver = get(driverId);

        if (Objects.isNull(driver)) {
            System.out.println("Driver ID not found. Returning to main menu...");
            return null;
        }

        System.out.println("Welcome, " + driver.getName() + "!");
        return driver;
    }

    /**
     * Retrieves driver by ID from registered taxis.
     */
    private Driver get(final int id) {
        return taxiController.get().stream()
                .filter(Objects::nonNull)
                .map(Taxi::getDriver)
                .filter(driver -> Objects.nonNull(driver) && driver.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Allows driver to edit profile details.
     */
    private void editProfile(final Driver driver) {
        System.out.println("\n--- EDIT PROFILE ---");
        System.out.println("Current Name: " + driver.getName());
        System.out.print("Enter new name (or press Enter to keep current): ");
        final String name = input.nextLine();

        if (!name.isEmpty()) {
            driver.setName(name);
        }

        System.out.println("Current Phone: " + driver.getPhoneNo());
        System.out.print("Enter new phone number (or press Enter to keep current): ");
        final String phone = input.nextLine();

        if (!phone.isEmpty()) {
            driver.setPhoneNo(phone);
        }

        driverController.update(driver);
        System.out.println("Profile updated successfully!");
    }

    /**
     * Displays all active bookings assigned to this driver.
     */
    private void viewCurrentBookings(final Driver driver) {
        System.out.println("\n--- YOUR CURRENT BOOKINGS ---");
        final Collection<Booking> bookings = bookingController.get().stream()
                .filter(Objects::nonNull)
                .filter(booking -> Objects.nonNull(booking.getTaxi())
                        && Objects.nonNull(booking.getTaxi().getDriver())
                        && booking.getTaxi().getDriver().getId() == driver.getId()
                        && booking.isActive())
                .toList();

        if (bookings.isEmpty()) {
            System.out.println("No current bookings assigned to you.");
            return;
        }

        final StringBuilder bookingInfo = new StringBuilder();

        bookings.forEach(booking -> bookingInfo.append("Booking ID: ").append(booking.getId())
                .append(", Customer: ").append(booking.getCustomer().getName())
                .append(", Pickup: ").append(booking.getPickupLocation())
                .append(", Drop: ").append(booking.getDropLocation())
                .append(", Fare: ₹").append(booking.getFare())
                .append(", Status: Active")
                .append("\n"));

        System.out.print(bookingInfo);
    }


    /**
     * Displays booking history (active and completed).
     */
    private void viewBookingHistory(final Driver driver) {
        System.out.println("\n--- YOUR BOOKING HISTORY ---");

        final Collection<Booking> driverBookings = bookingController.get().stream()
                .filter(booking -> Objects.nonNull(booking)
                        && Objects.nonNull(booking.getTaxi())
                        && Objects.nonNull(booking.getTaxi().getDriver())
                        && booking.getTaxi().getDriver().getId() == driver.getId())
                .toList();

        if (driverBookings.isEmpty()) {
            System.out.println("No bookings found for you.");
            return;
        }

        final StringBuilder bookingDetails = new StringBuilder();

        for (Booking booking : driverBookings) {
            bookingDetails.append("Booking ID: ").append(booking.getId())
                    .append(", Customer: ").append(booking.getCustomer().getName())
                    .append(", Pickup: ").append(booking.getPickupLocation())
                    .append(", Drop: ").append(booking.getDropLocation())
                    .append(", Fare: ₹").append(booking.getFare())
                    .append(", Status: ").append(booking.isActive() ? "Active" : "Completed")
                    .append("\n");
        }

        System.out.println(bookingDetails);
    }

}
