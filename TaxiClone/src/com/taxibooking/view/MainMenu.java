package com.taxibooking.view;

import java.util.Scanner;

/** Main menu for the Taxi Booking System */
public class MainMenu {

    private final Scanner input = new Scanner(System.in);
    private final CustomerMenu customerMenu;
    private final AdminMenu adminMenu;
    private final DriverMenu driverMenu;

    public MainMenu(final CustomerMenu customerMenu,
                    final AdminMenu adminMenu,
                    final DriverMenu driverMenu) {
        this.customerMenu = customerMenu;
        this.adminMenu = adminMenu;
        this.driverMenu = driverMenu;
    }

    /** Display main menu and handle user input */
    public void run() {
        int choice;
        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Customer Menu");
            System.out.println("2. Admin Menu");
            System.out.println("3. Driver Menu");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = input.nextInt();

            switch (choice) {
                case 1 -> customerMenu.run();
                case 2 -> adminMenu.run();
                case 3 -> driverMenu.run();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }
}
