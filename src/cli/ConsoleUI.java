package cli;

import java.util.Scanner;

import application.inventory.InventoryService;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import domain.user.User;
import util.IdGenerator;
import util.InputUtil;

public class ConsoleUI {    
    private Scanner scan;
    private InventoryService inventoryService;
    private User user;
    private Product product;

    public ConsoleUI(InventoryService inventoryService, Scanner scan){
        this.inventoryService = inventoryService;
        this.scan = scan;
    }

    public void start(){
       while(true){
            Menu.displayMainMenu();
            int choice = InputUtil.readMenu("Enter Choice: ", scan);
            if(choice == 1){
                handleUserInput();
            } else {
                //logout
                break;
            }
       }
    }

    public void handleUserInput(){
        boolean running = true;

        String id = IdGenerator.userIDenerateID();
        String email = InputUtil.readString("Enter Email: ", scan);
        String password = InputUtil.readString("Enter Password", scan);

        do {
            System.out.println("\nSelect User Type: | Admin || Customer || Exit |");
            String userType = InputUtil.readString(">", scan).trim().toLowerCase();

            switch (userType) {
                case "admin":
                    String admin = name(scan);
                    user = new Admin(id, admin, email, password);
                    adminDashboard();

                    break;
                case "customer":
                    String customer = name(scan);
                    user = new Customer(id, customer, email, password);
                    customerDashboard();

                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Choose Among the User types.");
                    break;
            }
        } while (running);
    }

    public void adminDashboard(){
        boolean running = true;

        do{
            Menu.AdminOptions();
            int choice = InputUtil.readInt("> ", scan);

            switch (choice) {
                case 1: // Add product
                boolean correctType = true;
                    while (correctType) {
                         System.out.println("""
                                Product Type:
                                1. Perishable 
                                2. Non-Perishable 
                                """);
                        int type = InputUtil.readInt("> ", scan);
                        if(type == 1 || type == 2){
                            
                            String id = IdGenerator.productIDGenerator();
                            String name = InputUtil.readString("Product Name: ", scan);
                            double price = InputUtil.readDouble("Price: ", scan);
                            int quantity = InputUtil.readInt("Quantity: ", scan);
                            
                            switch(type){
                                case 1:
                                    
                                    break;
                                case 2:

                                    break;
                            }

                        } else {
                            System.out.println("Choose Among the Product types.");
                        }
                    }
                break;

                case 2:
                    
                break;
                default:
                    break;
            }

        }while(running);
    }

    public void customerDashboard(){
        boolean running = true;

        do{
            Menu.CustomerOptions();
        }while(running);
    }

    static String name(Scanner scan){
        return InputUtil.readString("EWhat should we call to you? >", scan);
    }
}   
