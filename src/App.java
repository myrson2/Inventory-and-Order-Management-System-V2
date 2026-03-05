import java.util.Scanner;
import cli.ConsoleUI;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("==== Welcome to SmartStock System ====");
        Scanner scan = new Scanner(System.in);
        ConsoleUI consoleUI = new ConsoleUI(scan);

        consoleUI.start();
    }
}
