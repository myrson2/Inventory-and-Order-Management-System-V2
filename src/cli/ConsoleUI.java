package cli;

import java.io.Console;
import java.time.LocalDate;
import java.util.Scanner;

import inventory.InventoryService;
import product.NonPerishableProducts;
import product.PerishableProducts;
import user.Admin;
import user.Customer;
import user.User;
import util.InputUtil;

public class ConsoleUI {
    private InventoryService inventoryService;
    private static Scanner scanner = new Scanner(System.in);
    private Menu menu = new Menu();
    private User user;

    public ConsoleUI(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    public void start(){
        int ch;
        boolean isLogin = true;
        ch =  showLoginMenu();
        do {
            switch (ch) {
                case 1:
                    handleUserInput();
                    break;
                case 0:
                    isLogin = false;
                    break;
                default:
                    break;
            }
        } while (isLogin);
    }

    public void handleUserInput(){
        boolean isContinue = true;
        
        try {
            while (isContinue) {
        
            System.out.println("==== User type =====");
            int userChoice = menu.displayMainMenu(scanner);

            System.out.println("==== User Login ====");
            
            String id = InputUtil.userIDenerateID();
            String name = InputUtil.readString("Enter Name: ", scanner);
            String email = InputUtil.readString("Enter Email: ", scanner);
            String password = InputUtil.readString("Enter Password: ", scanner);
        
            switch (userChoice) {
                case 1: // Admin
                    user = new Admin(id, name, email, password);
                    Admin admin = (Admin) user; // type casting base class to child class
                    user.performRoleAction();
                    
                    boolean isAdmin = true;

                    while(isAdmin){
                        showAdminOptions();
                        int choice = InputUtil.readInt("\n> ", scanner);

                        switch (choice) {
                            case 0: // Exit
                                
                                break;
                            case 1: // Add Product
                                String productid = InputUtil.productIDGenerator();
                                String productName = InputUtil.readString("Enter product Name: ", scanner);
                                double price = InputUtil.readDouble("Enter price: ", scanner);
                                int quantity = InputUtil.readInt("Enter quantity: ", scanner);

                                System.out.println("""
                                        === Type of Product ===
                                        1. NonPerishable 
                                        2. Perishable
                                        """);

                                int typeOfProduct = InputUtil.readInt("Enter type of Product: ", scanner);

                                switch (typeOfProduct) {
                                    case 1: // Non Perishable 
                                        int warrantyMonths = InputUtil.readInt("Warranty: ", scanner);
                                        
                                        NonPerishableProducts nonPerishableProducts = new NonPerishableProducts(productid, productName, price, quantity, warrantyMonths);
                                        
                                        admin.addProduct(nonPerishableProducts);
                                        break;
                                    
                                        case 2: // Perishable 
                                        LocalDate expirationDate = InputUtil.readLocalDate("Enter expiration date", scanner);
                                       
                                        PerishableProducts perishableProducts = new PerishableProducts(productid, productName, price, quantity, expirationDate);
                                        
                                        admin.addProduct(perishableProducts);
                                        break;
                                
                                    default:
                                        break;
                                }

                                break;
                            case 2: // Update Stock
                                inventoryService.
                                break;
                            case 3: // View all Orders 
                                
                                break;
                            case 4: // View Logs
                                
                                break;
                            case 5: // View Inventory History
                                
                                break;
                        
                            default:
                                break;
                        }
                    }

                    
                    break;
                case 2: // Customer
                    user = new Customer(id, name, email, password);
                    showACustomerOptions();
                break;

                case 0:
                    isContinue = false; 
                break;
                default:
                    break;
            }
        } 
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
        

    public void showAdminOptions(){
        menu.AdminOptions();
    }

    public void showACustomerOptions(){
        menu.CustomerOptions();
    }

    public int showLoginMenu(){
        System.out.println("==== Welcome ====");
        System.out.print("""
                1 - Log in
                0 - Log out
                """);
        int ch = InputUtil.readMenu("> ", scanner);

        return ch;
    }
}
