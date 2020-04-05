package hu.adatb;

import hu.adatb.utils.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Stage primaryStage;

    public static Stage StageDeliver() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmlView/reg_or_login.fxml"));
            var scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Repülőgép");
            primaryStage.show();
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a főablakot");
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}