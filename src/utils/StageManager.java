package utils;

import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class StageManager {
    
    private static StageManager instance;
    private Stage primaryStage;
    private Map<String, Stage> stages;
    
    private StageManager() {
        stages = new HashMap<>();
    }
    
    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public void showStage(String stageName, Stage stage) {
        Stage existingStage = stages.get(stageName);
        if (existingStage != null && existingStage.isShowing()) {
            existingStage.close();
        }
        stages.put(stageName, stage);
        stage.show();
    }
    
    public void closeStage(String stageName) {
        Stage stage = stages.get(stageName);
        if (stage != null && stage.isShowing()) {
            stage.close();
            stages.remove(stageName);
        }
    }
    
    public void closeAllStages() {
        for (Stage stage : stages.values()) {
            if (stage != null && stage.isShowing()) {
                stage.close();
            }
        }
        stages.clear();
    }
}

