package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        try {
            App.DialogDeliver("add_plane.fxml", "Repülőgép hozzáadás");
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

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                private final Button editBtn = new Button("Módosítás");

                {
                    deleteBtn.setOnAction(event -> {
                        Plane p = getTableView().getItems().get(getIndex());
                        deletePlane(p);
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        Plane p = getTableView().getItems().get(getIndex());
                        editPlane(p);
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
                        container.getChildren().addAll(deleteBtn, editBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }

    private void editPlane(Plane p) {

        // TODO - create edit
    }

    private void deletePlane(Plane p){
        // TODO - create delete
    }
}