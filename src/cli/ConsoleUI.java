package cli;

import java.util.Scanner;
import util.InputUtil;

public class ConsoleUI {
    private static Scanner scanner = new Scanner(System.in);

    public void start(){
        int ch;
        do {
            showLoginMenu();
            ch = InputUtil.readMenu("> ", scanner);

        } while (ch != 0);

       
    }

    public void showLoginMenu(){
        System.out.println("""
                1 - Log in
                0 - Log out
                """);
    }
}
