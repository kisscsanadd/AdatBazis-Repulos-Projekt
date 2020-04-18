package hu.adatb.view.controller;

import hu.adatb.controller.*;
import hu.adatb.model.*;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddTicketController implements Initializable {

    @FXML
    GridPane grid;

    Button bookingButton = new Button("Foglalás");

    List<ComboBox<Category>> categoryComboBoxes = new ArrayList<>();
    List<ComboBox<TravelClass>> travelClassComboBoxes = new ArrayList<>();

    int countOfTicket;
    Ticket ticket = new Ticket();
    Booking booking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var categories = CategoryController.getInstance().getAll();
        var travelClasses = TravelClassController.getInstance().getAll();

        ObservableList<Category> obsCategoryList = FXCollections.observableList(categories);
        ObservableList<TravelClass> obsTravelClassList = FXCollections.observableList(travelClasses);

        countOfTicket = AddBookingController.getCountOfTicket();
        booking = AddBookingController.getBooking();

        int i;
        for (i = 0; i < countOfTicket; i++) {
            var label = new Label((i+1)+".jegy");
            var categoryComboBox = new ComboBox<Category>();
            var travelClassComboBox = new ComboBox<TravelClass>();
            categoryComboBox.setPromptText("Válassz kedvezményt");
            travelClassComboBox.setPromptText("Válassz utazási osztályt");

            categoryComboBox.getItems().addAll(obsCategoryList);
            Callback<ListView<Category>, ListCell<Category>> factory = lv -> new ListCell<>() {
                @Override
                protected void updateItem(Category item, boolean empty){
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getName());
                }
            };
            categoryComboBox.setCellFactory(factory);
            categoryComboBox.setButtonCell(factory.call(null));

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

            categoryComboBoxes.add(categoryComboBox);
            travelClassComboBoxes.add(travelClassComboBox);

            grid.add(label, 0, i);
            grid.add(categoryComboBox,1, i);
            grid.add(travelClassComboBox, 2, i);
        }

        bookingButton.setDefaultButton(true);
;
        grid.add(bookingButton, 2, ++i);

        addBooking();
    }

    public void addBooking(){
        bookingButton.setOnAction(actionEvent -> {
            if (BookingController.getInstance().add(booking)) {
                for(int i = 0; i < countOfTicket; i++) {
                    ticket.setCategory(categoryComboBoxes.get(i).getSelectionModel().getSelectedItem());
                    ticket.setTravelClass(travelClassComboBoxes.get(i).getSelectionModel().getSelectedItem());
                    ticket.setBooking(booking);

                    TicketController.getInstance().add(ticket);
                }

                Utils.showInformation("Sikeres foglalás");
            }

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        });
    }
}
