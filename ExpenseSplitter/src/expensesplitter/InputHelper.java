package expensesplitter;

import java.util.Scanner;

/**
 * Utility class for validated user input.
 * Practice concepts: static methods, exception handling, loops for retry logic.
 */
public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user for a positive number. Keeps asking until valid input is given.
     */
    public static double getPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("  Please enter a number greater than 0.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  That doesn't look like a number. Try again.");
            }
        }
    }

    /**
     * Prompts the user for a non-empty string.
     */
    public static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  Input cannot be empty. Try again.");
        }
    }

    /**
     * Prompts the user to choose between two options (e.g. "1" or "2").
     * Returns the chosen integer.
     */
    public static int getChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    /**
     * Returns the shared Scanner instance (useful if you need it directly).
     */
    public static Scanner getScanner() {
        return scanner;
    }
}
