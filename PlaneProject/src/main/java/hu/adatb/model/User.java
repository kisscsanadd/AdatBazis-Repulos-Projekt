package hu.adatb.model;

import javafx.beans.property.*;

public class User {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty isAdmin = new SimpleIntegerProperty();

    public User(String name, String password, Integer isAdmin, String email) {
        this.name.set(name);
        this.password.set(password);
        this.isAdmin.set(isAdmin);
        this.email.set(email);
    }

    public User() {
    }

    public int getIsAdmin() {
        return isAdmin.get();
    }

    public IntegerProperty isAdminProperty() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
