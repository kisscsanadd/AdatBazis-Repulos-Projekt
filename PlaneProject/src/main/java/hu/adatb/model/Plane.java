package hu.adatb.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Plane {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty ferohely = new SimpleIntegerProperty();

    public Plane(String name, int ferohely) {
        this.name.set(name);
        this.ferohely.set(ferohely);
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
    public int getFerohely() {
        return ferohely.get();
    }

    public IntegerProperty ferohelyProperty() {
        return ferohely;
    }

    public void setFerohely(int ferohely) {
        this.ferohely.set(ferohely);
    }
}
