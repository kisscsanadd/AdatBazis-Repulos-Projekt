package hu.adatb.view.controller;

import hu.adatb.controller.*;
import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.model.Flight;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;


public class AddFlightController implements Initializable {

    @FXML
    ComboBox<Airport> fromAirport;

    @FXML
    ComboBox<Airport> toAirport;

    @FXML
    ComboBox<Plane> planes;

    @FXML
    DatePicker dateBegin;

    @FXML
    Spinner<Integer> hourSpinner;

    @FXML
    Spinner<Integer> minuteSpinner;

    @FXML
    Button addButton;

    private Flight flight = new Flight();

    public AddFlightController() {
    }

    @FXML
    private void save(ActionEvent event) {
        var time = (FlightController.getInstance().SetTimeFormat(hourSpinner.getValue())) + ":"
                + (FlightController.getInstance().SetTimeFormat(minuteSpinner.getValue()));

        LocalDateTime dateTime = LocalDateTime.of(dateBegin.getValue(), LocalTime.parse(time));

        flight.setFromAirport(fromAirport.getSelectionModel().getSelectedItem());
        flight.setToAirport(toAirport.getSelectionModel().getSelectedItem());
        flight.setPlane(planes.getSelectionModel().getSelectedItem());
        flight.setDateTime(dateTime);
        flight.setFreeSeats(planes.getSelectionModel().getSelectedItem().getSeats());

        if (FlightController.getInstance().add(flight)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új járatot");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Plane> planesList = PlaneController.getInstance().getAll();
        ObservableList<Plane> obsPlaneList = FXCollections.observableList(planesList);

        List<Airport> airportList = AirportController.getInstance().getAll();
        ObservableList<Airport> obsAirportList = FXCollections.observableList(airportList);

        fromAirport.getItems().addAll(obsAirportList);
        toAirport.getItems().addAll(obsAirportList);
        planes.getItems().addAll(obsPlaneList);

        Callback<ListView<Plane>, ListCell<Plane>> factoryPlane = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Plane item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        Callback<ListView<Airport>, ListCell<Airport>> factoryAirport = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Airport item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        fromAirport.setCellFactory(factoryAirport);
        fromAirport.setButtonCell(factoryAirport.call(null));
        toAirport.setCellFactory(factoryAirport);
        toAirport.setButtonCell(factoryAirport.call(null));

        planes.setCellFactory(factoryPlane);
        planes.setButtonCell(factoryPlane.call(null));

        hourSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24));

        minuteSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60));
    }

    private void FieldValidator() {
    }
}
