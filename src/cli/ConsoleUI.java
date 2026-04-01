package cli;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import application.user.AdminService;
import application.user.CustomerService;
import application.user.UserService;
import domain.order.Order;
import domain.order.OrderItem;
import domain.product.NonPerishableProducts;
import domain.product.PerishableProducts;
import domain.product.Product;
import domain.user.Admin;
import domain.user.Customer;
import domain.user.User;
import exception.EntityNotFountException;
import infrastructure.notification.NotificationService;
import util.DateUtils;
import util.IdGenerator;
import util.InputUtil;

public class ConsoleUI {    
    private Scanner scan;
    private User user;
    private AdminService adminService;
    private CustomerService customerService;
    private UserService userService;
    private NotificationService notificationService;
    private Product product;

    public ConsoleUI(NotificationService notificationService, UserService userService, AdminService adminService, CustomerService customerService, Scanner scan){
        this.userService = userService;
        this.scan = scan;
        this.adminService = adminService;
        this.customerService = customerService;
        this.notificationService = notificationService;
    }

    // Execution Starts
    public void start()
    {
        // Opening the App 
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

    public void handleUserInput()
    {
        int choice;

        // User Authentication Flow
        do{
            showLoginMenu();
            choice = InputUtil.readInt("> ", scan);

            switch (choice) {
                case 1 -> userRegistration();
                case 2 -> userLogin();
                case 3 -> System.out.println("Exitingg....");
                default -> System.out.println("Error: 1 - 3 Only");
            }
        } while(choice != 3);
    }

    public void customerDashboard(User user)
    {
        Customer customer = (Customer) user;
        boolean running = true;

        do{
            userService.displayAdmin();
            String adminName = InputUtil.readString("> ", scan);

           // accessing which store is chosen by the customer
            try {
                Admin admin = userService.getAdmin(adminName); 
                int choice;
                Order order = null;

                do {
                    Menu.CustomerOptions();
                    choice = InputUtil.readInt("Enter choice: ", scan);

                    switch (choice) {
                        case 1:
                            System.out.println("======================================");
                            System.out.println("=           Browse Products          =");
                            System.out.println("======================================");
                            customerService.browseProducts(admin);
                        break;

                        case 2:
                            System.out.println("======================================");
                            System.out.println("=           Create an Order          =");
                            System.out.println("======================================");
                            order = customerService.createOrder(customer, admin);
                        break;

                        case 3:
                            System.out.println("======================================");
                            System.out.println("=             Add to Cart            =");
                            System.out.println("======================================");

                            if (order == null) {
                                System.out.println("No active order. Please create one first.");
                                break;
                            }
                            
                            String productID = InputUtil.readString("Product Id: ", scan);
                            Product product = customerService.getProducts(productID, admin);
                            int quantity = InputUtil.readInt("Quantity: ", scan);

                            customerService.addItemToOrder(order, product, quantity);
                        break;

                        case 4:
                            System.out.println("======================================");
                            System.out.println("=           Finalize Order           =");
                            System.out.println("======================================");

                            List<OrderItem> items = order.getItems();

                            for (OrderItem orderItem : items) {
                                System.out.println(orderItem.getItemDetails());
                            }

                            System.out.println("Total: " + order.calculateTotal());

                            System.out.println("--------------------------------------\n");

                            String finalOrder;

                            while (true) {
                                finalOrder = InputUtil.readString("Confirm Order (Yes/No): ", scan).trim();

                                if (finalOrder.equalsIgnoreCase("yes") || finalOrder.equalsIgnoreCase("no")) {
                                    if (finalOrder.equalsIgnoreCase("yes")) {
                                        customerService.finalizeOrder(customer.getName(), order);
                                    } 
                                    break;
                                }

                                System.out.println("Invalid input. Please enter Yes or No only.");
                            }

                        break;

                        case 5:
                            System.out.println("======================================");
                            System.out.println("=            Cancel Order            =");
                            System.out.println("======================================");

                            List<OrderItem> itemss = order.getItems();

                            for (OrderItem orderItem : itemss) {
                                System.out.println(orderItem.getItemDetails());
                            }

                            String cancelOrder;

                            while (true) {
                                cancelOrder = InputUtil.readString("Cancel Order (Yes/No): ", scan).trim();

                                if (cancelOrder.equalsIgnoreCase("yes") || cancelOrder.equalsIgnoreCase("no")) {
                                    if (cancelOrder.equalsIgnoreCase("yes")) {
                                        customerService.cancelOrder(order);
                                    } 
                                    break;
                                }

                                System.out.println("Invalid input. Please enter Yes or No only.");
                            }
                        break;

                        case 6:
                            System.out.println("======================================");
                            System.out.println("=            Order History           =");
                            System.out.println("======================================");

                            customerService.viewOrderHistory();

                        break;

                        case 0:
                        break;
                    
                        default:
                            break;
                    }
                } while (choice != 0);

              } catch (EntityNotFountException e){
                notificationService.notify(e.getMessage());
            }
        } while (running);
    }

    // Customer Dashboard Responsibility Functions =====================================



    public void adminDashboard(User user)
    {
        boolean running = true;
        do{
            System.out.println("======================================");
            System.out.println("=           Admin Dashboard          =");
            System.out.println("======================================");
            System.out.println("            Your Inventory            ");
            System.out.println("--------------------------------------\n");
            adminService.printProducts(user);
            System.out.println("--------------------------------------\n");
            System.out.println("note: save files before loging out:]");
             System.out.println("--------------------------------------\n");

            Menu.AdminOptions();
            int choice = InputUtil.readInt("> ", scan);

            switch (choice) {
                case 1 -> addProduct(user);
                case 2 -> updateStock(user);
                case 3 -> removeProduct(user);
                case 4 -> { // view all orders
                    System.out.println("======================================");
                    System.out.println("=           View All Orders          =");
                    System.out.println("======================================");
                    System.out.println("// Still not implemented //");
                    /*
                        1 - Customer Creates an Order
                        2 - Access The Customer Order in OrderService
                        3 - get the Order to the created HashMap in OrderService
                        4 - Access Order Items from the Order
                        5 - loop all customer that buys in admin stores
                    */
                }
                case 5 -> viewLogs(user);
                case 6 -> viewInventoryHistory(user);
                case 7 -> saveFile(user);
                case 8 -> loadFile(user);            
                case 0 -> {// loggin out
                    userService.logout();
                    System.out.println("(Enter again to exit)");
                    scan.nextLine();
                    running = false;
                }
                default -> System.out.println("0 - 6 Only");
            }

        }while(running);
    }
    // Admin Dashboard Responsibility Functions =======================================
    public void addProduct(User user)
    {
        System.out.println("======================================");
        System.out.println("=             Add Products           =");
        System.out.println("======================================");
        
        boolean correctType = true;
        
        while (correctType) {
            System.out.println("""
                Product Type:
                1. Perishable 
                2. Non-Perishable 
                """);

            int type = InputUtil.readInt("> ", scan);
            try {
                String id = IdGenerator.generateProductID();
                String name = InputUtil.readString("Product Name: ", scan);
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
    }

    public void updateStock(User user)
    {
        System.out.println("======================================");
        System.out.println("=             Update Stock           =");
        System.out.println("======================================");
        String productId = InputUtil.readString("Enter Product ID: ", scan);
        int amount = InputUtil.readInt("Enter amount (positive (increase) / negative (derease)): ", scan);
        adminService.updateStock(user, productId, amount);
    }

    public void removeProduct(User user)
    {
        System.out.println("======================================");
        System.out.println("=            Remove Product          =");
        System.out.println("======================================");
        String productIdToRemove = InputUtil.readString("Enter Product ID: ", scan);
        adminService.removeProduct(user, productIdToRemove);
    }

    public void viewLogs(User user)
    {
        System.out.println("======================================");
        System.out.println("=           View Admin Logs          =");
        System.out.println("======================================");
        adminService.viewLogs(user);
    }

    public void viewInventoryHistory(User user)
    {
        System.out.println("======================================");
        System.out.println("=       View Inventory History       =");
        System.out.println("======================================");
        adminService.viewInventoryHistory(user);
    }

    public void saveFile(User user)
    {// save to file
        System.out.println("======================================");
        System.out.println("=            Save To File            =");
        System.out.println("======================================");
        adminService.saveProductsToFile(user);
    }

    public void loadFile(User user){
         // load to file
        System.out.println("======================================");
        System.out.println("=            Load To File            =");
        System.out.println("======================================");
        adminService.loadProductsToFile(user);
    }

    // User Authentication functions ===========================================
    public static void showLoginMenu()
    {
        System.out.println("======================================");
        System.out.println("1 - Register");
        System.out.println("2 - Log in");
        System.out.println("3 - Exit");
    }
    
    public void userRegistration()
    {
        try {
                boolean isRegistered = false;
                do{
                    // Inputting Fields of a User
                    System.out.println("======================================");
                    System.out.println("=          User Registration         =");
                    System.out.println("======================================");
                    String email = InputUtil.readEmail("Email: ", scan);
                    String password = InputUtil.readPassword("Password: ", scan);
                    String userName = InputUtil.readString("EWhat should we call to you? > ", scan);
                    
                    // Choosing User Type
                    System.out.println("--------------------------------------");
                    System.out.println("User Type: Admin || Customer || Exit ");
                    String userType = InputUtil.readString("> ", scan).trim().toLowerCase();
                    
                    switch (userType) {
                        case "admin":
                            String adminID = IdGenerator.generateAdminID();
                            user = new Admin(adminID, userName, email, password);
                            isRegistered = userService.registerUser(user);
                            break;
                        case "customer":
                            String customerID = IdGenerator.generateCustomerID();
                            user = new Customer(customerID, userName, email, password);
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
    }

    public void userLogin()
    {
         System.out.println("======================================");
        System.out.println("=         User Authentication        =");
        System.out.println("======================================");

        boolean isLogin = false;
        do {
            try{
                String email = InputUtil.readEmail("Email: ", scan);
                String password = InputUtil.readPassword("Password: ", scan);

                // STEP 1: Call login and capture the returned User object
                User loggedInUser = userService.login(email, password);

                // STEP 2: Check if login was successful (not null)
                if (loggedInUser != null) {
                    System.out.println("Login successful! Welcome, " + loggedInUser.getName());
                    // STEP 3: Route the user based on their specific class (Polymorphism)
                    if (loggedInUser instanceof Admin) {
                        adminDashboard(loggedInUser); // Pass the user to the admin dashboard
                        
                    } else if (loggedInUser instanceof Customer) {
                        customerDashboard(loggedInUser); // Pass the user to the customer dashboard
                    }
                } else {
                    // Login failed (it returned null)
                    System.out.println("Please try again or register a new account.");
                }

                isLogin = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while(!isLogin);
    }

}   

