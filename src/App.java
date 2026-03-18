import java.util.Scanner;
import application.inventory.InventoryService;
import application.order.OrderService;
import application.user.AdminService;
import application.user.CustomerService;
import application.user.UserService;
import cli.ConsoleUI;
import infrastructure.file.FileManager;
import infrastructure.history.OrderHistory;
import infrastructure.log.LoggerService;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("======================================");
        System.out.println("=    Welcome to SmartStock System    =");
        System.out.println("======================================");
        
        Scanner scan = new Scanner(System.in);
        LoggerService loggerService = new LoggerService();
        FileManager fileManager = new FileManager();
        InventoryService inventoryService = new InventoryService();
        OrderHistory orderHistory = new OrderHistory();
        OrderService orderService = new OrderService(fileManager, loggerService, inventoryService, orderHistory);
        
        UserService userService = new UserService(loggerService);
        AdminService adminService = new AdminService(inventoryService, loggerService, fileManager);

        CustomerService customerService = new CustomerService(orderService, inventoryService, loggerService);
        ConsoleUI consoleUI = new ConsoleUI(userService, adminService, customerService, scan);
        
        // Start the main application loop
        try {
            consoleUI.start();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scan.close();
        }
    }
}
