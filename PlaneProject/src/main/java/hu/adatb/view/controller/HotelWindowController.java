package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.HotelController;
import hu.adatb.model.Hotel;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HotelWindowController implements Initializable {

    @FXML
    private TableView<Hotel> table;

    @FXML
    private TableColumn<Hotel, String> nameCol;

    @FXML
    private TableColumn<Hotel, Integer> starsCol;

    @FXML
    private TableColumn<Hotel, String> cityCol;

    @FXML
    private TableColumn<Hotel, Void> actionsCol;

    @FXML
    public void addHotel() {
        DialogHotelController.setIsAdd(true);

        try {
            App.DialogDeliver("dialog_hotel.fxml", "Szálloda hozzáadás");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public HotelWindowController() {
    }

    public void refreshTable() {
        List<Hotel> list = HotelController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Hotel> list = HotelController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        InitTable();
    }

    private void InitTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        starsCol.setCellValueFactory(new PropertyValueFactory<>("stars"));
        cityCol.setCellValueFactory(cityName -> new SimpleStringProperty(cityName.getValue().getCity().getName()));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button();
                private final Button editBtn = new Button();

                {
                    deleteBtn.setEffect(new ImageInput(new Image("pictures/delete.png")));
                    editBtn.setEffect(new ImageInput(new Image("pictures/edit.png")));

                    deleteBtn.setOnAction(event -> {
                        Hotel hotel = getTableView().getItems().get(getIndex());

                        var type = Utils.showConfirmation();

                        type.ifPresent(buttonType -> {
                            if(buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                                HotelController.getInstance().delete(hotel.getId());
                            }
                        });

                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        var selectedHotel = getTableView().getItems().get(getIndex());

                        DialogHotelController.setSelectedHotel(selectedHotel);
                        DialogHotelController.setIsAdd(false);

                        try {
                            App.DialogDeliver("dialog_hotel.fxml", "Szálloda módosítás");
                        } catch (IOException e) {
                            Utils.showWarning("Nem sikerült megnyitni a szálloda módosító ablakot");
                        }
                        refreshTable();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox();
                        container.setSpacing(10);
                        container.getChildren().addAll(editBtn, deleteBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }
}