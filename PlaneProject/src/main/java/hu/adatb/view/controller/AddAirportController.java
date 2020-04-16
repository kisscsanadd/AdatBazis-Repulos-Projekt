package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.CityController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.model.Plane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class AddAirportController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Double> latitudeSpinner;

    @FXML
    Spinner<Double> longitudeSpinner;

    @FXML
    Label errorMsgName;

    @FXML
    Button saveButton;

    @FXML
    ComboBox cities;

    private List<Airport> airports;
    private List<City> citylist;

    public AddAirportController() {
    }

    private Airport airport = new Airport();

    @FXML
    private void save(ActionEvent event) {
        if (AirportController.getInstance().add(airport)) {
            try {
                App.StageDeliver("/adminView/AirportScreen.fxml", "Repülőterek", "style.css");
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        try {
            App.StageDeliver("adminView/AirportScreen.fxml", "Repülőterek", "style.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO  not sure if this is the correct way
        citylist = CityController.getInstance().getAll();
        List<String> CityNameList = citylist.stream().map(City::getName).collect(Collectors.toList());
        ObservableList<String> obsCityList = FXCollections.observableList(CityNameList);

        longitudeSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                -180.0, 180, 0, 0.5));
        latitudeSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                -90.0, 90.0, 0, 0.5));
        cities.setItems(obsCityList);

        airports = AirportController.getInstance().getAll();

        airport.nameProperty().bindBidirectional(nameField.textProperty());
        longitudeSpinner.getValueFactory().valueProperty().bindBidirectional(airport.longitudeProperty().asObject());
        latitudeSpinner.getValueFactory().valueProperty().bindBidirectional(airport.latitudeProperty().asObject());

        FieldValidator();

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var airport: airports) {
                if (newValue.equals(airport.getName())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("Ilyen név már létezik");
                saveButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void FieldValidator() {
        saveButton.disableProperty().bind(nameField.textProperty().isEmpty());
    }


}
