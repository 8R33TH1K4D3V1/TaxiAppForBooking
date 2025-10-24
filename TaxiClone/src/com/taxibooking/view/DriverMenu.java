package com.taxibooking.view;

import com.taxibooking.controller.DriverController;
import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.TaxiController;
import com.taxibooking.model.Driver;
import com.taxibooking.model.Taxi;
import com.taxibooking.model.Booking;
import com.taxibooking.service.TaxiService;

import java.util.Collection;
import java.util.Scanner;

/**
 * Driver menu for managing driver actions.
 * Supports viewing current bookings, editing profile, and booking history.
 */
public class DriverMenu {

    private final Scanner input = new Scanner(System.in);
    private final TaxiController taxiController;
    private final BookingController bookingController;
    private final DriverController driverController;
    private Driver currentDriver;

    /** Constructor with required controllers */
    public DriverMenu(final TaxiService taxiService,
                      final BookingController bookingController,
                      final DriverController driverController) {
        this.taxiController = new TaxiController();
        this.bookingController = bookingController;
        this.driverController = driverController;
    }

    /** Main driver menu loop */
    public void run() {
        login();

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

    /** Display driver menu options */
    private void menu() {
        System.out.println("\n--- DRIVER MENU ---");
        System.out.println("1. Current Booking");
        System.out.println("2. Edit Profile");
        System.out.println("3. Booking History");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
    }

    /** Prompt driver for ID and validate login */
    private void login() {
        System.out.print("Enter your Driver ID: ");
        final int driverId = input.nextInt();
        input.nextLine();
        currentDriver = get(driverId);

        if (currentDriver == null) {
            System.out.println("Driver ID not found. Exiting.");
            System.exit(0);
        }

        System.out.println("Welcome, " + currentDriver.getName() + "!");
    }

    /** Find a driver by ID by checking all registered taxis */
    private Driver get(final int id) {
        final Collection<Taxi> taxis = taxiController.get();
        for (final Taxi taxi : taxis) {
            if (taxi.getDriver() != null && taxi.getDriver().getId() == id) {
                return taxi.getDriver();
            }
        }
        return null;
    }

    /** Edit driver's name and phone number */
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

        driverController.update(currentDriver);
        System.out.println("Profile updated successfully!");
    }

    /** Display all active bookings assigned to the driver */
    private void viewCurrentBookings() {
        System.out.println("\n--- YOUR CURRENT BOOKINGS ---");
        final Collection<Booking> allBookings = bookingController.get();
        boolean hasBooking = false;

        for (final Booking booking : allBookings) {
            final Driver driver = booking.getTaxi().getDriver();
            if (driver != null && driver.getId() == currentDriver.getId() && booking.isActive()) {
                hasBooking = true;
                System.out.println("Booking ID: " + booking.getId()
                        + ", Customer: " + booking.getCustomer().getName()
                        + ", Pickup: " + booking.getPickupLocation()
                        + ", Drop: " + booking.getDropLocation()
                        + ", Fare: ₹" + booking.getFare()
                        + ", Status: Active");
            }
        }

        if (!hasBooking) {
            System.out.println("No current bookings assigned to you.");
        }
    }

    /** Display all bookings (active and completed) for the driver */
    private void viewBookingHistory() {
        System.out.println("\n--- YOUR BOOKING HISTORY ---");
        final Collection<Booking> allBookings = bookingController.get();
        boolean hasBooking = false;

        for (final Booking booking : allBookings) {
            final Driver driver = booking.getTaxi().getDriver();
            if (driver != null && driver.getId() == currentDriver.getId()) {
                hasBooking = true;
                System.out.println("Booking ID: " + booking.getId()
                        + ", Customer: " + booking.getCustomer().getName()
                        + ", Pickup: " + booking.getPickupLocation()
                        + ", Drop: " + booking.getDropLocation()
                        + ", Fare: ₹" + booking.getFare()
                        + ", Status: " + (booking.isActive() ? "Active" : "Completed"));
            }
        }

        if (!hasBooking) {
            System.out.println("No bookings found for you.");
        }
    }
}
