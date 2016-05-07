package HelloWorld;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    Stage MainWindow;
    Scene MainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("SpeechAID - Speech Recognition application");
        MainScene = new Scene(root, 600, 400);
        primaryStage.setScene(MainScene);
        String image = this.getClass().getResource("control background.jpg").toExternalForm();
        MainScene.getRoot().setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        primaryStage.show();
    }
}
