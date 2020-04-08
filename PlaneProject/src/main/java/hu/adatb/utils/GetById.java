package hu.adatb.utils;

import hu.adatb.query.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static hu.adatb.query.Queries.SELECT_CITY_BY_ID;

public class GetById {

    public static String GetCityNameById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_CITY_BY_ID + id);

            while(rs.next())
                return rs.getString("nev");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
