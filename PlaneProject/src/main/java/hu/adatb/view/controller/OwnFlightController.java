package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.BookingController;
import hu.adatb.controller.FlightController;
import hu.adatb.controller.TicketController;
import hu.adatb.model.*;
import hu.adatb.utils.DistanceCalculator;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OwnFlightController implements Initializable {

    @FXML
    public TableView<Flight> table;

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
    private TableColumn<Flight, String> maxSeatCol;

    @FXML
    private TableColumn<Flight, String> seatCol;

    @FXML
    private TableColumn<Flight, String> ticketCol;

    @FXML
    private TableColumn<Flight, Void> actionCol;

    private List<Flight> flights;
    private List<Booking> bookings;
    private List<Airport> airports;

    public static String toAirportHotelNames;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookings = BookingController.getInstance().getAll();
        flights = FlightController.getInstance().getAll();
        airports = AirportController.getInstance().getAll();

        var filteredBookingsByUser = bookings.stream().filter(booking -> booking.getUser().getId() == LoginUserController.getUser().getId()).collect(Collectors.toList());
        List<Flight> filteredFlights = new ArrayList<>();

        ArrayList<Integer> flightIds = new ArrayList<>();

        for (var booking: filteredBookingsByUser) {
            if(!flightIds.contains(booking.getFlight().getId())) {
                filteredFlights.add(flights.stream().filter(flight -> flight.getId() == booking.getFlight().getId()).collect(Collectors.toList()).get(0));
                flightIds.add(booking.getFlight().getId());
            }

        }

        table.setItems(FXCollections.observableList(filteredFlights));

        PopulateTable();
    }

    private void PopulateTable(){
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
        maxSeatCol.setCellValueFactory(__ -> new SimpleStringProperty(Integer.toString(__.getValue().getPlane().getSeats())));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        ticketCol.setCellValueFactory(__-> new SimpleStringProperty(GetTicketNumber(__.getValue())));

        actionCol.setCellFactory(param ->
                new TableCell<>(){

                    final Button hotelButton = new Button("Szállodák");

                    {
                        hotelButton.setOnAction(event -> {
                            var flight = table.getItems().get(getIndex());
                            toAirportHotelNames = flight.getHotels(flight.getToAirport().getCity().getName());
                            HotelListController.setIsOwnFlight(true);

                            try {
                                App.DialogDeliver("hotel_list.fxml","Szállodák", false);
                            } catch (IOException e) {
                                Utils.showWarning("Nem sikerült megnyitni a szállodákat kilistázó ablakot");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hotelButton);
                        }
                    }
                }
        );
    }

    private String GetTicketNumber(Flight flight) {
        var tickets = TicketController.getInstance().getAll();
        List<Ticket> filteredTickets = new ArrayList<>();
        var filteredBookings = bookings.stream().filter(booking -> booking.getFlight().getId() == flight.getId()).collect(Collectors.toList());

        for (var booking: filteredBookings) {
            filteredTickets.addAll(tickets.stream().filter(ticket -> ticket.getBooking().getId() == booking.getId()).collect(Collectors.toList()));
        }

        return Integer.toString(filteredTickets.size());
    }

    public static String getToAirportHotelNames() {
        return toAirportHotelNames;
    }
}
