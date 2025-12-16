package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Courier;
import model.User;

import java.util.ArrayList;

public class AllCouriersWindow {
    
    private UserHandler userHandler = new UserHandler();
    
    public void show() {
        Stage stage = new Stage();
        stage.setTitle("All Couriers");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("All Couriers");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<User> couriers = userHandler.getAllCouriers();
        VBox couriersBox = new VBox(10);

        if (couriers.isEmpty()) {
            couriersBox.getChildren().add(new Label("No couriers found!"));
        } else {
            for (User courier : couriers) {
                if (courier instanceof Courier) {
                    Courier c = (Courier) courier;
                    VBox courierBox = new VBox(5);
                    courierBox.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                    
                    courierBox.getChildren().addAll(
                        new Label("ID: " + c.getIdUser()),
                        new Label("Name: " + c.getFullName()),
                        new Label("Email: " + c.getEmail()),
                        new Label("Phone: " + c.getPhone()),
                        new Label("Vehicle: " + c.getVehicleType() + " - " + c.getVehiclePlate())
                    );
                    
                    couriersBox.getChildren().add(courierBox);
                }
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(couriersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }
    
    private void applySimpleButtonStyle(Button button) {
        button.setStyle("-fx-background-color: white; " +
                       "-fx-text-fill: black; " +
                       "-fx-border-color: #ccc; " +
                       "-fx-border-width: 1; " +
                       "-fx-font-size: 14px; " +
                       "-fx-padding: 8px 16px; " +
                       "-fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f5f5f5; " +
                                                      "-fx-text-fill: black; " +
                                                      "-fx-border-color: #999; " +
                                                      "-fx-border-width: 1; " +
                                                      "-fx-font-size: 14px; " +
                                                      "-fx-padding: 8px 16px; " +
                                                      "-fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; " +
                                                     "-fx-text-fill: black; " +
                                                     "-fx-border-color: #ccc; " +
                                                     "-fx-border-width: 1; " +
                                                     "-fx-font-size: 14px; " +
                                                     "-fx-padding: 8px 16px; " +
                                                     "-fx-cursor: hand;"));
    }
}

