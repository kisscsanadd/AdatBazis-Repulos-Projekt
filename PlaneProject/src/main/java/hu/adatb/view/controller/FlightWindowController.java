package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.FlightController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Airport;
import hu.adatb.model.Flight;
import hu.adatb.model.Plane;
import hu.adatb.utils.DistanceCalculator;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FlightWindowController implements Initializable {

    @FXML
    public TableView<Flight> table = new TableView<>();

    @FXML
    private TableColumn<Flight, String> fromCol;

    @FXML
    private TableColumn<Flight, String> toCol;

    @FXML
    private TableColumn<Flight, String> whenCol;

    @FXML
    private TableColumn<Flight, String> timeCol;

    @FXML
    private TableColumn<Flight, String> withCol;

    @FXML
    private TableColumn<Flight, Integer> seatCol;

    @FXML
    private TableColumn<Flight, String> hotelCol;

    @FXML
    private TableColumn<Flight, Void> actionsCol;

    private List<Airport> airports;

    @FXML
    public void addFlight() {
        DialogFlightController.setIsAdd(true);

        try {
            App.DialogDeliver("dialog_flight.fxml", "Járat hozzáadás");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public FlightWindowController() {
    }

    public void refreshTable() {
        List<Flight> list = FlightController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Flight> list = FlightController.getInstance().getAll();
        airports = AirportController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        InitTable();
    }

    private void InitTable() {
        fromCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFromAirport().getName()));
        toCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getToAirport().getName()));
        whenCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getDateTimeInRightFormat()));
        timeCol.setCellValueFactory(
                __-> new SimpleStringProperty(__.getValue()
                        .getTravelTime(DistanceCalculator.GetLatitudeByName(airports, __.getValue().getFromAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getFromAirport().getName()),
                                DistanceCalculator.GetLatitudeByName(airports, __.getValue().getToAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getToAirport().getName()),
                                __.getValue().getPlane())));
        withCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getPlane().getName()));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                private final Button editBtn = new Button("Módosítás");
                private final Button addAlertBtn = new Button("Figyelmeztetés hozzáadása");

                {
                    deleteBtn.setOnAction(event -> {
                        Flight f = getTableView().getItems().get(getIndex());
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        //todo
                        refreshTable();
                    });

                    addAlertBtn.setOnAction(event -> {
                        Flight flight = getTableView().getItems().get(getIndex());
                        AddFlightAlertRelationController.setSelectedFlight(flight);

                        try {
                            App.DialogDeliver("add_flightAlertRel.fxml", "Figyelmeztetés hozzáadása járathoz");
                        } catch (IOException e) {
                            Utils.showWarning("Nem sikerült megnyitni a figyelmeztetés hozzáadás járathoz ablakot");
                            e.printStackTrace();
                        }

                    });
            }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox();
                        container.setSpacing(10);
                        container.getChildren().addAll(deleteBtn, editBtn, addAlertBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }
}