package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AlertController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Alert;
import hu.adatb.model.Plane;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AlertWindowController implements Initializable {

    @FXML
    private TableView<Alert> table;

    @FXML
    private TableColumn<Alert, String> messageCol;

    @FXML
    private TableColumn<Alert, Void> actionsCol;

    @FXML
    public void addAlert() {
        DialogAlertController.setIsAdd(true);

        try {
            App.DialogDeliver("dialog_alert.fxml", "Figyelmeztetés hozzáadás");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public AlertWindowController() {
    }

    public void refreshTable() {
        List<Alert> list = AlertController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Alert> list = AlertController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        InitTable();
    }

    private void InitTable() {
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                private final Button editBtn = new Button("Módosítás");

                {
                    deleteBtn.setOnAction(event -> {
                        Alert a = getTableView().getItems().get(getIndex());
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        var selectedAlert = getTableView().getItems().get(getIndex());

                        DialogAlertController.setSelectedAlert(selectedAlert);
                        DialogAlertController.setIsAdd(false);

                        try {
                            App.DialogDeliver("dialog_alert.fxml", "Figyelmeztetés módosítás");
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
                        container.getChildren().addAll(deleteBtn, editBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }
}