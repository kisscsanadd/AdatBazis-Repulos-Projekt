package hu.adatb.model;

import hu.adatb.controller.FlightAlertRelationController;
import hu.adatb.controller.HotelController;
import hu.adatb.utils.DistanceCalculator;
import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Flight {

    private IntegerProperty id = new SimpleIntegerProperty();
    private LocalDateTime dateTime;
    private Airport fromAirport;
    private Airport toAirport;
    private Plane plane;
    private  IntegerProperty vogue = new SimpleIntegerProperty();
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(int id, LocalDateTime dateTime, Airport fromAirport, Airport toAirport, Plane plane, Integer vogue, Integer freeSeats) {
        this.id.set(id);
        this.dateTime = dateTime;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.plane = plane;
        this.vogue.set(vogue);
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(hu.adatb.model.Plane plane) {
        this.plane = plane;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    public int getVogue() {
        return vogue.get();
    }

    public IntegerProperty vogueProperty() {
        return vogue;
    }

    public void setVogue(int vogue) {
        this.vogue.set(vogue);
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
