package view;

import controller.CartItemHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Customer;
import model.Product;

public class EditCartItemView {
    
    private CartItemHandler cartHandler = new CartItemHandler();
    
    public void show(Customer customer, Product product, int currentQty, HomeView homeView) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Cart");
        dialog.setHeaderText("Update quantity for " + product.getName() + 
                            "\nCurrent: " + currentQty + " | Stock: " + product.getStock());

        TextField quantityField = new TextField(String.valueOf(currentQty));
        quantityField.setPromptText("New Quantity");

        VBox vbox = new VBox(10, new Label("New Quantity:"), quantityField);
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
                cartHandler.editCartItem(customer.getIdUser(), product, qty);
                showAlert("Cart updated!");
                CartItemWindow cartItemWindow = new CartItemWindow();
                cartItemWindow.show(customer, homeView);
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity!");
                CartItemWindow cartItemWindow = new CartItemWindow();
                cartItemWindow.show(customer, homeView);
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

