import java.util.Scanner;

import javax.management.Notification;

import application.inventory.InventoryService;
import application.order.OrderService;
import application.user.AdminService;
import application.user.CustomerService;
import application.user.UserService;
import cli.ConsoleUI;
import infrastructure.file.FileManager;
import infrastructure.history.InventoryHistory;
import infrastructure.history.OrderHistory;
import infrastructure.log.LoggerService;
import infrastructure.notification.NotificationService;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("======================================");
        System.out.println("=    Welcome to SmartStock System    =");
        System.out.println("======================================");
        
        Scanner scan = new Scanner(System.in);
        // Dependencies
        LoggerService loggerService = new LoggerService();
        FileManager fileManager = new FileManager();
        InventoryHistory inventoryHistory = new InventoryHistory();
        NotificationService notificationService = new NotificationService();
        InventoryService inventoryService = new InventoryService(fileManager, loggerService, inventoryHistory, notificationService);
        OrderHistory orderHistory = new OrderHistory();

        // Services
        OrderService orderService = new OrderService(notificationService, fileManager, loggerService, inventoryService, orderHistory);
        UserService userService = new UserService(loggerService);
        AdminService adminService = new AdminService(orderService, inventoryService, loggerService, fileManager);
        CustomerService customerService = new CustomerService(orderService, inventoryService, loggerService);

        // Console User Interface for Interactions
        ConsoleUI consoleUI = new ConsoleUI(notificationService, userService, adminService, customerService, scan);
        
        // Start the main application loop
        try {
            consoleUI.start();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scan.close();
        }

        System.out.println("Thank You for using the System.");
    }

     public static void menu(){
        System.out.print("""
                1 - login 
                2 - logout
                """);
    }
}
