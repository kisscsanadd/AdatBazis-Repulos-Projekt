package hu.adatb.view.controller;

import hu.adatb.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegOrLoginController implements Initializable {

    @FXML
    public void registration() {
        Stage stage = App.StageDeliver();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxmlView/add_user.fxml"));
            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setTitle("Regisztráció");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void login() {
        Stage stage = App.StageDeliver();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxmlView/login_user.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("Bejelentkezés");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void back() {
        Stage stage = App.StageDeliver();
        Parent root = null;
        try {
            root = FXMLLoader.load(RegOrLoginController.class.getResource("/fxmlView/reg_or_login.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("Repülőgép");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
