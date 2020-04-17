package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddPlaneController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Integer> seatsSpinner;

    @FXML
    Spinner<Integer> speedSpinner;

    @FXML
    Label errorMsgName;

    @FXML
    Button saveButton;

    private List<Plane> planes;

    public AddPlaneController() {
    }

    private Plane plane = new Plane();

    @FXML
    private void save(ActionEvent event) {
        if (PlaneController.getInstance().add(plane)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőgépet");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                10, 1000, 10, 10));
        speedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                100, 2000, 100, 10));
        planes = PlaneController.getInstance().getAll();

        plane.nameProperty().bindBidirectional(nameField.textProperty());
        speedSpinner.getValueFactory().valueProperty().bindBidirectional(plane.speedProperty().asObject());
        seatsSpinner.getValueFactory().valueProperty().bindBidirectional(plane.seatsProperty().asObject());

        FieldValidator();

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var plane: planes) {
                if (newValue.equals(plane.getName())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("Ilyen név már létezik");
                saveButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void FieldValidator() {
        saveButton.disableProperty().bind(nameField.textProperty().isEmpty());
        //TODO better logic for these:
        if(plane.speedProperty().get()<100){
            plane.speedProperty().set(100);
        }
        if(plane.seatsProperty().get()<10){
            plane.seatsProperty().set(10);
        }
    }
}
