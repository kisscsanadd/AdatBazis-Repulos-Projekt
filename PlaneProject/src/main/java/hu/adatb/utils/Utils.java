package hu.adatb.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Utils {
    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmation() {
        return showConfirmation("");
    }

    public static Optional<ButtonType> showConfirmation(String statement) {
       var yesButton = new ButtonType("Igen", ButtonBar.ButtonData.YES);
       var noButton = new ButtonType("Nem", ButtonBar.ButtonData.NO);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Biztos törölni akarsz?\n" + statement, yesButton, noButton);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("Megerősítés");

        return alert.showAndWait();
    }

    public static String SetErrorMessage (boolean match) {
        return match ? "Ilyen név már létezik" : "";
    }
}
