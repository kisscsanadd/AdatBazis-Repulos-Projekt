package hu.adatb.dao;

import hu.adatb.model.Alert;
import hu.adatb.model.Country;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_ALERT;

public class AlertDaoImpl implements AlertDao {
    @Override
    public boolean add(Alert alert) {
        return false;
    }

    @Override
    public List<Alert> getAll() {
        List<Alert> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALERT);

            while (rs.next()) {
                Alert alert = new Alert(
                        rs.getInt("id"),
                        rs.getString("uzenet")
                );

                result.add(alert);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a figyelmeztetéseket");
        }

        return result;
    }
}