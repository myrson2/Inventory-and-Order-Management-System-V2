package cli;

import java.util.Scanner;
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
        switch (choice) {
            case 1: // Admin
                showAdminOptions();
                break;

            case 2: // Customer
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
