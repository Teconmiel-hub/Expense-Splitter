package expensesplitter;

/**
 * Represents a person in an expense split.
 * Practice concepts: classes, constructors, getters/setters, encapsulation.
 */
public class Person {

    private String name;
    private double amountOwed;

    public Person(String name) {
        this.name = name;
        this.amountOwed = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amount) {
        this.amountOwed = amount;
    }

    @Override
    public String toString() {
        return String.format("%-15s owes: €%.2f", name, amountOwed);
    }
}
