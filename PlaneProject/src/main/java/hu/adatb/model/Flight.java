package hu.adatb.model;

import javafx.beans.property.*;

public class Flight {

    private StringProperty date = new SimpleStringProperty();
    private StringProperty fromAirport = new SimpleStringProperty();
    private StringProperty toAirport = new SimpleStringProperty();
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(String date, String fromAirportId, String toAirportId, Integer freeSeats) {
        this.date.set(date);
        this.fromAirport.set(fromAirportId);
        this.toAirport.set(toAirportId);
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
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

    public int getFreeSeats() {
        return freeSeats.get();
    }

    public IntegerProperty freeSeatsProperty() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats.set(freeSeats);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "date=" + date +
                ", fromAirport=" + fromAirport +
                ", toAirport=" + toAirport +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
