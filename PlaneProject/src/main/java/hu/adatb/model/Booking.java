package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Booking {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty userId = new SimpleIntegerProperty();
    private IntegerProperty flightId = new SimpleIntegerProperty();
    private IntegerProperty paymentId = new SimpleIntegerProperty();

    public Booking(String name, int userId, int flightId, int paymentId) {
        this.name.set(name);
        this.userId.set(userId);
        this.flightId.set(flightId);
        this.paymentId.set(paymentId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Integer getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId.set(userId);
    }

    public Integer getFlightId() {
        return flightId.get();
    }

    public IntegerProperty flightIdProperty() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId.set(flightId);
    }

    public Integer getPaymentId() {
        return paymentId.get();
    }

    public IntegerProperty paymentIdProperty() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId.set(paymentId);
    }
}
