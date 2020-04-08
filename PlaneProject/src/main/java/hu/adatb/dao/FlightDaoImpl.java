package hu.adatb.dao;

import hu.adatb.model.Flight;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class FlightDaoImpl implements FlightDao {

    @Override
    public boolean add(Flight city) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_FLIGHT);

            while (rs.next()) {
                Flight flight = new Flight(
                        rs.getString("felszallas_datum"),
                        rs.getString("from_airport"),
                        rs.getString("to_airport"),
                        rs.getInt("szabad_helyek")
                );

                result.add(flight);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a járatokat");
        }

        return result;
    }
}
