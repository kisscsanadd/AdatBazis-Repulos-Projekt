package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Flight;
import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.*;
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
    public boolean update(Flight flight) {
        try(Connection conn = Database.ConnectionToDatabase();
            PreparedStatement st = conn.prepareStatement(UPDATE_FLIGHT)) {

            st.setInt(1, flight.getFromAirport().getId());
            st.setInt(2, flight.getToAirport().getId());
            st.setInt(3, flight.getPlane().getId());
            st.setInt(4, flight.getFreeSeats());
            st.setInt(5, flight.getVogue());
            st.setInt(6, flight.getId());

            int res = st.executeUpdate();

            if (res == 1) {
                return true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed update vogue");
            e.printStackTrace();
        }

        Utils.showWarning("Nem sikerült frissíteni a népszerűséget");
        return false;
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_FLIGHT);

            while (rs.next()) {
                var date = rs.getDate("felszallas_datum").toLocalDate();
                var time = rs.getTime("felszallas_datum").toLocalTime();

                LocalDateTime dateTime = LocalDateTime.of(date, time);

                var planeId = rs.getInt("repulogep_id");
                var plane = GetById.GetPlaneById(conn, planeId);

                var fromAirportId = rs.getInt("repuloter_id_fel");
                var fromAirport = GetById.GetAirportById(conn, fromAirportId);

                var toAirportId = rs.getInt("repuloter_id_le");
                var toAirport = GetById.GetAirportById(conn, toAirportId);

                Flight flight = new Flight(
                        rs.getInt("id"),
                        dateTime,
                        fromAirport,
                        toAirport,
                        plane,
                        rs.getInt("nepszeruseg"),
                        rs.getInt("szabad_helyek")
                );

                result.add(flight);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a járatokat");
            e.printStackTrace();
        }

        return result;
    }
}
