package util;

import java.util.Scanner;

public class InputUtil {
     public static String readString(String prompt, Scanner scanner) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }

    public static int readInt(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please enter a valid number.");
            }
        }
    }

     public static int readMenu(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int num = Integer.parseInt(input);

                // Check if number is in valid range 0–3
                if (num >= 0 && num < 3) {
                    return num; // valid input
                } else {
                    System.out.println("Invalid choice. Please enter a number 1 and 2.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please enter a valid number.");
            }
        }
    }
}
