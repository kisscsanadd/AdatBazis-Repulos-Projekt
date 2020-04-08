package hu.adatb.utils;

import hu.adatb.model.*;
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

    public static Category GetCategoryById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_CATEGORY_BY_ID + id);

            while(rs.next()) {
                return new Category(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("kedvezmeny")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TravelClass GetTravelClassById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_TRAVEL_CLASS_BY_ID + id);

            while(rs.next()) {
                return new TravelClass(
                        rs.getInt("id"),
                        rs.getString("nev")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Booking GetBookingById(int id) {
        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();
            ResultSet rs = stmt.executeQuery(SELECT_BOOKING_BY_ID + id);
            User user = null;
            Flight flight = null;
            Payment payment = null;

            while (rs.next()) {
                user = GetById.GetUserById(rs.getInt("felh_id"));
                flight = GetById.GetFlightById(rs.getInt("jarat_id"));
                payment = GetById.GetPaymentById(rs.getInt("fizetesi_mod_id"));
            }

            while(rs.next()) {
                return new Booking(
                        rs.getInt("id"),
                        user,
                        flight,
                        payment
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
