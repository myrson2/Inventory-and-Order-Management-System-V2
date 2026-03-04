package cli;

import java.util.Scanner;

import util.InputUtil;

public class Menu {
    public int displayMainMenu(Scanner scanner){
        System.out.print("""
                1 - Admin
                0 - Customer
                """);
        int ch = InputUtil.readMenu("> ", scanner);
        return ch;
    }

    public void AdminOptions(){
        System.out.println("""
                0. Exit
                1. Add Product
                2. Update Stock
                3. View All Orders
                4. View Logs
                5. View Inventory History
                """);
    }
    public void CustomerOptions(){
        System.out.println("displat Custtomer");
    }
}
