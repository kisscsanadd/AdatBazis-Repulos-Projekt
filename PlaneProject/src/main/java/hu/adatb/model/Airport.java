package hu.adatb.model;

import javafx.beans.property.*;

public class Airport {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty cityId = new SimpleIntegerProperty();

    public Airport(String name, Integer cityId)
    {
        this.name.set(name);
        this.cityId.set(cityId);
    }

    public Airport() {
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

    public Integer getCityId() {
        return cityId.get();
    }

    public IntegerProperty toCityIdProperty() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId.set(cityId);
    }
}
