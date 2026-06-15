package expensesplitter;

import java.util.ArrayList;
import java.util.List;

public class Bill {

    public enum SplitMode { EQUAL, PERCENTAGE }

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

    public void addPerson(Person person) { people.add(person); }
    public List<Person> getPeople() { return people; }
    public String getDescription() { return description; }
    public double getTotalAmount() { return totalAmount; }
    public SplitMode getSplitMode() { return splitMode; }

    public void splitEqually() {
        if (people.isEmpty()) return;
        double share = totalAmount / people.size();
        for (Person p : people) p.setAmountOwed(share);
    }

    public String splitByPercentage(double[] percentages) {
        if (percentages.length != people.size())
            return "Number of percentages must match number of people.";

        double total = 0;
        for (double p : percentages) total += p;

        if (Math.abs(total - 100.0) > 0.01)
            return String.format("Percentages must add up to 100. You entered: %.2f", total);

        for (int i = 0; i < people.size(); i++) {
            people.get(i).setAmountOwed((percentages[i] / 100.0) * totalAmount);
        }
        return null;
    }


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