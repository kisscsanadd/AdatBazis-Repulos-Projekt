package hu.adatb.model;

import javafx.beans.property.*;

public class Airport {

    private StringProperty name = new SimpleStringProperty();

    public Airport(String name) {
        this.name.set(name);
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
}
