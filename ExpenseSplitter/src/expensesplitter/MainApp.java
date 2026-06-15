package expensesplitter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private final ObservableList<String> personNames = FXCollections.observableArrayList();
    private final List<TextField> percentageFields = new ArrayList<>();
    private VBox peopleListBox;
    private VBox percentageBox;
    private VBox resultsBox;
    private Label errorLabel;
    private ToggleButton equalBtn;
    private ToggleButton percentBtn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("💸 Expense Splitter");
        stage.setMinWidth(480);
        stage.setMinHeight(640);

        VBox root = buildUI();
        root.setPadding(new Insets(24));

        Scene scene = new Scene(root, 480, 720);
        String css = getClass().getResource("/resources/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    private VBox buildUI() {
        VBox card = new VBox(20);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(28));

        Label title = new Label("🌸 Expense Splitter");
        title.getStyleClass().add("title-label");
        Label subtitle = new Label("Split bills fairly with your besties ✨");
        subtitle.getStyleClass().add("subtitle-label");

        Label billSection = new Label("BILL DETAILS");
        billSection.getStyleClass().add("section-label");

        TextField descField = new TextField();
        descField.setPromptText("What's this bill for? (e.g. Dinner 🍽️)");

        TextField amountField = new TextField();
        amountField.setPromptText("Total amount (€)");

        Label splitSection = new Label("SPLIT MODE");
        splitSection.getStyleClass().add("section-label");

        equalBtn = new ToggleButton("Equal Split 🎀");
        percentBtn = new ToggleButton("By Percentage 🎀");
        equalBtn.getStyleClass().add("toggle-btn");
        percentBtn.getStyleClass().add("toggle-btn");

        ToggleGroup toggleGroup = new ToggleGroup();
        equalBtn.setToggleGroup(toggleGroup);
        percentBtn.setToggleGroup(toggleGroup);
        equalBtn.setSelected(true);

        HBox toggleBox = new HBox(equalBtn, percentBtn);

        toggleGroup.selectedToggleProperty().addListener((obs, old, newVal) -> {
            if (newVal == null) { equalBtn.setSelected(true); return; }
            refreshPercentageBox();
        });

        Label peopleSection = new Label("PEOPLE");
        peopleSection.getStyleClass().add("section-label");

        HBox addPersonRow = new HBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Friend's name 💕");
        HBox.setHgrow(nameField, Priority.ALWAYS);

        Button addPersonBtn = new Button("+ Add");
        addPersonBtn.getStyleClass().add("btn-secondary");
        addPersonBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                personNames.add(name);
                nameField.clear();
                refreshPeopleList();
                refreshPercentageBox();
            }
        });

        nameField.setOnAction(e -> addPersonBtn.fire());
        addPersonRow.getChildren().addAll(nameField, addPersonBtn);

        peopleListBox = new VBox(8);
        percentageBox = new VBox(8);

        errorLabel = new Label("");
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        Button calculateBtn = new Button("Calculate Split 🌷");
        calculateBtn.getStyleClass().add("btn-primary");
        calculateBtn.setMaxWidth(Double.MAX_VALUE);
        calculateBtn.setOnAction(e -> calculate(descField, amountField));

        resultsBox = new VBox(10);
        resultsBox.setVisible(false);

        Button resetBtn = new Button("Start Over 🔄");
        resetBtn.getStyleClass().add("btn-secondary");
        resetBtn.setMaxWidth(Double.MAX_VALUE);
        resetBtn.setOnAction(e -> reset(descField, amountField));

        card.getChildren().addAll(
            title, subtitle,
            new Separator(),
            billSection, descField, amountField,
            splitSection, toggleBox,
            peopleSection, addPersonRow, peopleListBox,
            percentageBox,
            errorLabel,
            calculateBtn,
            resultsBox,
            resetBtn
        );

        VBox outer = new VBox(card);
        VBox.setVgrow(card, Priority.ALWAYS);
        return outer;
    }

    private void refreshPeopleList() {
        peopleListBox.getChildren().clear();
        for (int i = 0; i < personNames.size(); i++) {
            final int index = i;
            HBox row = new HBox(10);
            row.getStyleClass().add("person-row");
            row.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label("🌸 " + personNames.get(i));
            nameLabel.getStyleClass().add("result-name");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);

            Button removeBtn = new Button("✕");
            removeBtn.getStyleClass().add("btn-danger");
            removeBtn.setOnAction(e -> {
                personNames.remove(index);
                refreshPeopleList();
                refreshPercentageBox();
            });

            row.getChildren().addAll(nameLabel, removeBtn);
            peopleListBox.getChildren().add(row);
        }
    }

    private void refreshPercentageBox() {
        percentageBox.getChildren().clear();
        percentageFields.clear();

        if (percentBtn.isSelected() && !personNames.isEmpty()) {
            Label header = new Label("PERCENTAGES (must total 100)");
            header.getStyleClass().add("section-label");
            percentageBox.getChildren().add(header);

            for (String name : personNames) {
                HBox row = new HBox(10);
                row.setAlignment(Pos.CENTER_LEFT);

                Label lbl = new Label(name);
                lbl.getStyleClass().add("result-name");
                lbl.setMinWidth(120);

                TextField pctField = new TextField();
                pctField.setPromptText("%");
                pctField.setMaxWidth(80);
                percentageFields.add(pctField);

                row.getChildren().addAll(lbl, pctField);
                percentageBox.getChildren().add(row);
            }
        }
    }

    private void calculate(TextField descField, TextField amountField) {
        errorLabel.setVisible(false);
        resultsBox.setVisible(false);

        String desc = descField.getText().trim();
        if (desc.isEmpty()) { showError("Please enter a bill description 🌸"); return; }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("Please enter a valid amount greater than 0 💕");
            return;
        }

        if (personNames.size() < 2) {
            showError("Please add at least 2 friends 🌷");
            return;
        }

        Bill.SplitMode mode = equalBtn.isSelected() ? Bill.SplitMode.EQUAL : Bill.SplitMode.PERCENTAGE;
        Bill bill = new Bill(desc, amount, mode);
        for (String name : personNames) bill.addPerson(new Person(name));

        if (mode == Bill.SplitMode.EQUAL) {
            bill.splitEqually();
        } else {
            double[] percentages = new double[percentageFields.size()];
            try {
                for (int i = 0; i < percentageFields.size(); i++) {
                    percentages[i] = Double.parseDouble(percentageFields.get(i).getText().trim());
                }
            } catch (NumberFormatException e) {
                showError("Please enter valid percentages for everyone 💕");
                return;
            }
            String error = bill.splitByPercentage(percentages);
            if (error != null) { showError(error); return; }
        }

        showResults(bill);
    }

    private void showResults(Bill bill) {
        resultsBox.getChildren().clear();

        Label header = new Label("RESULTS ✨");
        header.getStyleClass().add("section-label");

        VBox resultCard = new VBox(10);
        resultCard.getStyleClass().add("result-card");

        Label totalLabel = new Label(String.format("Total: €%.2f  •  %s",
            bill.getTotalAmount(), bill.getDescription()));
        totalLabel.getStyleClass().add("result-total");
        resultCard.getChildren().add(totalLabel);
        resultCard.getChildren().add(new Separator());

        for (Person p : bill.getPeople()) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label("🌸 " + p.getName());
            nameLabel.getStyleClass().add("result-name");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);

            Label amountLabel = new Label(String.format("€%.2f", p.getAmountOwed()));
            amountLabel.getStyleClass().add("result-amount");

            row.getChildren().addAll(nameLabel, amountLabel);
            resultCard.getChildren().add(row);
        }

        resultsBox.getChildren().addAll(header, resultCard);
        resultsBox.setVisible(true);
    }

    private void showError(String message) {
        errorLabel.setText("⚠ " + message);
        errorLabel.setVisible(true);
    }

    private void reset(TextField descField, TextField amountField) {
        descField.clear();
        amountField.clear();
        personNames.clear();
        percentageFields.clear();
        peopleListBox.getChildren().clear();
        percentageBox.getChildren().clear();
        resultsBox.setVisible(false);
        errorLabel.setVisible(false);
        equalBtn.setSelected(true);
    }
}