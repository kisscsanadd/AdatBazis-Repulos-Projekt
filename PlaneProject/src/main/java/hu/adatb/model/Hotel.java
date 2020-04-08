package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hotel {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty stars = new SimpleIntegerProperty();
    private IntegerProperty cityId = new SimpleIntegerProperty();

    public Hotel(String name, int stars) {
        this.name.set(name);
        this.stars.set(stars);
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

    public int getStars() {
        return stars.get();
    }
    public IntegerProperty starsProperty() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars.set(stars);
    }

    public int getCityId() {return cityId.get();}
    public IntegerProperty cityIdProperty() {return cityId; }
    public void setCityId(int cityId) {this.cityId.set(cityId); }
}
