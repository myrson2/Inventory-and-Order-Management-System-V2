package cli;

import java.util.Scanner;

import domain.user.Admin;
import domain.user.Customer;
import domain.user.User;
import util.IdGenerator;
import util.InputUtil;

public class ConsoleUI {    
    private Scanner scan;
    private User user;

    public ConsoleUI(Scanner scan){
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
