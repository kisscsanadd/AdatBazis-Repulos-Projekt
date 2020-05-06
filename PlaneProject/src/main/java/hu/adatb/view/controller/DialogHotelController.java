package hu.adatb.view.controller;

import hu.adatb.controller.CityController;
import hu.adatb.controller.HotelController;
import hu.adatb.model.City;
import hu.adatb.model.Hotel;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static hu.adatb.utils.GetById.GetCityById;
import static hu.adatb.utils.Utils.SetErrorMessage;


public class DialogHotelController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Integer> starsSpinner;

    @FXML
    Label cityLabel;

    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    ComboBox<City> cities;

    private Hotel hotel = new Hotel();
    private List<Hotel> hotels;
    private static Hotel selectedHotel;
    private static boolean isAdd;

    public DialogHotelController() {
    }

    @FXML
    private void save(ActionEvent event) {
        hotel.setCity(cities.getSelectionModel().getSelectedItem());

        if (HotelController.getInstance().add(hotel)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új hotelt");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        hotel.setId(selectedHotel.getId());

        if (HotelController.getInstance().update(hotel)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni a módosított szállodát");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<City> cityList = CityController.getInstance().getAll();
        ObservableList<City> obsCityList = FXCollections.observableList(cityList);

        starsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, 5, isAdd ? 1 : selectedHotel.getStars(), 1));

        cities.getItems().addAll(obsCityList);

        Callback<ListView<City>, ListCell<City>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(City item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        cities.setCellFactory(factory);
        cities.setButtonCell(factory.call(null));

        hotels = HotelController.getInstance().getAll();

        hotel.nameProperty().bind(nameField.textProperty());
        hotel.starsProperty().bind(starsSpinner.valueProperty());

        if(!isAdd) {
            editButton.setVisible(true);
            addButton.setVisible(false);
            cities.setVisible(false);
            cityLabel.setVisible(false);
            nameField.setText(selectedHotel.getName());
        }

        FieldValidator();
    }

    private void FieldValidator() {
        addButton.disableProperty().bind(nameField.textProperty().isEmpty()
                .or(cities.valueProperty().isNull())
                .or(errorMsgName.textProperty().isNotEmpty()));

        editButton.disableProperty().bind(nameField.textProperty().isEmpty()
                .or(errorMsgName.textProperty().isNotEmpty()));

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var hotel: hotels) {
                if (newValue.equals(hotel.getName())) {
                    if(isAdd) {
                        match = true;
                    } else if(!newValue.equals(selectedHotel.getName())) {
                        match = true;
                    }
                }
            }

            errorMsgName.setText(SetErrorMessage(match));
        });
    }

    public static void setIsAdd(boolean isAdd) {
        DialogHotelController.isAdd = isAdd;
    }

    public static void setSelectedHotel(Hotel hotel) {
        selectedHotel = hotel;
    }

}
