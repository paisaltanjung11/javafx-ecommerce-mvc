package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Customer;

public class UserWindow {
    
    private UserHandler userHandler = new UserHandler();
    
    public void show(Customer customer, HomeView homeView) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update your profile information");

        TextField fullNameField = new TextField(customer.getFullName());
        TextField phoneField = new TextField(customer.getPhone());
        TextField addressField = new TextField(customer.getAddress());

        VBox vbox = new VBox(10, 
            new Label("Full Name:"), fullNameField,
            new Label("Phone:"), phoneField,
            new Label("Address:"), addressField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{fullNameField.getText(), phoneField.getText(), addressField.getText()};
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            userHandler.editProfile(customer, result[0], result[1], result[2]);
            Customer updatedCustomer = (Customer) userHandler.getUserById(customer.getIdUser());
            if (updatedCustomer != null) {
                homeView.showCustomerMenu(updatedCustomer);
            }
        });
    }
}

