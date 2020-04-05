package hu.adatb.view.controller;

import hu.adatb.controller.UserController;
import hu.adatb.model.User;
import hu.adatb.utils.Utils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddUserController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    TextField emailField;

    @FXML
    PasswordField passwordField;

    @FXML
    ImageView saveButton;

    @FXML
    Label errorMsgName;

    @FXML
    Label errorMsgEmail;

    @FXML
    Label errorMsgPassword;

    private List<User> users;

    public AddUserController() {
    }

    private User user = new User();

    @FXML
    private void save (MouseEvent mouseEvent) {
        if (UserController.getInstance().add(user)) {
            RegOrLoginController.back();
        } else {
            Utils.showWarning("Nem sikerult a mentes");
            return;
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        RegOrLoginController.back();
    }

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

        user.nameProperty().bind(nameField.textProperty());
        user.passwordProperty().bind(passwordField.textProperty());
        user.emailProperty().bind(emailField.textProperty());

        saveButton.disableProperty().bind(nameField.textProperty().isEmpty().
                or(errorMsgEmail.textProperty().isNotEmpty()).or(passwordField.textProperty().isEmpty()));

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var currentUser = "";
            var match = false;
            for (var user: users) {
                if (newValue.equals(user.getName())) {
                    match = true;
                    currentUser = user.getName();
                }
            }

            if (!match) {
                errorMsgName.setText("");
            } else {
                errorMsgName.setText("Ilyen név már létezik");
                saveButton.disableProperty().bind(nameField.textProperty().isEqualTo(currentUser));
            }
        });

        emailField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.matches("\\S+@\\S+\\.\\S\\S") ||
                    newValue.matches("\\S+@\\S+\\.\\S\\S\\S")) {
                errorMsgEmail.setText("");
            } else {
                errorMsgEmail.setText("Érvénytelen email cím");
            }
        });

        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > 4) {
                errorMsgPassword.setText("");
            } else {
                errorMsgPassword.setText("Túl rövid a jelszó");
                saveButton.disableProperty().bind(passwordField.textProperty().length().lessThan(5));
            }
        });
    }
}
