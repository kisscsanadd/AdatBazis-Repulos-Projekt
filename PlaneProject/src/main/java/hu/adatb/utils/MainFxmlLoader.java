package hu.adatb.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainFxmlLoader {
    private static Pane view;

    public static Pane getPage(String fileName) {
        try {
            view = new FXMLLoader().load(MainFxmlLoader.class.getResource("/fxmlView/adminView/" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
