import application.inventory.InventoryService;
import cli.ConsoleUI;
import domain.inventory.InventoryHistory;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        InventoryHistory inventoryHistory = new InventoryHistory();
        InventoryService inventoryService = new InventoryService(inventoryHistory);
        ConsoleUI consoleUI = new ConsoleUI(inventoryService);

        consoleUI.start();
    }
}
