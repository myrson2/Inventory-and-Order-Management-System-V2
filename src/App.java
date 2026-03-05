import java.util.Scanner;

import application.inventory.InventoryService;
import cli.ConsoleUI;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        Scanner scan = new Scanner(System.in);
        InventoryService inventoryService = new InventoryService();
        ConsoleUI consoleUI = new ConsoleUI(inventoryService, scan);

        consoleUI.start();
    }
}
