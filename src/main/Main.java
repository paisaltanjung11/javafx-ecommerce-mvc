package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.StageManager;
import view.LoginView;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        StageManager.getInstance().setPrimaryStage(primaryStage);
        primaryStage.setTitle("JoymarKet - Digital Marketplace");
        
        LoginView loginView = new LoginView();
        loginView.show();
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

