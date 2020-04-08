package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.utils.MainFxmlLoader;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminWindowController implements Initializable {

    @FXML
    private BorderPane mainPane;

    public AdminWindowController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void goToMainForAdmin() {
        try {
            System.out.println("\n"+ App.CurrentTime() + "Destroyed login page");
            Stage stage = App.StageDeliver("admin_main_window.fxml", "Főoldal");
            stage.setMaximized(true);
            System.out.println(App.CurrentTime() + "Opened admin main page after login");
        } catch (IOException e) {
            System.out.println(App.CurrentTime() + "Cannot open main page");
            Utils.showWarning("Nem sikerült megnyitni a főoldalt");
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonAction1(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened Screen1");
        Pane view = MainFxmlLoader.getPage("Screen2.fxml");
        mainPane.setCenter(view);
    }


    @FXML
    public void getPlaneScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened Screen2");
        Pane view = MainFxmlLoader.getPage("PlaneScreen.fxml");
        mainPane.setCenter(view);
    }
    @FXML
    public void buttonAction3(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened Screen2");
        Pane view = MainFxmlLoader.getPage("Screen2.fxml");
        mainPane.setCenter(view);
    }
    @FXML
    public void buttonAction4(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened Screen2");
        Pane view = MainFxmlLoader.getPage("Screen2.fxml");
        mainPane.setCenter(view);
    }
    @FXML
    public void buttonAction5(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened Screen2");
        Pane view = MainFxmlLoader.getPage("Screen2.fxml");
        mainPane.setCenter(view);
    }
}
