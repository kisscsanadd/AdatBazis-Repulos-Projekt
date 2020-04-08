package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alert {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty message = new SimpleStringProperty();

    public Alert(int id, String message) {
        this.id.set(id);
        this.message.set(message);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}
