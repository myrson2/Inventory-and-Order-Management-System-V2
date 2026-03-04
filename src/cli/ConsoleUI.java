package cli;

import java.util.Scanner;

import user.Admin;
import user.Customer;
import user.User;
import util.InputUtil;

public class ConsoleUI {
    private static Scanner scanner = new Scanner(System.in);
    private Menu menu = new Menu();
    private User user;

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
                    showAdminOptions();
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
