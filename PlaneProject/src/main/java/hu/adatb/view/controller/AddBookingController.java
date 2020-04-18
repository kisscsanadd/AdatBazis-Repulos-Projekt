package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.*;
import hu.adatb.model.*;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookingController implements Initializable {

    @FXML
    ComboBox<Payment> paymentComboBox;

    @FXML
    ComboBox<Category> categoryComboBox;

    @FXML
    ComboBox<TravelClass> travelClassComboBox;

    @FXML
    Spinner<Integer> ticketSpinner;

    @FXML
    Button ticketSelector;

    @FXML
    Button bookingButton;

    @FXML
    CheckBox sameTickets;

    private Booking booking = new Booking();
    private static int countOfTicket = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var payments = PaymentController.getInstance().getAll();
        ObservableList<Payment> obsPaymentList = FXCollections.observableList(payments);

        paymentComboBox.getItems().addAll(obsPaymentList);

        Callback<ListView<Payment>, ListCell<Payment>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Payment item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        paymentComboBox.setCellFactory(factory);
        paymentComboBox.setButtonCell(factory.call(null));

        ticketSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10));

        ticketSelector.disableProperty().addListener((observableValue, oldValue, newValue) -> {
            paymentComboBox.valueProperty().isNull();
        });

        // ----------------------

        var categories = CategoryController.getInstance().getAll();
        var travelClasses = TravelClassController.getInstance().getAll();

        ObservableList<Category> obsCategoryList = FXCollections.observableList(categories);
        ObservableList<TravelClass> obsTravelClassList = FXCollections.observableList(travelClasses);

        categoryComboBox.getItems().addAll(obsCategoryList);
        Callback<ListView<Category>, ListCell<Category>> factoryr = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        categoryComboBox.setCellFactory(factoryr);
        categoryComboBox.setButtonCell(factoryr.call(null));

        travelClassComboBox.getItems().addAll(obsTravelClassList);
        Callback<ListView<TravelClass>, ListCell<TravelClass>> factoryy = lv -> new ListCell<>() {
            @Override
            protected void updateItem(TravelClass item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        travelClassComboBox.setCellFactory(factoryy);
        travelClassComboBox.setButtonCell(factoryy.call(null));

        // -------------------------

        CheckCheckBox();
    }

    private void PopulateBooking() {
        booking.setUser(LoginUserController.getUser());
        booking.setPayment(paymentComboBox.getSelectionModel().getSelectedItem());
        booking.setFlight(FlightListing.getBookedFlight());
    }

    private void CheckCheckBox() {
        sameTickets.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (sameTickets.isSelected()) {
                ticketSelector.setVisible(false);
                bookingButton.setVisible(true);
                categoryComboBox.setVisible(true);
                travelClassComboBox.setVisible(true);
            } else {
                ticketSelector.setVisible(true);
                bookingButton.setVisible(false);
                categoryComboBox.setVisible(false);
                travelClassComboBox.setVisible(false);
            }
        });
    }

    public void addTicket(ActionEvent actionEvent) {
        countOfTicket = ticketSpinner.getValue();

        try {
            App.DialogDeliver("add_ticket.fxml","Foglalás", false);
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a jegyfoglalás ablakot");
        }
    }

    public static int getCountOfTicket() {
        return countOfTicket;
    }

    public void saveBooking(ActionEvent event) {
        PopulateBooking();

        if (BookingController.getInstance().add(booking)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőgépet");
        }
    }
}
