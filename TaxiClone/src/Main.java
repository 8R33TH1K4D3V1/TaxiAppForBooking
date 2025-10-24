import com.taxibooking.model.Taxi;
import com.taxibooking.model.Driver;
import com.taxibooking.controller.*;
import com.taxibooking.service.*;
import com.taxibooking.view.*;

import java.util.ArrayList;
import java.util.Collection;

public final class Main {

    private final TaxiService taxiService;
    private final Collection<Driver> driverList;
    private final DriverService driverService;
    private final DriverRegistrationService driverRegService;
    private final TaxiRegistrationService taxiRegService;
    private final BookingController bookingController;
    private final DriverController driverController;
    private final TaxiController taxiController;
    private final DriverRegistrationController driverRegController;
    private final TaxiRegistrationController taxiRegController;
    private final CustomerMenu customerMenu;
    private final AdminMenu adminMenu;
    private final DriverMenu driverMenu;
    private final MainMenu mainMenu;


    public Main() {

        this.driverList = new ArrayList<>();
        Collection<Taxi> taxiList = new ArrayList<>();

        this.taxiService = TaxiService.getInstance(taxiList);
        this.driverService = DriverService.getInstance(driverList);
        this.driverRegService = DriverRegistrationService.getInstance(driverList);
        this.taxiRegService = TaxiRegistrationService.getInstance(taxiList);
        this.bookingController = new BookingController();
        this.driverController = new DriverController(driverService);
        this.taxiController = new TaxiController();
        this.driverRegController = new DriverRegistrationController(driverRegService);
        this.taxiRegController = new TaxiRegistrationController(taxiRegService);
        this.customerMenu = new CustomerMenu(taxiService, bookingController);
        this.adminMenu = new AdminMenu(taxiController, driverController, taxiRegController, driverRegController);
        this.driverMenu = new DriverMenu(taxiService, bookingController, driverController);
        this.mainMenu = new MainMenu(customerMenu, adminMenu, driverMenu);
    }


    public static void main(final String[] args) {
        final Main app = new Main();
        app.taxiService.registerDemoTaxis();
        app.mainMenu.run();
    }
}
