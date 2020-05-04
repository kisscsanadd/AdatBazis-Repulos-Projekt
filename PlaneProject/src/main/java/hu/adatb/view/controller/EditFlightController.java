package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.BookingController;
import hu.adatb.controller.FlightAlertRelationController;
import hu.adatb.controller.FlightController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Alert;
import hu.adatb.model.Flight;
import hu.adatb.model.FlightAlertRelation;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditFlightController implements Initializable {

    @FXML
    ComboBox<Plane> planes;

    @FXML
    DatePicker dateBegin;

    @FXML
    Spinner<Integer> hourSpinner;

    @FXML
    Spinner<Integer> minuteSpinner;

    @FXML
    Button editButton;

    private Flight flight = new Flight();
    private static Flight selectedFlight;
    private static List<Alert> selectedAlerts;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Plane> planesList = PlaneController.getInstance().getAll();
        ObservableList<Plane> obsPlaneList = FXCollections.observableList(planesList);
        planes.getItems().addAll(obsPlaneList);

        Callback<ListView<Plane>, ListCell<Plane>> factoryPlane = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Plane item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        planes.setCellFactory(factoryPlane);
        planes.setButtonCell(factoryPlane.call(null));

        planes.setPromptText(selectedFlight.getPlane().getName());

        hourSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0, 24,  selectedFlight.getDateTime().toLocalTime().getHour()));

        minuteSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0, 59, selectedFlight.getDateTime().toLocalTime().getMinute(), 15));

        dateBegin.setValue(selectedFlight.getDateTime().toLocalDate());
    }

    @FXML
    public void edit(ActionEvent event) {
        var time = (FlightController.getInstance().SetTimeFormat(hourSpinner.getValue())) + ":"
                + (FlightController.getInstance().SetTimeFormat(minuteSpinner.getValue()));

        LocalDateTime dateTime = LocalDateTime.of(dateBegin.getValue(), LocalTime.parse(time));

        flight.setDateTime(dateTime);
        flight.setFromAirport(selectedFlight.getFromAirport());
        flight.setToAirport(selectedFlight.getToAirport());
        flight.setVogue(selectedFlight.getVogue());
        flight.setId(selectedFlight.getId());

        if(planes.getValue() == null) {
            flight.setPlane(selectedFlight.getPlane());
            flight.setFreeSeats(selectedFlight.getFreeSeats());
        } else {
            var bookings = BookingController.getInstance().getAll();

            var countOfTickets = FlightController.getInstance().GetTicketNumber(bookings, selectedFlight).size();

            flight.setPlane(planes.getValue());
            flight.setFreeSeats(planes.getValue().getSeats() - countOfTickets);
        }

       if (FlightController.getInstance().update(flight)) {
           if(selectedAlerts != null) {
               for(var selectedAlert : selectedAlerts) {
                   FlightAlertRelationController.getInstance().add(new FlightAlertRelation(flight, selectedAlert));
               }
           }

           Utils.showInformation("Sikeres módosítás");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addAlert(ActionEvent actionEvent) {
        AddFlightAlertRelationController.setSelectedFlight(selectedFlight);
        var stage = new Stage();
        Parent root = null;

        try {
            root = FXMLLoader.load(App.class.getResource("/fxmlView/adminView/add_flightAlertRel.fxml"));
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a figyelmeztetés hozzáadás járathoz ablakot");
            e.printStackTrace();
        }

        var scene = new Scene(root);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/pictures/icon.jpg")));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Figyelmeztetés hozzáadása járathoz");
        stage.showAndWait();
    }

    public static void setSelectedFlight(Flight flight) {
        selectedFlight = flight;
    }

    public static void setSelectedAlerts(List<Alert> selectedAlerts) {
        EditFlightController.selectedAlerts = selectedAlerts;
    }
}
