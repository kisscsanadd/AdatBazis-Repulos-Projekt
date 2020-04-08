package hu.adatb.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {

    private StringProperty name = new SimpleStringProperty();

    public City(String name) {
        this.name.set(name);
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
}
