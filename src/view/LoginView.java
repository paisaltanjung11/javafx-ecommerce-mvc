package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Admin;
import model.Courier;
import model.Customer;
import model.User;
import utils.StageManager;
import view.HomeView;

public class LoginView {
    
    private UserHandler userHandler = new UserHandler();
    
    public void show() {
        Stage stage = StageManager.getInstance().getPrimaryStage();
        
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("JoymarKet");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(250);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(250);
        applySimpleButtonStyle(loginBtn);

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(250);
        applySimpleButtonStyle(registerBtn);

        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            User user = userHandler.getUser(email, password);
            if (user == null) {
                errorLabel.setText("Invalid credentials!");
                return;
            }

            HomeView homeView = new HomeView();
            switch (user.getRole()) {
                case "CUSTOMER":
                    homeView.showCustomerMenu((Customer) user);
                    break;
                case "ADMIN":
                    homeView.showAdminMenu();
                    break;
                case "COURIER":
                    homeView.showCourierMenu((Courier) user);
                    break;
            }
        });

        registerBtn.setOnAction(e -> {
            RegisterView registerView = new RegisterView();
            registerView.show();
        });

        root.getChildren().addAll(title, emailField, passwordField, errorLabel, loginBtn, registerBtn);
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
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

