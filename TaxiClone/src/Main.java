import com.taxibooking.controller.*;
import com.taxibooking.service.*;
import com.taxibooking.view.*;
import com.taxibooking.model.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for the Taxi Booking System application.
 * Initializes services, controllers, menus, and starts the main menu.
 */
public final class Main {

    private Main() {}

    public static void main(final String[] args) {

        final TaxiService taxiService = new TaxiServiceImpl();
        final List<Driver> driverList = new ArrayList<>();
        final DriverServiceImpl driverService = new DriverServiceImpl(driverList);
        final DriverRegistrationService driverRegService = new DriverRegistrationServiceImpl(driverList);
        final TaxiRegistrationService taxiRegService = new TaxiRegistrationServiceImpl(taxiService.get());
        final BookingController bookingController = new BookingController();
        final DriverController driverController = new DriverController(driverService);
        final TaxiController taxiController = new TaxiController(taxiService);
        final DriverRegistrationController driverRegController = new DriverRegistrationController(driverRegService);
        final TaxiRegistrationController taxiRegController = new TaxiRegistrationController(taxiRegService);
        final CustomerMenu customerMenu = new CustomerMenu(taxiService, bookingController);
        final AdminMenu adminMenu = new AdminMenu(taxiController, driverController, taxiRegController, driverRegController);
        final DriverMenu driverMenu = new DriverMenu(taxiService, bookingController, driverController);
        final MainMenu mainMenu = new MainMenu(customerMenu, adminMenu, driverMenu);

        taxiService.registerDemoTaxis();
        mainMenu.run();
    }
}
