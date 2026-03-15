import java.util.Scanner;
import application.inventory.InventoryService;
import application.user.AdminService;
import application.user.UserService;
import cli.ConsoleUI;
import infrastructure.file.FileManager;
import infrastructure.log.LoggerService;
import util.IdGenerator;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        
        Scanner scan = new Scanner(System.in);
        LoggerService loggerService = new LoggerService();
        FileManager fileManager = new FileManager();
        InventoryService inventoryService = new InventoryService();
        
        UserService userService = new UserService(loggerService);
        AdminService adminService = new AdminService(inventoryService, loggerService, fileManager);
        ConsoleUI consoleUI = new ConsoleUI(userService, adminService, scan);

        consoleUI.start();
    }
}
