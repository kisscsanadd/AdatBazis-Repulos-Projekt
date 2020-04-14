package hu.adatb.dao;

import hu.adatb.model.Flight;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                var date = rs.getDate("felszallas_datum").toLocalDate();
                var time = rs.getTime("felszallas_datum").toLocalTime();

                LocalDateTime dateTime = LocalDateTime.of(date, time);

                Flight flight = new Flight(
                        dateTime,
                        rs.getString("from_airport"),
                        rs.getString("to_airport"),
                        rs.getString("plane_name"),
                        rs.getInt("szabad_helyek")
                );

                System.out.println("Flight: " + flight.getDateTime() + " " + flight.getPlane());

                result.add(flight);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a járatokat");
        }

        return result;
    }
}
