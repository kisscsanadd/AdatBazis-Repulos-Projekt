package hu.adatb.model;

import javafx.beans.property.*;

public class Flight {

    private StringProperty date = new SimpleStringProperty();
    private IntegerProperty fromAirportId = new SimpleIntegerProperty();
    private IntegerProperty toAirportId = new SimpleIntegerProperty();
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(String date, Integer fromAirportId, Integer toAirportId, Integer freeSeats) {
        this.date.set(date);
        this.fromAirportId.set(fromAirportId);
        this.toAirportId.set(toAirportId);
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public Integer getFromAirportId() {
        return fromAirportId.get();
    }

    public IntegerProperty fromProperty() {
        return fromAirportId;
    }

    public void setFromAirportId(Integer fromAirportId) {
        this.fromAirportId.set(fromAirportId);
    }

    public Integer getToAirportId() {
        return toAirportId.get();
    }

    public IntegerProperty toAirportIdProperty() {
        return toAirportId;
    }

    public void setToAirportId(Integer toAirportId) {
        this.toAirportId.set(toAirportId);
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
                ", from=" + fromAirportId +
                ", to=" + toAirportId +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
