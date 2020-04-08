package hu.adatb.dao;

import hu.adatb.model.Country;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_COUNTRY;

public class CountryDaoImpl implements CountryDao {
    @Override
    public boolean add(Country country) {
        return false;
    }

    @Override
    public List<Country> getAll() {
        List<Country> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_COUNTRY);

            while (rs.next()) {
                Country country = new Country(
                        rs.getInt("id"),
                        rs.getString("nev")
                );

                result.add(country);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni az országokat");
        }

        return result;
    }
}
