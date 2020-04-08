package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket {

    private IntegerProperty categoryId = new SimpleIntegerProperty();
    private IntegerProperty bookingId = new SimpleIntegerProperty();
    private IntegerProperty travelClassId = new SimpleIntegerProperty();

    public Ticket(Integer categoryId, Integer bookingId, Integer travelClassId)
    {
        this.categoryId.set(categoryId);
        this.bookingId.set(bookingId);
        this.travelClassId.set(travelClassId);
    }

    public Integer getCategoryId() {
        return categoryId.get();
    }
    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId.set(categoryId);
    }

    public Integer getBookingId() {
        return bookingId.get();
    }
    public IntegerProperty bookingIdProperty() {
        return bookingId;
    }
    public void setBookingId(Integer bookingId) {this.bookingId.set(bookingId);}

    public Integer getTravelClassId() {return travelClassId.get(); }
    public IntegerProperty travelClassIdProperty() { return travelClassId;}
    public void setTravelClassId(Integer travelClassId) {this.travelClassId.set(travelClassId);}

}
