package view;

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
import model.OrderHeader;
import model.User;

import java.util.ArrayList;

public class AllOrdersWindow {
    
    private OrderHandler orderHandler = new OrderHandler();
    private UserHandler userHandler = new UserHandler();
    
    public void show() {
        Stage stage = new Stage();
        stage.setTitle("All Orders");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("All Orders");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<OrderHeader> orders = orderHandler.getAllOrders();
        VBox ordersBox = new VBox(10);

        if (orders.isEmpty()) {
            ordersBox.getChildren().add(new Label("No orders found!"));
        } else {
            for (OrderHeader order : orders) {
                HBox orderRow = new HBox(10);
                orderRow.setPadding(new Insets(5));
                orderRow.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                
                User customer = userHandler.getUserById(order.getIdCustomer());
                String customerName = customer != null ? customer.getFullName() : "Unknown";
                
                VBox orderInfo = new VBox(5);
                orderInfo.getChildren().addAll(
                    new Label("Order ID: " + order.getIdOrder()),
                    new Label("Customer: " + customerName),
                    new Label("Date: " + order.getOrderedAt()),
                    new Label("Status: " + order.getStatus()),
                    new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                );
                
                orderRow.getChildren().add(orderInfo);
                HBox.setHgrow(orderInfo, Priority.ALWAYS);
                ordersBox.getChildren().add(orderRow);
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ordersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 700, 600);
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

