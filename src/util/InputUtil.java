package util;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InputUtil {
    private static final Set<Integer> userUsedIds = new HashSet<>();

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

    public static String userIDenerateID(){
        int currentId = 0;
        final int MAX_ID = 1000;

        if (currentId > MAX_ID) {
            throw new IllegalStateException("Maximum ID limit reached.");
        }

        while (userUsedIds.contains(currentId)) {
            currentId++;
        }

        int id = currentId;
        userUsedIds.add(id);

        String userId = String.valueOf(id);
        currentId++;

        return userId;
    }
}
