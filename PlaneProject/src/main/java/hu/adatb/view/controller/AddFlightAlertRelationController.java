package hu.adatb.view.controller;

import hu.adatb.controller.AirportController;
import hu.adatb.controller.AlertController;
import hu.adatb.controller.FlightAlertRelationController;
import hu.adatb.model.Alert;
import hu.adatb.model.City;
import hu.adatb.model.Flight;
import hu.adatb.model.FlightAlertRelation;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddFlightAlertRelationController implements Initializable {

    @FXML
    private ComboBox<Alert> firstAlertComboBox;

    @FXML
    private ComboBox<Alert> secondAlertComboBox;

    @FXML
    private ComboBox<Alert> thirdAlertComboBox;

    @FXML
    private Button saveButton;

    public static Flight selectedFlight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PopulateComboBoxes();

        FieldValidator();
    }

    private void PopulateComboBoxes() {
        List<Alert> alerts = AlertController.getInstance().getAll();
        ObservableList<Alert> obsAlerts = FXCollections.observableList(alerts);

        firstAlertComboBox.getItems().addAll(obsAlerts);
        secondAlertComboBox.getItems().addAll(obsAlerts);
        thirdAlertComboBox.getItems().addAll(obsAlerts);

        Callback<ListView<Alert>, ListCell<Alert>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Alert item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getMessage());
            }
        };

        firstAlertComboBox.setCellFactory(factory);
        firstAlertComboBox.setButtonCell(factory.call(null));

        secondAlertComboBox.setCellFactory(factory);
        secondAlertComboBox.setButtonCell(factory.call(null));

        thirdAlertComboBox.setCellFactory(factory);
        thirdAlertComboBox.setButtonCell(factory.call(null));
    }

    private void FieldValidator() {
        saveButton.disableProperty().bind(firstAlertComboBox.valueProperty().isNull());

        secondAlertComboBox.disableProperty().bind(firstAlertComboBox.valueProperty().isNull());
        thirdAlertComboBox.disableProperty().bind(firstAlertComboBox.valueProperty().isNull().or(
                secondAlertComboBox.valueProperty().isNull()
        ));
    }

    @FXML
    private void save(ActionEvent event) {
        var alerts = new ArrayList<Alert>();
        alerts.add(firstAlertComboBox.getValue());

        if (secondAlertComboBox.getValue() != null) alerts.add(secondAlertComboBox.getValue());
        if (thirdAlertComboBox.getValue() != null) alerts.add(thirdAlertComboBox.getValue());

        int counter = 0;
        for (var alert: alerts) {
            var relation = new FlightAlertRelation(selectedFlight, alert);

            if (FlightAlertRelationController.getInstance().add(relation)) {
                counter++;
            } else {
                Utils.showWarning("Nem sikerült menteni az új relációt");
            }
        }

        if(counter == alerts.size()) {
            Utils.showInformation("Sikeresen hozzáadtál a járathoz " + alerts.size() + " figyelmeztetést");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public static void setSelectedFlight(Flight selectedFlight) {
        AddFlightAlertRelationController.selectedFlight = selectedFlight;
    }
}
