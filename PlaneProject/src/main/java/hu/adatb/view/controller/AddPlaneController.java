package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.PlaneController;
import hu.adatb.controller.UserController;
import hu.adatb.model.Plane;
import hu.adatb.model.User;
import hu.adatb.utils.Utils;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class AddPlaneController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Integer> seatsSpinner;

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
            try {
                App.StageDeliver("/adminView/PlaneScreen.fxml", "Repülőgépek", "style.css");
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        try {
            App.StageDeliver("adminView/PlaneScreen.fxml", "Repülőgépek", "style.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        seatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, 1000, 100));
        planes = PlaneController.getInstance().getAll();

        plane.nameProperty().bindBidirectional(nameField.textProperty());

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
    }


}
