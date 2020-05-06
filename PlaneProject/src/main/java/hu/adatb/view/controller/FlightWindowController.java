package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.*;
import hu.adatb.model.*;
import hu.adatb.utils.DistanceCalculator;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private List<Booking> bookings;
    private List<FlightAlertRelation> relations;

    @FXML
    public void addFlight() {
        try {
            App.DialogDeliver("add_flight.fxml", "Járat hozzáadás");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public void refreshTable() {
        List<Flight> list = FlightController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var flights = FlightController.getInstance().getAll();
        airports = AirportController.getInstance().getAll();
        bookings = BookingController.getInstance().getAll();
        relations = FlightAlertRelationController.getInstance().getAll();

        table.setItems(FXCollections.observableList(flights));
        InitTable();
    }

    private void InitTable() {
        fromCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFromAirport().getName()));
        toCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getToAirport().getName()));
        whenCol.setCellValueFactory(__-> new SimpleStringProperty(
                FlightController.getInstance().GetDateTimeInRightFormat(__.getValue().getDateTime())));
        timeCol.setCellValueFactory(
                __-> new SimpleStringProperty(FlightController.getInstance()
                        .GetTravelTime(DistanceCalculator.GetLatitudeByName(airports, __.getValue().getFromAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getFromAirport().getName()),
                                DistanceCalculator.GetLatitudeByName(airports, __.getValue().getToAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getToAirport().getName()),
                                __.getValue().getPlane())));
        withCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getPlane().getName()));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button();
            private final Button editBtn = new Button();

            {
                deleteBtn.setEffect(new ImageInput(new Image("pictures/delete.png")));
                editBtn.setEffect(new ImageInput(new Image("pictures/edit.png")));

                deleteBtn.setOnAction(event -> {
                    Flight flight = getTableView().getItems().get(getIndex());

                    var type = Utils.showConfirmation("Törlődni fog minden foglalás és figyelmezetés is!");

                    type.ifPresent(buttonType -> {
                        if(buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            DeleteFlight(flight);
                        }
                    });
                });

                editBtn.setOnAction(event -> {
                    Flight flight = getTableView().getItems().get(getIndex());
                    EditFlightController.setSelectedFlight(flight);

                    try {
                        App.DialogDeliver("edit_flight.fxml", "Járat módosítás");
                    } catch (IOException e) {
                        Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
                    }

                    refreshTable();
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
                    container.getChildren().addAll(editBtn, deleteBtn);
                    setGraphic(container);
                }
            }
        });
    }

    public void DeleteFlight(Flight deletedFlight) {
        var deletedBookings = bookings.stream().filter(booking -> booking.getFlight().getId() == deletedFlight.getId())
                .collect(Collectors.toList());

        var deletedRelations = relations.stream().filter(relation -> relation.getFlight().getId() == deletedFlight.getId())
                .collect(Collectors.toList());

        boolean canDeleteFlight = true;

        for(var deletedRelation : deletedRelations) {
            if(!FlightAlertRelationController.getInstance().delete(deletedRelation)) {
                Utils.showWarning("Nem sikerült törölni egy figyelmeztetést ami a járathoz tartozik");
                canDeleteFlight = false;
            };
        }

        for(var deletedBooking : deletedBookings) {
            if(!BookingController.getInstance().delete(deletedBooking)) {
                Utils.showWarning("Nem sikerült törölni egy foglalást ami a járathoz tartozik");
                canDeleteFlight = false;
            }
        }

        if(canDeleteFlight) {
            if(FlightController.getInstance().delete(deletedFlight.getId())) {
                refreshTable();
            }
        }

    }
}