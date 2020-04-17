package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class PlaneDaoImpl implements PlaneDao {

    @Override
    public boolean add(Plane plane) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_PLANE)){

            st.setString(1, plane.getName());
            st.setInt(2, plane.getSpeed());
            st.setInt(3, plane.getSeats());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful addition");
                Utils.showInformation("Sikeres hozzáadás");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed addition");
        }

        Utils.showWarning("Nem sikerült a hozzáadás");
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Plane> getAll() {
        List<Plane> result = new ArrayList<>();

        try(Connection conn = Database.ConnectionToDatabase();
            Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_PLANE);

            while (rs.next()) {
                Plane plane = new Plane(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("sebesseg"),
                        rs.getInt("ferohely")
                );

                result.add(plane);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a repülőgépeket");
        }

        return result;
    }
}
