package hu.adatb.dao;

import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_PLANE;

public class PlaneDaoImpl implements PlaneDao {

    @Override
    public boolean add(Plane plane) {
        return false; // TODO - make it
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Plane> getAll() {
        List<Plane> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_PLANE);

            while (rs.next()) {
                Plane plane = new Plane(
                        rs.getInt("id"),
                        rs.getString("nev"),
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
