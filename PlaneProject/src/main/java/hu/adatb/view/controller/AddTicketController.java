package hu.adatb.view.controller;

import hu.adatb.controller.PaymentController;
import hu.adatb.controller.TravelClassController;
import hu.adatb.model.City;
import hu.adatb.model.Payment;
import hu.adatb.model.TravelClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTicketController implements Initializable {

    @FXML
    GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var payments = PaymentController.getInstance().getAll();
        var travelClasses = TravelClassController.getInstance().getAll();

        ObservableList<Payment> obsPaymentList = FXCollections.observableList(payments);
        ObservableList<TravelClass> obsTravelClassList = FXCollections.observableList(travelClasses);

        var tickets = AddBookingController.getCountOfTicket();

        for (int i = 0; i < tickets; i++) {
            var label = new Label((i+1)+".jegy");
            var paymentList = new ComboBox<Payment>();
            var travelClassList = new ComboBox<TravelClass>();
            paymentList.setPromptText("Válassz kedvezményt");
            travelClassList.setPromptText("Válassz utazási osztályt");

            grid.add(label, 0, i);
            grid.add(paymentList,1, i);
            grid.add(travelClassList, 2, i);
        }
    }
}
