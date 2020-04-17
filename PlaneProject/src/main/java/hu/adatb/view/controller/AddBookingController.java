package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.PaymentController;
import hu.adatb.model.Category;
import hu.adatb.model.City;
import hu.adatb.model.Payment;
import hu.adatb.model.TravelClass;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookingController implements Initializable {

    @FXML
    ComboBox<Payment> paymentComboBox;

    @FXML
    Spinner<Integer> ticketSpinner;

    @FXML
    Button ticketSelector;

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
    }

    public void addTicket(ActionEvent actionEvent) {
        countOfTicket = ticketSpinner.getValue();

        try {
            App.DialogDeliver("add_ticket.fxml","Foglalás", false);
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a jegy foglalás ablakot");
        }
    }

    public static int getCountOfTicket() {
        return countOfTicket;
    }
}
