package expensesplitter;

/**
 * Entry point for the Personal Expense Splitter app.
 *
 * Run this class to start the program.
 * Practice concepts: main method, orchestrating classes together, loops, control flow.
 *
 * STRETCH GOALS (try these once the basics work):
 *   1. Save split results to a .txt file
 *   2. Keep a history of all bills in one session
 *   3. Let the user edit a person's name before splitting
 *   4. Add a "who paid?" field and show who gets money back
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("============================");
        System.out.println("   Personal Expense Splitter");
        System.out.println("============================\n");

        boolean keepGoing = true;

        while (keepGoing) {
            // --- Step 1: Get bill details ---
            String description = InputHelper.getNonEmptyString("Bill description (e.g. Dinner): ");
            double total = InputHelper.getPositiveDouble("Total amount (€): ");

            // --- Step 2: Choose split mode ---
            System.out.println("\nHow do you want to split?");
            System.out.println("  1. Equally");
            System.out.println("  2. By percentage");
            int modeChoice = InputHelper.getChoice("Choose (1 or 2): ", 1, 2);

            Bill.SplitMode mode = (modeChoice == 1) ? Bill.SplitMode.EQUAL : Bill.SplitMode.PERCENTAGE;
            Bill bill = new Bill(description, total, mode);

            // --- Step 3: Add people ---
            System.out.println("\nAdd people (enter a blank name when done, minimum 2):");
            while (true) {
                System.out.print("  Person's name (or press Enter to finish): ");
                String name = InputHelper.getScanner().nextLine().trim();

                if (name.isEmpty()) {
                    if (bill.getPeople().size() < 2) {
                        System.out.println("  Please add at least 2 people.");
                    } else {
                        break;
                    }
                } else {
                    bill.addPerson(new Person(name));
                    System.out.println("  Added: " + name);
                }
            }

            // --- Step 4: Calculate split ---
            if (mode == Bill.SplitMode.EQUAL) {
                bill.splitEqually();
            } else {
                // Collect a percentage for each person
                double[] percentages = new double[bill.getPeople().size()];
                System.out.println("\nEnter percentage for each person (must total 100):");

                boolean valid = false;
                while (!valid) {
                    for (int i = 0; i < bill.getPeople().size(); i++) {
                        String prompt = "  " + bill.getPeople().get(i).getName() + "'s %: ";
                        percentages[i] = InputHelper.getPositiveDouble(prompt);
                    }
                    valid = bill.splitByPercentage(percentages);
                    if (!valid) {
                        System.out.println("  Let's try the percentages again.\n");
                    }
                }
            }

            // --- Step 5: Show results ---
            bill.printSummary();

            // --- Step 6: Go again? ---
            System.out.println("Split another bill?");
            System.out.println("  1. Yes");
            System.out.println("  2. No, exit");
            int again = InputHelper.getChoice("Choose (1 or 2): ", 1, 2);
            keepGoing = (again == 1);
            System.out.println();
        }

        System.out.println("Goodbye! Happy splitting 💸");
    }
}
