package hu.adatb.view.controller;

import hu.adatb.controller.CategoryController;
import hu.adatb.controller.PaymentController;
import hu.adatb.controller.TravelClassController;
import hu.adatb.model.Category;
import hu.adatb.model.City;
import hu.adatb.model.Payment;
import hu.adatb.model.TravelClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTicketController implements Initializable {

    @FXML
    GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var categories = CategoryController.getInstance().getAll();
        var travelClasses = TravelClassController.getInstance().getAll();

        ObservableList<Category> obsCategoryList = FXCollections.observableList(categories);
        ObservableList<TravelClass> obsTravelClassList = FXCollections.observableList(travelClasses);

        var tickets = AddBookingController.getCountOfTicket();
        int i = 0;
        for (i = 0; i < tickets; i++) {
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

            grid.add(label, 0, i);
            grid.add(categoryComboBox,1, i);
            grid.add(travelClassComboBox, 2, i);
        }

        Button bookingButton = new Button("Foglalás");
        bookingButton.setDefaultButton(true);
;
        grid.add(bookingButton, 2, ++i);
    }
}
