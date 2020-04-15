package hu.adatb.model;

import hu.adatb.utils.DistanceCalculator;
import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {

    private LocalDateTime dateTime;
    private StringProperty fromAirport = new SimpleStringProperty();
    private StringProperty toAirport = new SimpleStringProperty();
    private Plane plane;
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(LocalDateTime dateTime, String fromAirport, String toAirport, Plane plane, Integer freeSeats) {
        this.dateTime = dateTime;
        this.fromAirport.set(fromAirport);
        this.toAirport.set(toAirport);
        this.plane = plane;
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public String getDateTimeInRightFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dateTime.format(formatter);
    }

    public String getTravelTime(double lat1, double lon1, double lat2, double lon2, Plane plane) {
        var distance = DistanceCalculator.Distance(lat1, lon1, lat2, lon2);
        var travelTimeInMinutes = (int)((distance/plane.getSpeed()) * 60);

        int hours = travelTimeInMinutes / 60;
        int minutes = travelTimeInMinutes - hours * 60;

        return hours > 0
                ? hours + " óra " + minutes + " perc"
                : travelTimeInMinutes + " perc";
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFromAirport() {
        return fromAirport.get();
    }

    public StringProperty fromAirportProperty() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport.set(fromAirport);
    }

    public String getToAirport() {
        return toAirport.get();
    }

    public StringProperty toAirportProperty() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport.set(toAirport);
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public int getFreeSeats() {
        return freeSeats.get();
    }

    public IntegerProperty freeSeatsProperty() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats.set(freeSeats);
    }

}
