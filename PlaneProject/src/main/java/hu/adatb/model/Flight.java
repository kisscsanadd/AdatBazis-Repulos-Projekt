package hu.adatb.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {

    private LocalDateTime dateTime;
    private StringProperty fromAirport = new SimpleStringProperty();
    private StringProperty toAirport = new SimpleStringProperty();
    private StringProperty plane = new SimpleStringProperty();
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(LocalDateTime dateTime, String fromAirport, String toAirport, String plane, Integer freeSeats) {
        this.dateTime = dateTime;
        this.fromAirport.set(fromAirport);
        this.toAirport.set(toAirport);
        this.plane.set(plane);
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public String getDateTimeInRightFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = dateTime.format(formatter);

        return formatDateTime;
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

    public String getPlane() {
        return plane.get();
    }

    public StringProperty planeProperty() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane.set(plane);
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
