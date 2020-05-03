package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
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

public class PlaneWindowController implements Initializable {

    @FXML
    private TableView<Plane> table;

    @FXML
    private TableColumn<Plane, String> nameCol;

    @FXML
    private TableColumn<Plane, Integer> speedCol;

    @FXML
    private TableColumn<Plane, Integer> seatsCol;

    @FXML
    private TableColumn<Plane, Void> actionsCol;

    @FXML
    public void addPlane() {
        DialogPlaneController.setIsAdd(true);

        try {
            App.DialogDeliver("dialog_plane.fxml", "Repülőgép hozzáadás");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public PlaneWindowController() {
    }

    public void refreshTable() {
        List<Plane> list = PlaneController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Plane> list = PlaneController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        InitTable();
    }

    private void InitTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button();
                private final Button editBtn = new Button();

                {
                    deleteBtn.setEffect(new ImageInput(new Image("pictures/delete.png")));
                    editBtn.setEffect(new ImageInput(new Image("pictures/edit.png")));

                    deleteBtn.setOnAction(event -> {
                        Plane p = getTableView().getItems().get(getIndex());
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        var selectedPlane = getTableView().getItems().get(getIndex());

                        DialogPlaneController.setSelectedPlane(selectedPlane);
                        DialogPlaneController.setIsAdd(false);

                        try {
                            App.DialogDeliver("dialog_plane.fxml", "Repülőgép módosítás");
                        } catch (IOException e) {
                            Utils.showWarning("Nem sikerült megnyitni a repülőgép módosító ablakot");
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