package hu.adatb.view.controller;

import hu.adatb.controller.HotelController;
import hu.adatb.model.Hotel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HotelListController implements Initializable {

    @FXML
    public TableView<Hotel> table = new TableView<>();

    @FXML
    private TableColumn<Hotel, String> nameCol;

    @FXML
    private TableColumn<Hotel, ImageView> starCol;

    @FXML
    private Label infoText;

    public static boolean isOwnFlight;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var hotels = HotelController.getInstance().getAll();
        String toAirportHotelNames;

        if (isOwnFlight) {
            toAirportHotelNames = OwnFlightController.getToAirportHotelNames();
        } else {
            toAirportHotelNames = FlightListController.getToAirportHotelNames();
        }

        if (toAirportHotelNames.equals("-")) {
            table.setVisible(false);
            infoText.setVisible(true);
        } else {

            var toAirportHotelNamePieces = toAirportHotelNames.split(", ");

            List<Hotel> filteredHotels = new ArrayList<>();

            for (int i = 0; i < toAirportHotelNamePieces.length; i++) {
                var toAirportHotelName = toAirportHotelNamePieces[i];
                var filteredHotel = hotels.stream().filter(hotel -> hotel.getName().equals(toAirportHotelName)).collect(Collectors.toList()).get(0);

                if (filteredHotel == null) {
                    continue;
                }

                filteredHotels.add(filteredHotel);
            }

            table.setItems(FXCollections.observableList(filteredHotels));

            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            starCol.setCellValueFactory(__ -> new ObservableValue<>() {
                @Override
                public void addListener(ChangeListener<? super ImageView> changeListener) {

                }

                @Override
                public void removeListener(ChangeListener<? super ImageView> changeListener) {

                }

                @Override
                public ImageView getValue() {
                    return new ImageView(new Image("pictures/star" + __.getValue().getStars() + ".png"));
                }

                @Override
                public void addListener(InvalidationListener invalidationListener) {

                }

                @Override
                public void removeListener(InvalidationListener invalidationListener) {

                }
            });
        }
    }


    public static void setIsOwnFlight(boolean isOwnFlight) {
        HotelListController.isOwnFlight = isOwnFlight;
    }
}
