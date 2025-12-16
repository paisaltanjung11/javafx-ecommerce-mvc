package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Customer;

public class TopUpView {
    
    private UserHandler userHandler = new UserHandler();
    
    public void show(Customer customer, HomeView homeView) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Top Up Balance");
        dialog.setHeaderText("Enter amount to top up (minimum: 10000)");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        VBox vbox = new VBox(10, new Label("Amount:"), amountField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return amountField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(amount -> {
            userHandler.topUpBalance(customer, amount);
            Customer updatedCustomer = (Customer) userHandler.getUserById(customer.getIdUser());
            if (updatedCustomer != null) {
                homeView.showCustomerMenu(updatedCustomer);
            }
        });
    }
}

