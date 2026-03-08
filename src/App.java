import java.util.Scanner;

import application.inventory.InventoryService;
import application.user.AdminService;
import application.user.UserService;
import cli.ConsoleUI;
import domain.inventory.Inventory;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        Scanner scan = new Scanner(System.in);
        Inventory inventory = new Inventory();
        InventoryService inventoryService = new InventoryService(inventory);
        AdminService adminService = new AdminService(inventoryService);
        UserService userService = new UserService();
        ConsoleUI consoleUI = new ConsoleUI(userService, adminService, scan);

        consoleUI.start();
    }
}
