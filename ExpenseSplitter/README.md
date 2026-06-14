# Personal Expense Splitter — Java Practice Project

## Project Structure

```
ExpenseSplitter/
└── src/
    └── expensesplitter/
        ├── Main.java         ← Start here. Runs the app.
        ├── Bill.java         ← Represents a bill and handles splitting logic.
        ├── Person.java       ← Represents a person and what they owe.
        └── InputHelper.java  ← Reusable input validation utilities.
```

## How to Run in VS Code

1. Install the **Extension Pack for Java** from the VS Code marketplace.
2. Open the `ExpenseSplitter` folder in VS Code.
3. Open `Main.java` and click **Run** above the `main` method, or press `F5`.

## How to Run with javac (terminal)

```bash
cd ExpenseSplitter/src
javac expensesplitter/*.java
java expensesplitter.Main
```

## What You'll Practice

| Concept              | Where to find it          |
|----------------------|---------------------------|
| Classes & objects    | `Person.java`, `Bill.java`|
| Constructors         | All classes               |
| Getters & setters    | `Person.java`             |
| ArrayList            | `Bill.java`               |
| Loops (`for`, `while`)| `Bill.java`, `Main.java` |
| Exception handling   | `InputHelper.java`        |
| Static methods       | `InputHelper.java`        |
| Enums                | `Bill.SplitMode`          |
| String formatting    | `Person.toString()`       |

## Stretch Goals (try these next!)

- [ ] **Save to file** — Write the split summary to a `.txt` file using `FileWriter`
- [ ] **Session history** — Keep a `List<Bill>` and print all bills at the end
- [ ] **Who paid?** — Add a "payer" field and show net amounts (who owes who)
- [ ] **GUI** — Rebuild the UI using Java Swing or JavaFX
- [ ] **Unit tests** — Write JUnit tests for `splitEqually()` and `splitByPercentage()`
