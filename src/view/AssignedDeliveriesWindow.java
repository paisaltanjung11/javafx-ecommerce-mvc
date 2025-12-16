package view;

import controller.DeliveryHandler;
import controller.OrderHandler;
import controller.UserHandler;
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
import model.Delivery;
import model.OrderHeader;
import model.User;

import java.util.ArrayList;

public class AssignedDeliveriesWindow {
    
    private DeliveryHandler deliveryHandler = new DeliveryHandler();
    private OrderHandler orderHandler = new OrderHandler();
    private UserHandler userHandler = new UserHandler();
    
    public void show(Courier courier) {
        Stage stage = new Stage();
        stage.setTitle("Assigned Deliveries");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Assigned Deliveries");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<Delivery> deliveries = deliveryHandler.getDeliveriesByCourier(courier.getIdUser());
        VBox deliveriesBox = new VBox(10);

        if (deliveries.isEmpty()) {
            deliveriesBox.getChildren().add(new Label("No deliveries assigned!"));
        } else {
            for (Delivery delivery : deliveries) {
                OrderHeader order = orderHandler.getOrderById(delivery.getIdOrder());
                if (order != null) {
                    HBox deliveryRow = new HBox(10);
                    deliveryRow.setPadding(new Insets(5));
                    deliveryRow.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                    
                    User customer = userHandler.getUserById(order.getIdCustomer());
                    String customerName = customer != null ? customer.getFullName() : "Unknown";
                    
                    VBox deliveryInfo = new VBox(5);
                    deliveryInfo.getChildren().addAll(
                        new Label("Order ID: " + delivery.getIdOrder()),
                        new Label("Customer: " + customerName),
                        new Label("Status: " + delivery.getStatus()),
                        new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                    );
                    
                    deliveryRow.getChildren().add(deliveryInfo);
                    HBox.setHgrow(deliveryInfo, Priority.ALWAYS);
                    deliveriesBox.getChildren().add(deliveryRow);
                }
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(deliveriesBox);
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

