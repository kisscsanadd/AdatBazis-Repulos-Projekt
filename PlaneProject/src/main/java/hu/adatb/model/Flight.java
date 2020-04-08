package hu.adatb.model;

import javafx.beans.property.*;

public class Flight {

    private StringProperty date = new SimpleStringProperty();
    private StringProperty from = new SimpleStringProperty();
    private StringProperty to = new SimpleStringProperty();
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(String date, String from, String to, Integer freeSeats) {
        this.date.set(date);
        this.from.set(from);
        this.to.set(to);
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public String getFrom() {
        return from.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getTo() {
        return to.get();
    }

    public StringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
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
                ", from=" + from +
                ", to=" + to +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
