package hu.adatb.view.controller;

import hu.adatb.controller.UserController;
import hu.adatb.model.User;
import hu.adatb.utils.Utils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginUserController implements Initializable {


    @FXML
    TextField nameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    private List<User> users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = RegOrLoginController.getAllUsers();
    }

    public void login(ActionEvent actionEvent) {
        var valid = false;
        var isAdmin = false;

        for (var user: users) {
            if (nameField.getText().equals(user.getName()) && passwordField.getText().equals(user.getPassword())) {
                valid = true;
                isAdmin = user.getIsAdmin() == 1;
            }
        }

        if(valid) {
            MainWindowController.goToMain(isAdmin);    //TODO - separate the logic according to isAdmin
        } else {
            Utils.showWarning("Érvénytelen felhasználó-jelszó páros");
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        RegOrLoginController.back("login");
    }
}
