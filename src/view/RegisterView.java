package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.StageManager;

public class RegisterView {
    
    private UserHandler userHandler = new UserHandler();
    
    public void show() {
        Stage stage = StageManager.getInstance().getPrimaryStage();
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("Register Account");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        fullNameField.setPrefWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setPrefWidth(300);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.setPrefWidth(300);

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setPrefWidth(300);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setWrapText(true);

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(300);
        applySimpleButtonStyle(registerBtn);

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(300);
        applySimpleButtonStyle(backBtn);

        registerBtn.setOnAction(e -> {
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            System.out.println("Registering...");
            userHandler.registerAccount(fullName, email, password, confirmPassword, phone, address);
            errorLabel.setText("Registration submitted. Check console for result.");
        });

        backBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.show();
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, fullNameField, emailField, passwordField, confirmPasswordField, 
                                phoneField, addressField, errorLabel, registerBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 600);
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

