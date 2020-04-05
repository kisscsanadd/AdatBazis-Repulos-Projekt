package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.CityController;
import hu.adatb.model.City;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private TableView<City> table;

    @FXML
    private TableColumn<City, String> nameCol;

    public MainWindowController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<City> cityList = CityController.getInstance().getAll();
        table.setItems(FXCollections.observableList(cityList));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    public static void back() {
        Stage stage = App.StageDeliver();
        Parent root = null;
        try {
            root = FXMLLoader.load(MainWindowController.class.getResource("/fxmlView/main_window.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("Főoldal");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
