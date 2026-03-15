package cli;

import java.time.LocalDate;
import java.util.Scanner;

import application.user.AdminService;
import application.user.UserService;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import domain.user.User;
import infrastructure.log.LoggerService;
import util.DateUtils;
import util.IdGenerator;
import util.InputUtil;

public class ConsoleUI {    
    private Scanner scan;
    private User user;
    private AdminService adminService;
    private UserService userService;
    private Product product;

    public ConsoleUI(UserService userService, AdminService adminService, Scanner scan){
        this.userService = userService;
        this.scan = scan;
        this.adminService = adminService;
    }

    // Execution Starts
    public void start(){
        boolean running = true;
        while(running){
            Menu.displayMainMenu();

            int choice = InputUtil.readMenu("Enter Choice: ", scan);
            
            switch (choice) {
                case 1 -> handleUserInput();
                case 2 -> running = false;
                default -> System.out.println("1 and 2 Only. Try again.");
            }
       }
    }

    public void handleUserInput(){
        int choice;

        // User Authentication Flow
        do{
            System.out.println("=======================");
            System.out.println("1 - Register");
            System.out.println("2 - Log in");
            System.out.println("3 - Exit");
            choice = InputUtil.readInt("> ", scan);

            switch (choice) {
                case 1: // User Registration
                try {
                    boolean isRegistered = false;
                    do{
                        // Inputting Fields of a User
                        System.out.println("\n=== User Authentication ===");
                        
                        String id = IdGenerator.productIDGenerator();
                        String email = InputUtil.readEmail("Email: ", scan);
                        String password = InputUtil.readPassword("Password: ", scan);
                        String userName = InputUtil.readString("EWhat should we call to you? > ", scan);
                        
                        // Choosing User Type
                        System.out.println("\nSelect User Type: | Admin || Customer || Exit |");
                        String userType = InputUtil.readString("> ", scan).trim().toLowerCase();
                        
                        switch (userType) {
                            case "admin":
                                user = new Admin(id, userName, email, password);
                                isRegistered = userService.registerUser(user);
                                break;
                            case "customer":
                                user = new Customer(id, userName, email, password);
                                isRegistered = userService.registerUser(user);
                                break;
                            case "exit":
                                isRegistered = true;
                                break;
                            default:
                                System.out.println("Choose Among the User types.");
                                break;
                        }
                        
                    } while(!isRegistered);

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
                
                case 2: // User Login
                    System.out.println("\n=== User Login ===");

                    boolean isLogin = false;
                    do {
                        try{
                            String email = InputUtil.readEmail("Email: ", scan);
                            String password = InputUtil.readPassword("Password: ", scan);

                            // STEP 1: Call login and capture the returned User object
                            User loggedInUser = userService.login(email, password);

                            // STEP 2: Check if login was successful (not null)
                            if (loggedInUser != null) {
                                
                                // STEP 3: Route the user based on their specific class (Polymorphism)
                                if (loggedInUser instanceof Admin) {

                                    System.out.println("Routing to Admin Dashboard...");
                                    adminDashboard(loggedInUser); // Pass the user to the admin dashboard
                                    
                                } else if (loggedInUser instanceof Customer) {
                                    System.out.println("Routing to Customer Dashboard...");
                                    customerDashboard(loggedInUser); // Pass the user to the customer dashboard
                                }

                                System.out.println("Login successful! Welcome, " + loggedInUser.getName());

                                isLogin = true;
                            } else {
                                // Login failed (it returned null)
                                System.out.println("Please try again or register a new account.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } while(!isLogin);
                    break;
                case 3:
                    System.out.println("Exitingg....");
                break;
                default:
                    System.out.println("Error: 1 - 3 Only");
                    break;
            }
            } while(choice != 3);
    }

    public void adminDashboard(User user){
        boolean running = true;
        do{
            System.out.println("\n====== Welcome To Admin Dashboard =======\n");
            System.out.println("Your Inventory: ");
            adminService.getProducts(user);
            System.out.println("\n=========================================\n");

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
                
                    try {
                        String id = IdGenerator.productIDGenerator();
                        String name = InputUtil.readString("Product Name:   ", scan);
                        double price = InputUtil.readDouble("Price: ", scan);

                        if(price < 0){
                            throw new IllegalArgumentException("Price cannot be negative.");
                        }

                        int quantity = InputUtil.readInt("Quantity: ", scan);

                        if(quantity < 0){
                            throw new IllegalArgumentException("Quantity cannot be negative.");
                        }
                        
                        switch(type){
                            case 1: // Perishable
                                LocalDate expirationDate = DateUtils.readLocalDate("Enter Expiration Date: ", scan);
                                product = new PerishableProducts(id, name, price, quantity, expirationDate);
                                break;
                            case 2: // Non-Perishable
                                int warrantyInMonths = InputUtil.readInt("Enter Warranty (Months): ", scan);
                                product = new NonPerishableProducts(id, name, price, quantity, warrantyInMonths);
                                break;
                            default:
                                System.out.println("Choose Among the Product types.");
                                break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    adminService.addProduct(user, product);
                    correctType = false;
                }
                break;

                case 2: // Update Stock
                    String productId = InputUtil.readString("Enter Product ID: ", scan);
                    int amount = InputUtil.readInt("Enter amount (positive (increase) / negative (derease)): ", scan);
                    adminService.updateStock(user, productId, amount);
                break;

                case 3: // remove product
                     String productIdToRemove = InputUtil.readString("Enter Product ID: ", scan);
                     adminService.removeProduct(user, productIdToRemove);
                break;

                case 4: // view all orders
                    System.out.println("// Still not implemented //");
                break;

                case 5: // view logs  
                   adminService.viewLogs(user);
                break;

                case 6: // View Inventory History
                    adminService.viewInventoryHistory(user);
                break;

                case 7: // save to file
                    
                break;

                case 8: // load to file

                break;
                case 0: // loggin out
                    userService.logout();
                    System.out.println("(Enter again to exit)");
                    scan.nextLine();
                    running = false;
                break;

                default:    
                    System.out.println("0 - 6 Only");
                break;
            }

        }while(running);
    }

    public void customerDashboard(User user){
        boolean running = true;

        do{
            do {
                userService.displayAdmin();
                String adminName = InputUtil.readString("> ", scan);

                Admin admin = userService.getAdmin(adminName); // accessing which store is chosen by the customer

                do {
                    adminService.getProducts(admin);

                    Menu.CustomerOptions();
                    int choice = InputUtil.readInt("Enter choice: ", scan);

                    switch (choice) {
                        case 1:
                            
                            break;
                    
                        default:
                            break;
                    }
                } while (running);

                
            } while (running);
            
        }while(running);
    }
}   
