package hu.adatb.dao;

import hu.adatb.model.City;
import hu.adatb.model.Hotel;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class HotelDaoImpl implements HotelDao{
    @Override
    public boolean add(Hotel hotel) {
        return false;   // TODO - make it
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Hotel> getAll() {
        List<Hotel> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_HOTEL);

            while (rs.next()) {
                var cityId = rs.getInt("varos_id");
                var city = GetById.GetCityById(cityId);

                if (city == null) {
                    throw new GetByIdException("Cannot get city name by id");
                }

                Hotel hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("csillagok_szama"),
                        city
                );

                result.add(hotel);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a szállodákat");
        }

        return result;
    }
}
