package cli;

import java.util.Scanner;

import user.Admin;
import user.Customer;
import util.InputUtil;

public class ConsoleUI {
    private static Scanner scanner = new Scanner(System.in);
    private Menu menu = new Menu();

    public void start(){
        int ch;
        boolean isLogin = true;
        do {
            ch =  showLoginMenu();
            switch (ch) {
                case 1:
                    System.out.println("==== User type =====");
                    int userChoice = menu.displayMainMenu(scanner);
                    handleUserInput(userChoice);
                    break;
                case 0:
                    isLogin = false;
                    break;
                default:
                    break;
            }
        } while (isLogin);
    }

    public void handleUserInput(int choice){
        System.out.println("==== User Login ====");
        
        String id = InputUtil.userIDenerateID();
        String name = InputUtil.readString("Enter Name: ", scanner);
        String email = InputUtil.readString("Enter Email: ", scanner);
        String password = InputUtil.readString("Enter Password: ", scanner);

        switch (choice) {
            case 1: // Admin
                Admin admin = new Admin(id, name, email, password);
                showAdminOptions();
                break;
            case 2: // Customer
                Customer customer = new Customer(id, name, email, password);
                showACustomerOptions();
            break;
            default:
                break;
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
