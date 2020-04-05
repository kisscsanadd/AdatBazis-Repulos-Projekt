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
import javafx.scene.input.MouseEvent;

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
        Task<List<User>> task = new Task<>() {
            @Override
            protected List<User> call() throws Exception {
                return users = UserController.getInstance().getAll();
            }
        };

        Thread getAllThread = new Thread(task);
        getAllThread.start();
    }

    public void login(ActionEvent actionEvent) {
        var valid = false;

        for (var user: users) {
            if (nameField.getText().equals(user.getName()) && passwordField.getText().equals(user.getPassword())) {
                valid = true;
            }
        }

        if(valid) {
            MainWindowController.back();
        } else {
            Utils.showWarning("Érvénytelen felhasználó-jelszó páros");
        }

    }


    @FXML
    private void cancel(ActionEvent event) {
        RegOrLoginController.back();
    }
}
