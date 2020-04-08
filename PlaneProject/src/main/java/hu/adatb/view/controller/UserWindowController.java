package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.FlightController;
import hu.adatb.model.Flight;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserWindowController implements Initializable {

    private List<Flight> flights;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ComboBox fromCity;

    @FXML
    private ComboBox toCity;

    @FXML
    private Button search;

    public UserWindowController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flights = FlightController.getInstance().getAll();
        var airports = AirportController.getInstance().getAll();

        for (var airport: airports) {
            fromCity.getItems().addAll(airport.getName());
            toCity.getItems().addAll(airport.getName());
        }
    }

    public static void goToMainForUser() {
        try {
            System.out.println("\n"+ App.CurrentTime() + "Destroyed login page");
            Stage stage = App.StageDeliver("user_main_window.fxml", "Főoldal");
            stage.setMaximized(true);
            System.out.println(App.CurrentTime() + "Opened user main page after login");
        } catch (IOException e) {
            System.out.println(App.CurrentTime() + "Cannot open main page");
            Utils.showWarning("Nem sikerült megnyitni a főoldalt");
            e.printStackTrace();
        }
    }

    public void search(ActionEvent actionEvent) {
        for (var flight: flights
             ) {
            if (fromCity.getSelectionModel().getSelectedItem().equals(flight.getFromAirport())
                    && toCity.getSelectionModel().getSelectedItem().equals(flight.getToAirport())) {
                System.out.println("Mikor: " + flight.getDate());
            } else {
                System.out.println("Nincs ilyen");
            }

        }
    }
}
