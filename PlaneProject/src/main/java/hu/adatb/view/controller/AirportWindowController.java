package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.FlightController;
import hu.adatb.model.Airport;
import hu.adatb.model.Flight;
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
import java.util.List;
import java.util.ResourceBundle;

public class AirportWindowController implements Initializable {

    @FXML
    private TableView<Airport> table;

    @FXML
    private TableColumn<Airport, String> nameCol;

    @FXML
    private TableColumn<Airport, Double> longitudeCol;

    @FXML
    private TableColumn<Airport, Double> latitudeCol;

    @FXML
    private TableColumn<Airport, String> cityCol;

    @FXML
    private TableColumn<Airport, Void> actionsCol;

    public List<Flight> flights;

    @FXML
    public void addAirport() {
        try {
            App.DialogDeliver("add_airport.fxml","Repülőtér hozzáadása", "style.css");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flights = FlightController.getInstance().getAll();
        refreshTable();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        cityCol.setCellValueFactory(cityName -> new SimpleStringProperty(cityName.getValue().getCity().getName()));
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button();

            {
                deleteBtn.setEffect(new ImageInput(new Image("pictures/delete.png")));

                deleteBtn.setOnAction(event -> {
                    Airport selectedAirport = getTableView().getItems().get(getIndex());
                    DeleteAirport(selectedAirport);
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
                    container.getChildren().addAll(deleteBtn);
                    setGraphic(container);
                }
            }
        });
    }

    public void refreshTable() {
        var airports = AirportController.getInstance().getAll();
        table.setItems(FXCollections.observableList(airports));
    }

    public void DeleteAirport(Airport selectedAirport){
        var countOfFlights = (int)flights.stream().filter(flight -> flight.getFromAirport().getId() == selectedAirport.getId()
                || flight.getToAirport().getId() == selectedAirport.getId()).count();

        if(countOfFlights != 0) {
            Utils.showWarning("Ehhez a reptérhez tartozik még járat\nTörölni csak akkor lehet ha már nem lesz forglama");
        } else {
            var type = Utils.showConfirmation();

            type.ifPresent(buttonType -> {
                if(buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    AirportController.getInstance().delete(selectedAirport.getId());
                    refreshTable();
                }
            });
        }
    }
}