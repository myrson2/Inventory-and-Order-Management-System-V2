import java.util.Scanner;

import application.inventory.InventoryService;
import application.user.AdminService;
import application.user.UserService;
import cli.ConsoleUI;
import infrastructure.history.InventoryHistory;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        Scanner scan = new Scanner(System.in);
       
        UserService userService = new UserService();
        ConsoleUI consoleUI = new ConsoleUI(userService, scan);

        consoleUI.start();
    }
}
