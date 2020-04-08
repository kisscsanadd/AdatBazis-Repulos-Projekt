package hu.adatb.model;

import javafx.beans.property.*;

public class Airport {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty cityId = new SimpleIntegerProperty();
    private City city;

    public Airport(String name, City city)
    {
        this.name.set(name);
        this.city = city;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
