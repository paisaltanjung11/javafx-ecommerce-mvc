package view;

import controller.CartItemHandler;
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
import model.CartItem;
import model.Customer;
import model.Product;

import java.util.ArrayList;

public class CartItemWindow {
    
    private CartItemHandler cartHandler = new CartItemHandler();
    private ProductHandler productHandler = new ProductHandler();
    
    public void show(Customer customer, HomeView homeView) {
        Stage stage = new Stage();
        stage.setTitle("Shopping Cart");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Shopping Cart");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<CartItem> cartItems = cartHandler.getCartItems(customer.getIdUser());
        VBox cartBox = new VBox(10);

        if (cartItems.isEmpty()) {
            cartBox.getChildren().add(new Label("Cart is empty!"));
        } else {
            double total = 0;
            for (CartItem item : cartItems) {
                Product p = productHandler.getProduct(item.getIdProduct());
                if (p != null) {
                    double itemTotal = p.getPrice() * item.getCount();
                    total += itemTotal;

                    HBox itemRow = new HBox(10);
                    itemRow.setPadding(new Insets(5));
                    itemRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");

                    VBox itemInfo = new VBox(5);
                    itemInfo.getChildren().addAll(
                        new Label(p.getName()),
                        new Label("Rp " + String.format("%.0f", p.getPrice()) + " x " + item.getCount()),
                        new Label("Subtotal: Rp " + String.format("%.0f", itemTotal))
                    );

                    Button updateBtn = new Button("Update");
                    applySimpleButtonStyle(updateBtn);
                    Product selectedProduct = p;
                    int selectedQty = item.getCount();
                    updateBtn.setOnAction(e -> {
                        stage.close();
                        EditCartItemView editCartItemView = new EditCartItemView();
                        editCartItemView.show(customer, selectedProduct, selectedQty, homeView);
                    });

                    Button removeBtn = new Button("Remove");
                    applySimpleButtonStyle(removeBtn);
                    removeBtn.setOnAction(e -> {
                        cartHandler.removeCartItem(customer.getIdUser(), item.getIdProduct());
                        stage.close();
                        show(customer, homeView);
                    });

                    itemRow.getChildren().addAll(itemInfo, updateBtn, removeBtn);
                    HBox.setHgrow(itemInfo, Priority.ALWAYS);
                    cartBox.getChildren().add(itemRow);
                }
            }

            Label totalLabel = new Label("Total: Rp " + String.format("%.0f", total));
            totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            cartBox.getChildren().add(totalLabel);
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(cartBox);
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

