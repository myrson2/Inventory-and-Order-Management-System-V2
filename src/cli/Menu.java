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
        System.out.println("displat Admin");
    }
    public void CustomerOptions(){
        System.out.println("displat Custtomer");
    }
}
