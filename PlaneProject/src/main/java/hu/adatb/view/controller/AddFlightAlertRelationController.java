package hu.adatb.view.controller;

import hu.adatb.controller.AirportController;
import hu.adatb.controller.AlertController;
import hu.adatb.controller.FlightAlertRelationController;
import hu.adatb.controller.FlightController;
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
import java.util.stream.Collectors;

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
    private List<FlightAlertRelation> alertsForSelectedFlight;
    private final String basicAlertPromptText = "Válassz figyelmeztetést";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PopulateComboBoxes();

        alertsForSelectedFlight = FlightController.getInstance().GetAlerts(selectedFlight);

        if (alertsForSelectedFlight.size() > 0) {
            firstAlertComboBox.setPromptText(alertsForSelectedFlight.get(0).getAlert().getMessage());
            if(alertsForSelectedFlight.size() > 1) {
                secondAlertComboBox.setPromptText(alertsForSelectedFlight.get(1).getAlert().getMessage());
                if(alertsForSelectedFlight.size() > 2) {
                    thirdAlertComboBox.setPromptText(alertsForSelectedFlight.get(2).getAlert().getMessage());
                }
            }
        }

        FieldValidator();
    }

    private void PopulateComboBoxes() {
        List<Alert> alerts = new ArrayList<>();
        alerts.add(new Alert(-1, "---"));
        alerts.addAll(AlertController.getInstance().getAll());
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

    @FXML
    private void save(ActionEvent event) {
        var alerts = new ArrayList<Alert>();

        if(firstAlertComboBox.getPromptText().equals(basicAlertPromptText)) {
            alerts.add(firstAlertComboBox.getValue());
        } else {
            if(firstAlertComboBox.getValue() == null)
                alerts.add(alertsForSelectedFlight.get(0).getAlert());
        }

        if (secondAlertComboBox.getPromptText().equals(basicAlertPromptText)) {
            if(secondAlertComboBox.getValue() != null)
                alerts.add(secondAlertComboBox.getValue());
        } else {
            if(secondAlertComboBox.getValue() == null)
                alerts.add(alertsForSelectedFlight.get(1).getAlert());
        }

        if (thirdAlertComboBox.getPromptText().equals(basicAlertPromptText)) {
            if(thirdAlertComboBox.getValue() != null)
                alerts.add(thirdAlertComboBox.getValue());
        } else {
            if(thirdAlertComboBox.getValue() == null)
                alerts.add(alertsForSelectedFlight.get(2).getAlert());
        }

        for(var relation : alertsForSelectedFlight) {
            FlightAlertRelationController.getInstance().delete(relation);
        }

        var filteredAlerts = alerts.stream().filter(alert -> alert.getId() != -1).collect(Collectors.toList());

        EditFlightController.setSelectedAlerts(filteredAlerts);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void FieldValidator() {
        saveButton.disableProperty().bind(firstAlertComboBox.valueProperty().isNull()
                .and(firstAlertComboBox.promptTextProperty().isEqualTo(basicAlertPromptText)));

        secondAlertComboBox.disableProperty().bind(firstAlertComboBox.valueProperty().isNull()
                .and(firstAlertComboBox.promptTextProperty().isEqualTo(basicAlertPromptText)));
        thirdAlertComboBox.disableProperty().bind((firstAlertComboBox.valueProperty().isNull().or(
                secondAlertComboBox.valueProperty().isNull()).and(firstAlertComboBox.promptTextProperty().isEqualTo(basicAlertPromptText).and(secondAlertComboBox.promptTextProperty().isEqualTo(basicAlertPromptText)))
        ));
    }

    public static void setSelectedFlight(Flight selectedFlight) {
        AddFlightAlertRelationController.selectedFlight = selectedFlight;
    }
}
