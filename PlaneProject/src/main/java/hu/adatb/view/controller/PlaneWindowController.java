package hu.adatb.view.controller;

import hu.adatb.controller.PlaneController;
import hu.adatb.model.Plane;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
    private TableColumn<Plane, Integer> seatsCol;

    @FXML
    private TableColumn<Plane, Void> actionsCol;

    @FXML
    public void refreshTable() {
        List<Plane> list = PlaneController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @FXML
    public void addPlane() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/hu/adatb/fxmlView/add_plane.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PlaneWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Plane> list = PlaneController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("ferohely"));
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