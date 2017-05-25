// This version features lambda expression and  JavaFx Alert() 
// March 1, 2017

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OrderLambdaExpression extends Application {
    // All the UI components
    Button btnRestore, btnSave, btnCalc;
    CheckBox checkNuts, checkCherries;
    RadioButton radioVanilla, radioChocolate, radioStrawberry;
    
    public static final double CONE = 2.25;
    public static final double TAX = 6.0;
    public static final double EXTRAS = 0.50;
    public static final String DATA_FILE = "icecream.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Winter 2017 - Ice Cream Order");
        
        btnRestore = new Button("Restore");
        btnSave = new Button("Save");
        btnCalc = new Button();
        btnCalc.setText("Calculate Cost");
        
        checkNuts = new CheckBox("Nuts");
        checkCherries = new CheckBox("Cherries");
        
        radioVanilla = new RadioButton("Vanilla");
        radioChocolate = new RadioButton("Chocolate");
        radioStrawberry = new RadioButton("Strawberry");
        ToggleGroup flavorGroup = new ToggleGroup();
        flavorGroup.getToggles().addAll(radioVanilla, radioChocolate, radioStrawberry);
        radioVanilla.setSelected(true);

        HBox boxFlavor = new HBox();
        boxFlavor.setPadding(new Insets(15, 12, 15, 12));
        boxFlavor.setSpacing(10);
        boxFlavor.getChildren().addAll(radioVanilla, radioChocolate, radioStrawberry);
        TitledPane flavorPane = new TitledPane("Icecream Flavors", boxFlavor);
        flavorPane.setCollapsible(false);
        
        HBox boxExtras = new HBox();
        boxExtras.setPadding(new Insets(15, 12, 15, 12));
        boxExtras.setSpacing(10);
        boxExtras.getChildren().addAll(checkNuts, checkCherries);
        TitledPane extrasPane = new TitledPane("Toppings", boxExtras);
        extrasPane.setCollapsible(false);
        
        HBox boxProcess = new HBox();
        boxProcess.setPadding(new Insets(15, 12, 15, 12));
        boxProcess.setSpacing(10);
        boxProcess.getChildren().addAll(btnRestore, btnSave, btnCalc);
        TitledPane processPane = new TitledPane("Order", boxProcess);
        processPane.setCollapsible(false);
        
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(flavorPane, 0, 0);
        grid.add(extrasPane, 1, 0);
        grid.add(processPane, 2, 0);
        
        Scene scene = new Scene(grid, 700, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        btnCalc.setOnAction(e -> {
            double extraTopping = 0.0;
            double cost = 0.0;
            double salesTax = 0.0;
            if (checkNuts.isSelected()) {
                extraTopping += EXTRAS;
            }
            if (checkCherries.isSelected()) {
                extraTopping += EXTRAS;
            }
            cost = CONE + extraTopping;
            salesTax = (cost * TAX) / 100.0;

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(String.format("YOUR ORDER\n\nCost: %.2f\nTax: %.2f\nTotal: %.2f",
                    cost, salesTax, cost + salesTax));
            alert.showAndWait();
        });
        
        btnRestore.setOnAction(e -> {
            File file = new File(DATA_FILE);
            try {
                Scanner inputfile = new Scanner(file);
                while (inputfile.hasNext()) {
                    String line = inputfile.nextLine();
                    if (line.equalsIgnoreCase("Vanilla")) {
                        radioVanilla.setSelected(true);
                    } else if (line.equalsIgnoreCase("Chocolate")) {
                        radioChocolate.setSelected(true);
                    } else if (line.equalsIgnoreCase("Strawberry")) {
                        radioStrawberry.setSelected(true);
                    } else if (line.equalsIgnoreCase("With_Nuts")) {
                        checkNuts.setSelected(true);
                    } else if (line.equalsIgnoreCase("Without_Nuts")) {
                        checkNuts.setSelected(false);
                    } else if (line.equalsIgnoreCase("With_Cherries")) {
                        checkCherries.setSelected(true);
                    } else if (line.equalsIgnoreCase("Without_Cherries")) {
                        checkCherries.setSelected(false);
                    }
                }
                inputfile.close();
            } catch (Exception errorRestoreBtn) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Error opening data file");
                alert.showAndWait();
            }
        });
        
        btnSave.setOnAction(e -> {
            File file = new File(DATA_FILE);
            try {
                PrintWriter outputfile = new PrintWriter(file);
                if (radioVanilla.isSelected()) {
                    outputfile.println("Vanilla");
                } else if (radioChocolate.isSelected()) {
                    outputfile.println("Chocolate");
                } else if (radioStrawberry.isSelected()) {
                    outputfile.println("Strawberry");
                }
                if (checkNuts.isSelected()) {
                    outputfile.println("With_Nuts");
                } else {
                    outputfile.println("Without_Nuts");
                }
                if (checkCherries.isSelected()) {
                    outputfile.println("With_Cherries");
                } else {
                    outputfile.println("Without_Cherries");
                }
                outputfile.close();
            } catch (Exception errorSaveBtn) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Error: " + errorSaveBtn.toString());
                alert.showAndWait();
            }
        });
    }
}
