package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Flight;
import hu.adatb.model.FlightAlertRelation;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class FlightAlertRelationDaoImpl implements FlightAlertRelationDao {
    @Override
    public boolean add(FlightAlertRelation relation) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_FLIGHT_ALERT_RELATION)){

            st.setInt(1, relation.getFlight().getId());
            st.setInt(2, relation.getAlert().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful relation addition");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed addition");
        }

        Utils.showWarning("Nem sikerült a hozzáadás");
        return false;
    }

    @Override
    public boolean delete(FlightAlertRelation relation) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_FLIGHT_ALERT_RELATION)){

            st.setInt(1, relation.getFlight().getId());
            st.setInt(2, relation.getAlert().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted relation");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete relation");
        }

        return false;
    }

    @Override
    public List<FlightAlertRelation> getAll() {
        List<FlightAlertRelation> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_FLIGHT_ALERT_RELATION);

            while (rs.next()) {
                var flight = GetById.GetFlightById(conn, rs.getInt("jarat_id"));
                var alert = GetById.GetAlertById(conn, rs.getInt("figyelmeztetes_id"));

                var relation = new FlightAlertRelation(
                        rs.getInt("id"),
                        flight,
                        alert
                );

                result.add(relation);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a járatokat");
            e.printStackTrace();
        }

        return result;
    }
}
