package com.taxibooking.view;

import com.taxibooking.controller.DriverController;
import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.TaxiController;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Booking;
import com.taxibooking.service.TaxiService;

import java.util.List;
import java.util.Scanner;

/**
 * Menu for drivers to view current bookings, edit profile, and see booking history.
 */
public class DriverMenu {

    private final Scanner input = new Scanner(System.in);
    private final TaxiController taxiController;
    private final BookingController bookingController;
    private final DriverController driverController;
    private Driver currentDriver;

    /**
     * Constructs the DriverMenu with required controllers.
     */
    public DriverMenu(final TaxiService taxiService,
                      final BookingController bookingController,
                      final DriverController driverController) {
        this.taxiController = new TaxiController(taxiService);
        this.bookingController = bookingController;
        this.driverController = driverController;
    }

    /**
     * Main driver menu loop: login, display options, and handle actions.
     */
    public void run() {
        login(); // Prompt for driver login

        int choice;
        do {
            menu();
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> viewCurrentBookings();
                case 2 -> editProfile();
                case 3 -> viewBookingHistory();
                case 0 -> System.out.println("Back to main menu...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    /**
     * Display the driver menu options.
     */
    private void menu() {
        System.out.println("\n--- DRIVER MENU ---");
        System.out.println("1. Current Booking");
        System.out.println("2. Edit Profile");
        System.out.println("3. Booking History");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
    }

    // ---------------- Login ----------------
    private void login() {
        System.out.print("Enter your Driver ID: ");
        final int driverId = input.nextInt();
        input.nextLine();
        currentDriver = findDriverById(driverId);

        if (currentDriver == null) {
            System.out.println("Driver ID not found. Exiting.");
            System.exit(0);
        }

        System.out.println("Welcome, " + currentDriver.getName() + "!");
    }

    /**
     * Find a driver by their ID by checking all registered taxis.
     */
    private Driver findDriverById(final int id) {
        final List<Taxi> taxis = taxiController.get();
        for (final Taxi t : taxis) {
            if (t.getDriver() != null && t.getDriver().getId() == id) {
                return t.getDriver();
            }
        }
        return null;
    }

    // ---------------- Edit Profile ----------------
    private void editProfile() {
        System.out.println("\n--- EDIT PROFILE ---");

        System.out.println("Current Name: " + currentDriver.getName());
        System.out.print("Enter new name (or press Enter to keep current): ");
        final String name = input.nextLine();
        if (!name.isEmpty()) currentDriver.setName(name);

        System.out.println("Current Phone: " + currentDriver.getPhoneNo());
        System.out.print("Enter new phone number (or press Enter to keep current): ");
        final String phone = input.nextLine();
        if (!phone.isEmpty()) currentDriver.setPhoneNo(phone);

        driverController.update(currentDriver); // Save updated info
        System.out.println("Profile updated successfully!");
    }

    // ---------------- Current Bookings ----------------
    private void viewCurrentBookings() {
        System.out.println("\n--- YOUR CURRENT BOOKINGS ---");
        final List<Booking> allBookings = bookingController.get();
        boolean hasBooking = false;

        for (final Booking b : allBookings) {
            if (b.getTaxi().getDriver() != null && b.getTaxi().getDriver().getId() == currentDriver.getId() && b.isActive()) {
                hasBooking = true;
                System.out.println("Booking ID: " + b.getId()
                        + ", Customer: " + b.getCustomer().getName()
                        + ", Pickup: " + b.getPickupLocation()
                        + ", Drop: " + b.getDropLocation()
                        + ", Fare: ₹" + b.getFare()
                        + ", Status: Active");
            }
        }

        if (!hasBooking) {
            System.out.println("No current bookings assigned to you.");
        }
    }

    // ---------------- Booking History ----------------
    private void viewBookingHistory() {
        System.out.println("\n--- YOUR BOOKING HISTORY ---");
        final List<Booking> allBookings = bookingController.get();
        boolean hasBooking = false;

        for (final Booking b : allBookings) {
            if (b.getTaxi().getDriver() != null && b.getTaxi().getDriver().getId() == currentDriver.getId()) {
                hasBooking = true;
                System.out.println("Booking ID: " + b.getId()
                        + ", Customer: " + b.getCustomer().getName()
                        + ", Pickup: " + b.getPickupLocation()
                        + ", Drop: " + b.getDropLocation()
                        + ", Fare: ₹" + b.getFare()
                        + ", Status: " + (b.isActive() ? "Active" : "Completed"));
            }
        }

        if (!hasBooking) {
            System.out.println("No bookings found for you.");
        }
    }
}
