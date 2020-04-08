package hu.adatb.dao;

import hu.adatb.model.Airport;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_AIRPORT;

public class AirportDaoImpl implements  AirportDao {

    @Override
    public boolean add(Airport airport) {
        return false;
    }

    @Override
    public List<Airport> getAll() {
        List<Airport> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_AIRPORT);

            while (rs.next()) {
                Airport airport = new Airport(
                        rs.getString("nev")
                );

                result.add(airport);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a város neveket");
        }

        return result;
    }
}
