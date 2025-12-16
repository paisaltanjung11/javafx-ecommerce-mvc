package view;

import controller.DeliveryHandler;
import controller.UserHandler;
import javafx.scene.control.Alert;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Courier;
import model.OrderHeader;
import model.User;

import java.util.ArrayList;

public class CourierSelectionWindow {
    
    private DeliveryHandler deliveryHandler = new DeliveryHandler();
    private UserHandler userHandler = new UserHandler();
    
    public void show(OrderHeader order) {
        Stage stage = new Stage();
        stage.setTitle("Select Courier");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Select Courier for Order: " + order.getIdOrder());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<User> couriers = userHandler.getAllCouriers();
        VBox couriersBox = new VBox(10);

        if (couriers.isEmpty()) {
            couriersBox.getChildren().add(new Label("No couriers available!"));
        } else {
            for (User courier : couriers) {
                if (courier instanceof Courier) {
                    Courier c = (Courier) courier;
                    
                    HBox courierRow = new HBox(10);
                    courierRow.setPadding(new Insets(5));
                    courierRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
                    
                    VBox courierInfo = new VBox(5);
                    courierInfo.getChildren().addAll(
                        new Label(c.getFullName()),
                        new Label("Phone: " + c.getPhone()),
                        new Label("Vehicle: " + c.getVehicleType() + " - " + c.getVehiclePlate())
                    );
                    
                    Button selectBtn = new Button("Select");
                    applySimpleButtonStyle(selectBtn);
                    selectBtn.setOnAction(e -> {
                        deliveryHandler.assignCourier(order.getIdOrder(), c.getIdUser());
                        stage.close();
                        showAlert("Courier assigned successfully!");
                    });
                    
                    courierRow.getChildren().addAll(courierInfo, selectBtn);
                    HBox.setHgrow(courierInfo, Priority.ALWAYS);
                    couriersBox.getChildren().add(courierRow);
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
        Scene scene = new Scene(root, 500, 500);
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
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

