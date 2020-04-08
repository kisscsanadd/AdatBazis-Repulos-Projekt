package hu.adatb.utils;

import hu.adatb.model.Flight;
import hu.adatb.model.Payment;
import hu.adatb.model.User;
import hu.adatb.query.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static hu.adatb.query.Queries.*;

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

    public static User GetUserById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_USER_BY_ID + id);

            while(rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("felh_nev"),
                        rs.getString("jelszo"),
                        rs.getInt("isAdmin"),
                        rs.getString("email")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Flight GetFlightById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_FLIGHT_BY_ID + id);

            while(rs.next()) {
                return new Flight(
                        rs.getString("felszallas_datum"),
                        rs.getString("from_airport"),
                        rs.getString("to_airport"),
                        rs.getInt("szabad_helyek")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Payment GetPaymentById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_PAYMENT_BY_ID + id);

            while(rs.next()) {
                return new Payment(
                        rs.getInt("id"),
                        rs.getString("nev")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
