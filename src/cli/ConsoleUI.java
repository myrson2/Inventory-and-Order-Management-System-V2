package cli;

import java.time.LocalDate;
import java.util.Scanner;

import application.inventory.InventoryService;
import application.user.AdminService;
import application.user.UserService;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import domain.user.User;
import infrastructure.history.InventoryHistory;
import util.DateUtils;
import util.IdGenerator;
import util.InputUtil;

public class ConsoleUI {    
    private Scanner scan;
    private User user;
    private UserService userService;
    private Product product;

    public ConsoleUI(UserService userService, Scanner scan){
        this.userService = userService;
        this.scan = scan;
    }

    public void start(){
        boolean running = true;
        while(running){
            Menu.displayMainMenu();
            int choice = InputUtil.readMenu("Enter Choice: ", scan);
            if(choice == 1){
                handleUserInput();
            } else {
                running = false;
                break;
            }
       }
    }

    public void handleUserInput(){
        System.out.println("1 - Register");
        System.out.println("2 - Log in");
        int ch = InputUtil.readInt("> ", scan);

        switch (ch) {
            case 1: // User Registration
                    try {
                         boolean isRegistered = false;
                               do{
                                    System.out.println("\n=== User Auth ===");
                                    String id = IdGenerator.userIDenerateID();
                                    String email = InputUtil.readString("Enter Email: ", scan);
                                    String password = InputUtil.readString("Enter Password: ", scan);
                                    String userName = name(scan);

                                            System.out.println("\nSelect User Type: | Admin || Customer || Exit |");
                                            String userType = InputUtil.readString("> ", scan).trim().toLowerCase();

                               
                                     switch (userType) {
                                        case "admin":
                                            user = new Admin(id, userName, email, password);
                                            break;
                                        case "customer":
                                            user = new Customer(id, userName, email, password);
                                            break;
                                        default:
                                            System.out.println("Choose Among the User types.");
                                            break;
                                    }

                                    isRegistered = userService.registerUser(user);
                               } while(!isRegistered);

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                break;
            
            case 2: 
                System.out.println("\n=== User Login ===");
                String email = InputUtil.readString("Enter Email: ", scan);
                String password = InputUtil.readString("Enter Password: ", scan);

                // STEP 1: Call login and capture the returned User object
                User loggedInUser = userService.login(email, password);

                // STEP 2: Check if login was successful (not null)
                if (loggedInUser != null) {
                    
                    // STEP 3: Route the user based on their specific class (Polymorphism)
                    if (loggedInUser instanceof Admin) {
                        InventoryHistory inventoryHistory = new InventoryHistory();
                        InventoryService inventoryService = new InventoryService(inventoryHistory);
                        AdminService adminService = new AdminService(inventoryService);

                        System.out.println("Routing to Admin Dashboard...");
                        adminDashboard(loggedInUser, adminService); // Pass the user to the admin dashboard
                        
                    } else if (loggedInUser instanceof Customer) {
                        System.out.println("Routing to Customer Dashboard...");
                        customerDashboard(loggedInUser); // Pass the user to the customer dashboard
                    }

                } else {
                    // Login failed (it returned null)
                    System.out.println("Please try again or register a new account.");
                }
                break;
            default:
                break;
        }
    }

        public void adminDashboard(User user, AdminService adminService){
        boolean running = true;

        do{
            adminService.checkInventory();

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
                                case 1: // Perishable
                                    LocalDate expirationDate = DateUtils.readLocalDate("Enter Expiration Date: ", scan);
                                    product = new PerishableProducts(id, name, price, quantity, expirationDate);
                                    break;
                                case 2: // Non-Perishable
                                    int warrantyInMonths = InputUtil.readInt("Enter Warranty (Months): ", scan);
                                    product = new NonPerishableProducts(id, name, price, quantity, warrantyInMonths);
                                    break;
                            }
                            adminService.addProduct(product);
                            

                            correctType = false;
                        } else {
                            System.out.println("Choose Among the Product types.");
                        }
                    }
                break;

                case 2: // Update Stock
                    String productId = InputUtil.readString("Enter Product ID: ", scan);
                    int amount = InputUtil.readInt("Enter amount (positive (increase) / negative (derease)): ", scan);
                    adminService.updateStock(productId, amount);
                break;

                case 3: // remove product
                     String productIdToRemove = InputUtil.readString("Enter Product ID: ", scan);
                     adminService.removeProduct(productIdToRemove);
                break;

                case 4: // view all orders
                    System.out.println("// Still not implemented //");
                break;

                case 5: // view logs 
                break;

                case 6: // View Inventory History
                    adminService.viewInventoryHistory();
                break;

                case 0: // loggin out
                    userService.logout();
                    System.out.println("(Enter again to exit)");
                    scan.nextLine();
                    running = false;
                break;

                default:    
                break;
            }

        }while(running);
    }

    public void customerDashboard(User user){
        boolean running = true;

        do{
            userService.displayAdmin();
            Menu.CustomerOptions();
        }while(running);
    }

    static String name(Scanner scan){
        return InputUtil.readString("EWhat should we call to you? > ", scan);
    }
}   
