package view;

import controller.DeliveryHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Delivery;

public class DeliveryStatusSelectionView {
    
    private DeliveryHandler deliveryHandler = new DeliveryHandler();
    
    public void show(Delivery delivery) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Delivery Status");
        dialog.setHeaderText("Select new status for Order: " + delivery.getIdOrder() + 
                            "\nCurrent Status: " + delivery.getStatus());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label label = new Label("Select Status:");
        Button pendingBtn = new Button("Pending");
        Button inProgressBtn = new Button("In Progress");
        Button deliveredBtn = new Button("Delivered");

        pendingBtn.setPrefWidth(200);
        inProgressBtn.setPrefWidth(200);
        deliveredBtn.setPrefWidth(200);

        applySimpleButtonStyle(pendingBtn);
        applySimpleButtonStyle(inProgressBtn);
        applySimpleButtonStyle(deliveredBtn);

        pendingBtn.setOnAction(e -> {
            dialog.setResult("Pending");
            dialog.close();
        });

        inProgressBtn.setOnAction(e -> {
            dialog.setResult("In Progress");
            dialog.close();
        });

        deliveredBtn.setOnAction(e -> {
            dialog.setResult("Delivered");
            dialog.close();
        });

        vbox.getChildren().addAll(label, pendingBtn, inProgressBtn, deliveredBtn);
        dialog.getDialogPane().setContent(vbox);

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        dialog.showAndWait().ifPresent(status -> {
            if (status != null && !status.isEmpty()) {
                deliveryHandler.editDeliveryStatus(delivery.getIdOrder(), status);
                showAlert("Delivery status updated!");
            }
        });
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

