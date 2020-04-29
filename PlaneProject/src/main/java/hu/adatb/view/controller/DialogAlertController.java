package hu.adatb.view.controller;

import hu.adatb.controller.AlertController;
import hu.adatb.model.Alert;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DialogAlertController implements Initializable {

    @FXML
    TextField messageField;

    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    private Alert alert = new Alert();
    private List<Alert> alerts;
    private static Alert selectedAlert;
    private static boolean isAdd;

    public DialogAlertController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (AlertController.getInstance().add(alert)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új figyelmeztetést");
        }
    }

    @FXML
    public void edit(ActionEvent event) {

    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alerts = AlertController.getInstance().getAll();

        if(isAdd) {
            addButton.setVisible(true);
            editButton.setVisible(false);
        } else {
            addButton.setVisible(false);
            editButton.setVisible(true);
        }

        InitTable();
        FieldValidator();
    }

    private void FieldValidator() {
        addButton.disableProperty().bind(messageField.textProperty().isEmpty());

        messageField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var alert: alerts) {
                if (newValue.equals(alert.getMessage())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("Ilyen üzenet már létezik");
                addButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void InitTable() {
        messageField.setText(isAdd ? "" : selectedAlert.getMessage());

        alert.messageProperty().bind(messageField.textProperty());
        alert.setId(selectedAlert.getId());
    }

    public static void setIsAdd(boolean isAdd) {
        DialogAlertController.isAdd = isAdd;
    }

    public static void setSelectedAlert(Alert alert) {
        selectedAlert = alert;
    }

}
