package view;

import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;
import model.Product;

import java.util.ArrayList;

public class ProductWindow {
    
    private ProductHandler productHandler = new ProductHandler();
    
    public void show(Customer customer) {
        Stage stage = new Stage();
        stage.setTitle("Products");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Available Products");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<Product> products = productHandler.getAllProducts();
        VBox productsBox = new VBox(10);
        
        for (Product p : products) {
            HBox productRow = new HBox(10);
            productRow.setPadding(new Insets(5));
            productRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
            
            VBox productInfo = new VBox(5);
            productInfo.getChildren().addAll(
                new Label(p.getName()),
                new Label("Rp " + String.format("%.0f", p.getPrice())),
                new Label("Stock: " + p.getStock())
            );
            
            Button addBtn = new Button("Add to Cart");
            applySimpleButtonStyle(addBtn);
            addBtn.setOnAction(e -> {
                stage.close();
                AddToCartView addToCartView = new AddToCartView();
                addToCartView.show(customer, p);
            });
            
            productRow.getChildren().addAll(productInfo, addBtn);
            HBox.setHgrow(productInfo, Priority.ALWAYS);
            productsBox.getChildren().add(productRow);
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(productsBox);
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
}

