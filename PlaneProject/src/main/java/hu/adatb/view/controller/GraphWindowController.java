package hu.adatb.view.controller;

import hu.adatb.controller.*;
import hu.adatb.utils.DataHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
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

    @FXML
    private LineChart<String, Integer> countOfToAirportChart;

    @FXML
    private LineChart<String, Integer> countOfPlaneChart;

    @FXML
    private LineChart<String, Integer> countOfCategoryChart;

    @FXML
    ComboBox<String> monthComboBox;

    @FXML
    private PieChart soldTicketPieChart;

    private final String countOfTicketGroupByTravelClass = "Jegyek száma utazási osztály szerint";
    private final String countOfPlane = "Legnépszerűbb repülőgépek";
    private final String countOfToAirport = "Legnépszerűbb repülőterek";
    private final String countOfCategory = "Kedvezmény kategóriák gyakorisága";
    private final String countOfSoldTicket = "Eladott jegyek";
    private final List<String> months = Arrays.asList("Április","Május", "Június");
    private final List<String> allMonths = Arrays.asList(
            "Január", "Február", "Március",
            "Április", "Május", "Június",
            "Július", "Augusztus", "Szeptember",
            "Október", "November", "December");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphComboBox.getItems().addAll(countOfTicketGroupByTravelClass, countOfToAirport,
                countOfPlane, countOfCategory, countOfSoldTicket);

        monthComboBox.getItems().addAll(allMonths);

        PopulateTicketNumberChart();
        PopulateCountOfToAirport();
        PopulateCountOfPlane();
        PopulateCountOfCategory();
        PopulateSoldTicketPieChart();

        graphComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case countOfTicketGroupByTravelClass:
                    ticketGroupByTravelClassChart.setVisible(true);
                    countOfPlaneChart.setVisible(false);
                    countOfToAirportChart.setVisible(false);
                    countOfCategoryChart.setVisible(false);
                    soldTicketPieChart.setVisible(false);
                    monthComboBox.setVisible(false);
                    break;
                case countOfToAirport:
                    countOfToAirportChart.setVisible(true);
                    ticketGroupByTravelClassChart.setVisible(false);
                    countOfPlaneChart.setVisible(false);
                    countOfCategoryChart.setVisible(false);
                    soldTicketPieChart.setVisible(false);
                    monthComboBox.setVisible(false);
                    break;
                case countOfPlane:
                    ticketGroupByTravelClassChart.setVisible(false);
                    countOfToAirportChart.setVisible(false);
                    countOfCategoryChart.setVisible(false);
                    countOfPlaneChart.setVisible(true);
                    soldTicketPieChart.setVisible(false);
                    monthComboBox.setVisible(false);
                    break;
                case countOfCategory:
                    ticketGroupByTravelClassChart.setVisible(false);
                    countOfToAirportChart.setVisible(false);
                    countOfCategoryChart.setVisible(true);
                    countOfPlaneChart.setVisible(false);
                    soldTicketPieChart.setVisible(false);
                    monthComboBox.setVisible(false);
                    break;
                case countOfSoldTicket:
                    soldTicketPieChart.setVisible(true);
                    ticketGroupByTravelClassChart.setVisible(false);
                    countOfToAirportChart.setVisible(false);
                    countOfCategoryChart.setVisible(false);
                    countOfPlaneChart.setVisible(false);
                    monthComboBox.setVisible(true);
            }
        });

        monthComboBox.valueProperty().addListener(observable -> {
            PopulateSoldTicketPieChart();
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

    private void PopulateCountOfToAirport() {
        var airports = AirportController.getInstance().getAll();
        var dictionary = AirportController.getInstance().getCountOfToAirport();

        var series = new XYChart.Series<String, Integer>();

        for (var airport : airports) {
            series.getData().add(new XYChart.Data<>(
                    airport.getName(),
                    dictionary.get(airport.getName()))
            );
        }

        countOfToAirportChart.getData().add(series);
    }

    private void PopulateCountOfCategory() {
        var categories = CategoryController.getInstance().getAll();
        var dictionary = CategoryController.getInstance().getCountOfCategory();

        var series = new XYChart.Series<String, Integer>();

        for (var category : categories) {
            series.getData().add(new XYChart.Data<>(
                    category.getName(),
                    dictionary.get(category.getName()))
            );
        }

        countOfCategoryChart.getData().add(series);
    }

    private void PopulateCountOfPlane() {
        var planes = PlaneController.getInstance().getAll();
        var dictionary = PlaneController.getInstance().getCountOfPlane();

        var series = new XYChart.Series<String, Integer>();

        for (var plane : planes) {
            series.getData().add(new XYChart.Data<>(
                    plane.getName(),
                    dictionary.get(plane.getName()))
            );
        }

        countOfPlaneChart.getData().add(series);
    }

    private void PopulateSoldTicketPieChart() {
        var selectedMonth = getMonth(monthComboBox.getValue());

        if (selectedMonth == -1) {
            monthComboBox.setValue("Május");
            selectedMonth = 5;
        }

        var countOfAllTicket = TicketController.getInstance().getCountOfAllTicketInMonth(selectedMonth);
        var countOfSoldTicket = TicketController.getInstance().getCountOfSoldTicketInMonth(selectedMonth);
        var countOfNotSoldTicket = countOfAllTicket - countOfSoldTicket;

        var pieChart = FXCollections.observableArrayList(
                new PieChart.Data("Eladott jegyek (" + countOfSoldTicket + ")", countOfSoldTicket),
                new PieChart.Data("Nem eladott jegyek (" + countOfNotSoldTicket + ")", countOfNotSoldTicket)
        );

        soldTicketPieChart.setData(pieChart);
        soldTicketPieChart.setTitle("Össz jegyek száma: " + countOfAllTicket);
    }

    private int getMonth(String selectedMonth) {
        int i = 1;
        for (var month: allMonths) {
            if(month.equals(selectedMonth)) {
                return i;
            }
            i++;
        }

        return -1;
    }
}

class SortByMonth implements Comparator<DataHelper> {
    public int compare(DataHelper a, DataHelper b)
    {
        return a.month - b.month;
    }
}