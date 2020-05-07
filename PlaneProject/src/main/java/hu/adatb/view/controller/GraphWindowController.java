package hu.adatb.view.controller;

import hu.adatb.controller.TravelClassController;
import hu.adatb.utils.DataHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GraphWindowController implements Initializable {

    @FXML
    private ComboBox<String> graphComboBox;

    @FXML
    private BarChart<String, Integer> ticketGroupByTravelClassChart;

    private final String countOfTicketGroupByTravelClass = "Jegyek száma utazási osztály szerint";
    private final List<String> months = Arrays.asList("Április","Május", "Június");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphComboBox.getItems().addAll(countOfTicketGroupByTravelClass, "asd");
        PopulateTicketNumberChart();


        graphComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals(countOfTicketGroupByTravelClass)) {
                ticketGroupByTravelClassChart.setVisible(true);
            } else {
                ticketGroupByTravelClassChart.setVisible(false);
            }
        });
    }

    private void PopulateTicketNumberChart() {
        var dictionary = TravelClassController.getInstance().getCountOfTicketGroupByTravelClass();

        var tourist = dictionary.stream().filter(data -> data.travelClassName.equals("Turista"))
                .sorted(new SortByMonth()).collect(Collectors.toList());

        var business = dictionary.stream().filter(data -> data.travelClassName.equals("Business"))
                .sorted(new SortByMonth()).collect(Collectors.toList());

        var first = dictionary.stream().filter(data -> data.travelClassName.equals("First"))
                .sorted(new SortByMonth()).collect(Collectors.toList());

        var set1 = new XYChart.Series<String, Integer>();
        var set2 = new XYChart.Series<String, Integer>();
        var set3 = new XYChart.Series<String, Integer>();

        set1.setName("Turista");
        set2.setName("Business");
        set3.setName("First");

        int i = 0;
        for (var month : months) {
            set1.getData().add(new XYChart.Data<>(month, tourist.get(i).countOfTickets));
            set2.getData().add(new XYChart.Data<>(month, business.get(i).countOfTickets));
            set3.getData().add(new XYChart.Data<>(month, first.get(i).countOfTickets));
            i++;
        }

        ticketGroupByTravelClassChart.getData().addAll(set1, set2, set3);
    }
}

class SortByMonth implements Comparator<DataHelper> {
    public int compare(DataHelper a, DataHelper b)
    {
        return a.month - b.month;
    }
}