package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.*;
import hu.adatb.model.*;
import hu.adatb.model.Alert;
import hu.adatb.utils.DistanceCalculator;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OwnFlightController implements Initializable {

    @FXML
    public TableView<Booking> table;

    @FXML
    private TableColumn<Booking, String> fromCol;

    @FXML
    private TableColumn<Booking, String> toCol;

    @FXML
    private TableColumn<Booking, String> whenCol;

    @FXML
    private TableColumn<Booking, String> timeCol;

    @FXML
    private TableColumn<Booking, String> withCol;

    @FXML
    private TableColumn<Booking, String> maxSeatCol;

    @FXML
    private TableColumn<Booking, String> seatCol;

    @FXML
    private TableColumn<Booking, String> ticketCol;

    @FXML
    private TableColumn<Booking, Void> actionCol;

    public List<Booking> bookings;
    private List<Airport> airports;
    public List<Hotel> hotels;

    public static String toAirportHotelNames;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookings = BookingController.getInstance().getAll();
        airports = AirportController.getInstance().getAll();
        hotels = HotelController.getInstance().getAll();

        refreshTable();
        PopulateTable();
    }

    private void PopulateTable(){
        fromCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFlight().getFromAirport().getName()));
        toCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFlight().getToAirport().getName()));
        whenCol.setCellValueFactory(__-> new SimpleStringProperty(
                FlightController.getInstance().GetDateTimeInRightFormat(__.getValue().getFlight().getDateTime())));
        timeCol.setCellValueFactory(
                __-> new SimpleStringProperty(FlightController.getInstance()
                        .GetTravelTime(DistanceCalculator.GetLatitudeByName(airports, __.getValue().getFlight().getFromAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getFlight().getFromAirport().getName()),
                                DistanceCalculator.GetLatitudeByName(airports, __.getValue().getFlight().getToAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getFlight().getToAirport().getName()),
                                __.getValue().getFlight().getPlane())));
        withCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFlight().getPlane().getName()));
        maxSeatCol.setCellValueFactory(__ -> new SimpleStringProperty(Integer.toString(__.getValue().getFlight().getPlane().getSeats())));
        seatCol.setCellValueFactory(__ -> new SimpleStringProperty(Integer.toString(__.getValue().getFlight().getFreeSeats())));
        ticketCol.setCellValueFactory(__-> new SimpleStringProperty(GetTicketNumber(__.getValue().getFlight())));

        actionCol.setCellFactory(param ->
                new TableCell<>(){

                    final Button hotelButton = new Button("Szállodák");
                    final Button deleteButton = new Button();

                    {
                        deleteButton.setEffect(new ImageInput(new Image("pictures/delete.png")));

                        hotelButton.setOnAction(event -> {
                            var flight = table.getItems().get(getIndex()).getFlight();
                            toAirportHotelNames = FlightController.getInstance().GetHotels(hotels, flight.getToAirport().getCity().getName());
                            HotelListController.setIsOwnFlight(true);

                            try {
                                App.DialogDeliver("hotel_list.fxml","Szállodák", false);
                            } catch (IOException e) {
                                Utils.showWarning("Nem sikerült megnyitni a szállodákat kilistázó ablakot");
                            }
                        });

                        deleteButton.setOnAction(actionEvent -> {
                            Booking selectedBooking = table.getItems().get(getIndex());

                            var deletedBookings = bookings.stream().filter(b -> b.getFlight().getId() == selectedBooking.getFlight().getId()).collect(Collectors.toList());

                            var type = Utils.showConfirmation();

                            type.ifPresent(buttonType -> {
                                if(buttonType == ButtonType.YES) {
                                    var countOfTickets = 0;
                                    for (var booking : deletedBookings) {
                                        var tickets = BookingController.getInstance().delete(booking);
                                        if(tickets == -1) {
                                            try {
                                                throw new Exception("Nem sikerült lekérni a foglaláshoz tartozó jegyeket");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            countOfTickets += tickets;
                                        }
                                    }
                                    refreshTable();

                                    var flight = deletedBookings.get(0).getFlight();
                                    var newCountOfFreeSeats = flight.getFreeSeats() + countOfTickets;

                                    flight.setFreeSeats(newCountOfFreeSeats);
                                    FlightController.getInstance().update(flight);

                                    if(newCountOfFreeSeats > 10) {
                                        var relation = new FlightAlertRelation(flight, new Alert(1));
                                        FlightAlertRelationController.getInstance().delete(relation);
                                    }
                                }
                            });
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox container = new HBox();
                            container.setSpacing(30);
                            container.getChildren().addAll(hotelButton, deleteButton);
                            setGraphic(container);
                        }
                    }
                }
        );
    }

    public void refreshTable() {
        var filteredBookingsByUser = bookings.stream().filter(booking ->
                booking.getUser().getId() == LoginUserController.getUser().getId()
                        && Integer.parseInt(GetTicketNumber(booking.getFlight())) > 0)
                .collect(Collectors.toList());

        List<Integer> flightIds = new ArrayList<>();
        List<Booking> filteredBookings = new ArrayList<>();

        for (var booking: filteredBookingsByUser) {
            if(!flightIds.contains(booking.getFlight().getId())) {
                filteredBookings.add(booking);
                flightIds.add(booking.getFlight().getId());
            }
        }

        table.setItems(FXCollections.observableList(filteredBookings));
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
