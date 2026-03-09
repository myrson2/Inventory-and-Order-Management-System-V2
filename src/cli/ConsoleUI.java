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
        this.adminService = adminService;
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
        try {
            System.out.println("\n=== User Auth ===");
            String id = IdGenerator.userIDenerateID();
            String email = InputUtil.readString("Enter Email: ", scan);
            String password = InputUtil.readString("Enter Password: ", scan);
            boolean isLogin = userService.login(email, password);

            if (isLogin) {
            do {
                System.out.println("\nSelect User Type: | Admin || Customer || Exit |");
                String userType = InputUtil.readString("> ", scan).trim().toLowerCase();

                switch (userType) {
                    case "admin":
                        String admin = name(scan);
                        user = new Admin(id, admin, email, password);
                        adminDashboard(user);

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

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

        public void adminDashboard(User user){
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

                break;

                case 5: // view logs 
                break;

                case 6: // View Inventory History
                    adminService.viewInventoryHistory();
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
        return InputUtil.readString("EWhat should we call to you? > ", scan);
    }
}   
