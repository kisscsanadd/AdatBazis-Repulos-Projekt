package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AlertController;
import hu.adatb.controller.FlightAlertRelationController;
import hu.adatb.controller.FlightController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Alert;
import hu.adatb.model.FlightAlertRelation;
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
import java.util.stream.Collectors;

public class AlertWindowController implements Initializable {

    @FXML
    private TableView<Alert> table;

    @FXML
    private TableColumn<Alert, String> messageCol;

    @FXML
    private TableColumn<Alert, Void> actionsCol;

    public List<FlightAlertRelation> relations;

    @FXML
    public void addAlert() {
        DialogAlertController.setIsAdd(true);

        try {
            App.DialogDeliver("dialog_alert.fxml", "Figyelmeztetés hozzáadás", "style.css");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        relations = FlightAlertRelationController.getInstance().getAll();

        refreshTable();
        InitTable();
    }

    private void InitTable() {
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button();
            private final Button editBtn = new Button();

            {
                deleteBtn.setEffect(new ImageInput(new Image("pictures/delete.png")));
                editBtn.setEffect(new ImageInput(new Image("pictures/edit.png")));

                deleteBtn.setOnAction(event -> {
                    Alert selectedAlert = getTableView().getItems().get(getIndex());

                    var type = Utils.showConfirmation("Törlődni fognak a figyelmeztetéshez tartozó járat\n figyelmezetések is!");

                    type.ifPresent(buttonType ->  {
                        if(buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            DeleteAlert(selectedAlert);
                            refreshTable();
                        }
                    });
                });

                editBtn.setOnAction(event -> {
                    var selectedAlert = getTableView().getItems().get(getIndex());

                    DialogAlertController.setSelectedAlert(selectedAlert);
                    DialogAlertController.setIsAdd(false);

                    try {
                        App.DialogDeliver("dialog_alert.fxml", "Figyelmeztetés módosítás", "style.css");
                    } catch (IOException e) {
                        Utils.showWarning("Nem sikerült megnyitni a figyelmeztetés módosító ablakot");
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
        });
    }

    public void DeleteAlert(Alert selectedAlert) {
        var filteredRelations = relations.stream().filter(relation -> relation.getAlert().getId() == selectedAlert.getId())
                .collect(Collectors.toList());

        if(filteredRelations.size() != 0) {
            for(var filteredRelation : filteredRelations) {
                FlightAlertRelationController.getInstance().delete(filteredRelation);
            }
        }

        AlertController.getInstance().delete(selectedAlert.getId());
    }

    public void refreshTable() {
        table.setItems(FXCollections.observableList(
                AlertController.getInstance().getAll()));
    }
}