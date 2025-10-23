package com.taxibooking.view;

import java.util.Scanner;

/**
 * Main menu for the Taxi Booking System.
 * Provides navigation to Customer, Admin, and Driver menus.
 */
public class MainMenu {

    private final Scanner input = new Scanner(System.in);

    // References to the sub-menus
    private final CustomerMenu customerMenu;
    private final AdminMenu adminMenu;
    private final DriverMenu driverMenu;

    /**
     * Constructs the main menu with all sub-menus.
     *
     * @param customerMenu Customer menu instance
     * @param adminMenu    Admin menu instance
     * @param driverMenu   Driver menu instance
     */
    public MainMenu(final CustomerMenu customerMenu,
                    final AdminMenu adminMenu,
                    final DriverMenu driverMenu) {
        this.customerMenu = customerMenu;
        this.adminMenu = adminMenu;
        this.driverMenu = driverMenu;
    }

    /**
     * Displays the main menu and handles user input until exit.
     */
    public void run() {
        int choice;

        do {
            // Display main menu options
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Customer Menu");
            System.out.println("2. Admin Menu");
            System.out.println("3. Driver Menu");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = input.nextInt(); // Read user input

            // Navigate to selected menu
            switch (choice) {
                case 1 -> customerMenu.run();
                case 2 -> adminMenu.run();
                case 3 -> driverMenu.run();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 0); // Repeat until user chooses to exit
    }
}
