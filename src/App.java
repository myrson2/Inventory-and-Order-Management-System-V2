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

        FileManager fileManager = new FileManager();
        IdGenerator idGenerator = new IdGenerator();
        UserService userService = new UserService();
        LoggerService loggerService = new LoggerService();
        InventoryService inventoryService = new InventoryService(loggerService);
        AdminService adminService = new AdminService(inventoryService, fileManager);
        ConsoleUI consoleUI = new ConsoleUI(userService, adminService, loggerService,  idGenerator, scan);

        consoleUI.start();
    }
}
