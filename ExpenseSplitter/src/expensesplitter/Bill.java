package expensesplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bill to be split among people.
 * Practice concepts: ArrayList, loops, validation, enums.
 */
public class Bill {

    public enum SplitMode {
        EQUAL,
        PERCENTAGE
    }

    private String description;
    private double totalAmount;
    private List<Person> people;
    private SplitMode splitMode;

    public Bill(String description, double totalAmount, SplitMode splitMode) {
        this.description = description;
        this.totalAmount = totalAmount;
        this.splitMode = splitMode;
        this.people = new ArrayList<>();
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public List<Person> getPeople() {
        return people;
    }

    public String getDescription() {
        return description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public SplitMode getSplitMode() {
        return splitMode;
    }

    /**
     * Splits the bill equally among all people.
     * TODO (stretch goal): Try splitting by custom amounts instead.
     */
    public void splitEqually() {
        if (people.isEmpty()) {
            System.out.println("No people added to this bill.");
            return;
        }
        double share = totalAmount / people.size();
        for (Person person : people) {
            person.setAmountOwed(share);
        }
    }

    /**
     * Splits the bill by percentage.
     * Each person's percentage is stored in their amountOwed temporarily,
     * then converted to a euro amount.
     *
     * @param percentages array of percentages (must sum to 100)
     * @return true if split succeeded, false if percentages don't add up
     */
    public boolean splitByPercentage(double[] percentages) {
        if (percentages.length != people.size()) {
            System.out.println("Number of percentages must match number of people.");
            return false;
        }

        double total = 0;
        for (double p : percentages) {
            total += p;
        }

        // Allow a tiny rounding margin
        if (Math.abs(total - 100.0) > 0.01) {
            System.out.printf("Percentages must add up to 100. You entered: %.2f%n", total);
            return false;
        }

        for (int i = 0; i < people.size(); i++) {
            double share = (percentages[i] / 100.0) * totalAmount;
            people.get(i).setAmountOwed(share);
        }
        return true;
    }

    /** Prints the full split summary to the console. */
    public void printSummary() {
        System.out.println("\n=============================");
        System.out.println("  Bill: " + description);
        System.out.printf("  Total: €%.2f%n", totalAmount);
        System.out.println("  Split: " + splitMode);
        System.out.println("-----------------------------");
        for (Person p : people) {
            System.out.println("  " + p);
        }
        System.out.println("=============================\n");
    }
}
