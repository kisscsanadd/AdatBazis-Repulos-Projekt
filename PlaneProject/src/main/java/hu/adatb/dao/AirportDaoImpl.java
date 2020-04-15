package hu.adatb.dao;

import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class AirportDaoImpl implements AirportDao {

    @Override
    public boolean add(Airport airport) {
        return false;   // TODO - make it
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Airport> getAll() {
        List<Airport> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_AIRPORT);

            while (rs.next()) {
                var cityId = rs.getInt("varos_id");
                var city = GetById.GetCityById(cityId);

                if (city == null) {
                    throw new GetByIdException("Cannot get city name by id");
                }

                Airport airport = new Airport(
                        rs.getString("nev"),
                        rs.getDouble("szelesseg"),
                        rs.getDouble("hosszusag"),
                        city
                );

                result.add(airport);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a repülőtereket");
        }

        return result;
    }
}
