package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.FlightController;
import hu.adatb.model.Flight;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FlightListing implements Initializable {

    @FXML
    ComboBox<String> fromAirport;

    @FXML
    ComboBox<String> toAirport;

    @FXML
    DatePicker dateBegin;

    @FXML
    DatePicker dateEnd;

    @FXML
    Button sourceButton;

    @FXML
    public TableView<Flight> table = new TableView<>();

    @FXML
    private TableColumn<Flight, String> fromCol;

    @FXML
    private TableColumn<Flight, String> toCol;

    @FXML
    private TableColumn<Flight, String> whenCol;

    @FXML
    private TableColumn<Flight, String> withCol;

    @FXML
    private TableColumn<Flight, Integer> seatCol;

    @FXML
    private TableColumn<Flight, Void> actionCol;

    private List<Flight> flights;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flights = FlightController.getInstance().getAll();
        var airports = AirportController.getInstance().getAll();

        for (var airport: airports) {
            fromAirport.getItems().addAll(airport.getName());
            toAirport.getItems().addAll(airport.getName());
        }

        table.setVisible(false);
    }

    public void source(ActionEvent actionEvent) {
        var formatter = new SimpleDateFormat("HH:mm");
        var date = new Date();
        var currentTime = LocalTime.parse(formatter.format(date));

        var filteredFlights = flights.stream()
                .filter(flight-> flight.getFromAirport().equals(fromAirport.getSelectionModel().getSelectedItem())
                        && flight.getToAirport().equals(toAirport.getSelectionModel().getSelectedItem())
                        && ((flight.getDateTime().toLocalDate()).isAfter(dateBegin.getValue())
                            || (flight.getDateTime().toLocalDate()).isEqual(dateBegin.getValue()))
                        && ((flight.getDateTime().toLocalDate()).isBefore(dateEnd.getValue())
                            || (flight.getDateTime().toLocalDate()).isEqual(dateEnd.getValue()))
        ).collect(Collectors.toList());

        System.out.println("size: " + filteredFlights.size());

        table.setItems(FXCollections.observableList(filteredFlights));

        fromCol.setCellValueFactory(new PropertyValueFactory<>("fromAirport"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("toAirport"));
        whenCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getDateTimeInRightFormat()));
        withCol.setCellValueFactory(new PropertyValueFactory<>("plane"));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));


        actionCol.setCellFactory(param ->
                new TableCell<>(){      //anonimus inner class (nem interfeszre, osztatlyea)

                    Button btn = new Button("Foglalás");

                    {
                        btn.setOnAction(event -> {
                            Flight flight = table.getItems().get(getIndex());
                            System.out.println("Flight: " + flight.getFromAirport() + " " + flight.getToAirport() +
                                    " " + flight.getDateTime() + " " + flight.getFreeSeats());
                            // TODO - open another window
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {  //mindig meghivodik ha az adott cella módosul
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                }
        );

        if (filteredFlights.size() > 0) {
            table.setVisible(true);
        }
    }
}
