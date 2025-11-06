import com.taxibooking.controller.BookingController;
import com.taxibooking.controller.DriverController;
import com.taxibooking.controller.DriverRegistrationController;
import com.taxibooking.controller.TaxiController;
import com.taxibooking.controller.TaxiRegistrationController;

import com.taxibooking.view.AdminMenu;
import com.taxibooking.view.CustomerMenu;
import com.taxibooking.view.DriverMenu;
import com.taxibooking.view.MainMenu;



public final class Main {

    private final BookingController bookingController;
    private final TaxiController taxiController;
    private final DriverController driverController;
    private final DriverRegistrationController driverRegController;
    private final TaxiRegistrationController taxiRegController;
    private final CustomerMenu customerMenu;
    private final DriverMenu driverMenu;
    private final AdminMenu adminMenu;
    private final MainMenu mainMenu;

    public Main() {
        this.bookingController = BookingController.getInstance();
        this.taxiController = TaxiController.getInstance();
        this.driverController = DriverController.getInstance();
        this.driverRegController = DriverRegistrationController.getInstance();
        this.taxiRegController = TaxiRegistrationController.getInstance();
        this.customerMenu = new CustomerMenu(bookingController);
        this.driverMenu = new DriverMenu(bookingController, driverController);
        this.adminMenu = new AdminMenu(taxiController, taxiRegController,driverRegController,driverController);
        this.mainMenu = new MainMenu(customerMenu, adminMenu, driverMenu);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.taxiController.registerDemoTaxis();
        app.mainMenu.run();
    }
}
