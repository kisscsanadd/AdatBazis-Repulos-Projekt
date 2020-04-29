package hu.adatb.view.controller;

import hu.adatb.controller.FlightController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.City;
import hu.adatb.model.Flight;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DialogFlightController implements Initializable {

    @FXML
    ComboBox<String> fromAirport;

    @FXML
    ComboBox<String> toAirport;

    @FXML
    DatePicker dateBegin;

    @FXML
    DatePicker dateEnd;

    @FXML
    ComboBox<City> cities;

    @FXML
    ComboBox<Plane> planes;

    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    Spinner<Integer> freeSeatsSpinner;

    private Flight flight = new Flight();
    private List<Flight> flights;
    private static Flight selectedFlight;
    private static boolean isAdd;

    public DialogFlightController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (FlightController.getInstance().add(flight)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új járatot");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
       /* if (FlightController.getInstance().update(flight)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni a módosított járatot");
        }*/
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flights = FlightController.getInstance().getAll();

        if(isAdd) {
            addButton.setVisible(true);
            editButton.setVisible(false);
        } else {
            addButton.setVisible(false);
            editButton.setVisible(true);
        }

        InitTable();
        FieldValidator();
    }

    private void FieldValidator() {
    }

    private void InitTable() {
        freeSeatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                10, 1000, isAdd ? 10 : selectedFlight.getFreeSeats(), 10));
/*
        flight.nameProperty().bind(nameField.textProperty());
        flight.seatsProperty().bind(seatsSpinner.valueProperty());
        flight.speedProperty().bind(speedSpinner.valueProperty());
        flight.setId(selectedPlane.getId());*/
    }

    public static void setIsAdd(boolean isAdd) {
        DialogFlightController.isAdd = isAdd;
    }

    public static void setSelectedFlight(Flight flight) {
        selectedFlight = flight;
    }

}
