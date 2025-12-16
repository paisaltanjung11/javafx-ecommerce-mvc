package view;

import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Product;

public class EditStockQuantityView {
    
    private ProductHandler productHandler = new ProductHandler();
    
    public void show(Product product) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Product Stock");
        dialog.setHeaderText("Update stock for " + product.getName() + 
                            "\nCurrent Stock: " + product.getStock());

        TextField stockField = new TextField(String.valueOf(product.getStock()));
        stockField.setPromptText("New Stock");

        VBox vbox = new VBox(10, new Label("New Stock:"), stockField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return stockField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(stockStr -> {
            try {
                int stock = Integer.parseInt(stockStr);
                productHandler.editProductStock(product.getIdProduct(), stock);
                showAlert("Stock updated!");
            } catch (NumberFormatException e) {
                showAlert("Invalid stock value!");
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

