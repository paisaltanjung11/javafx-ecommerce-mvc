package view;

import controller.CartItemHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Customer;
import model.Product;

public class AddToCartView {
    
    private CartItemHandler cartHandler = new CartItemHandler();
    
    public void show(Customer customer, Product product) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add to Cart");
        dialog.setHeaderText("Quantity for " + product.getName() + "\nPrice: Rp " + String.format("%.0f", product.getPrice()) + 
                            " | Stock: " + product.getStock());

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        VBox vbox = new VBox(10, new Label("Quantity:"), quantityField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return quantityField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(qtyStr -> {
            try {
                int qty = Integer.parseInt(qtyStr);
                cartHandler.createCartItem(customer.getIdUser(), product, qty);
                showAlert("Product added to cart!");
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity!");
            }
        });
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

