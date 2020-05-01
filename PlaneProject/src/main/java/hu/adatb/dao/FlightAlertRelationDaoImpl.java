package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.FlightAlertRelation;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static hu.adatb.query.Queries.INSERT_FLIGHT_ALERT_RELATION;

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
    public List<FlightAlertRelation> getAll() {
        return null;
    }
}
