package hu.adatb.view.controller;

import hu.adatb.controller.FlightController;
import hu.adatb.model.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertListController implements Initializable {

    @FXML
    GridPane grid;

    public static Flight flight;

    @FXML
    private void okButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var alerts = FlightController.getInstance().GetAlerts(flight);

        for(int i = 0; i < alerts.size(); i++) {
            var alert = new Label(alerts.get(i).getAlert().getMessage());
            alert.setWrapText(true);

            grid.add(new Label((i + 1) + "."), 0, i);
            grid.add(alert, 1,  i);
        }
    }

    public static void setFlight(Flight flight) {
        AlertListController.flight = flight;
    }
}
