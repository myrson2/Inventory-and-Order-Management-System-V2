import cli.ConsoleUI;
import inventory.InventoryHistory;
import inventory.InventoryService;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        InventoryHistory inventoryHistory = new InventoryHistory();
        InventoryService inventoryService = new InventoryService(inventoryHistory);
        ConsoleUI consoleUI = new ConsoleUI(inventoryService);

        consoleUI.start();
    }
}
