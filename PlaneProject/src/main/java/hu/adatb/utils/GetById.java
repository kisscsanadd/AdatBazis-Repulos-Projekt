package hu.adatb.utils;

import hu.adatb.model.*;
import hu.adatb.query.Database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static hu.adatb.query.Queries.*;

public class GetById {

    public static City GetCityById(int id) {
        try(Connection conn = Database.ConnectionToDatabase();
            PreparedStatement st = conn.prepareStatement(SELECT_CITY_BY_ID);) {

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                return new City (
                        rs.getInt("id"),
                        rs.getString("nev")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User GetUserById(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_USER_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

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

    public static Plane GetPlaneById(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_PLANE_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                return new Plane (
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("sebesseg"),
                        rs.getInt("ferohely")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Flight GetFlightById(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_FLIGHT_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                var date = rs.getDate("felszallas_datum").toLocalDate();
                var time = rs.getTime("felszallas_datum").toLocalTime();

                LocalDateTime dateTime = LocalDateTime.of(date, time);

                var planeId = rs.getInt("repulogep_id");
                var plane = GetById.GetPlaneById(planeId);

                return new Flight(
                        dateTime,
                        rs.getString("from_airport"),
                        rs.getString("to_airport"),
                        plane,
                        rs.getInt("szabad_helyek")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Payment GetPaymentById(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_PAYMENT_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

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
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_CATEGORY_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

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
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_TRAVEL_CLASS_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

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
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_BOOKING_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                var user = GetById.GetUserById(rs.getInt("felh_id"));
                var flight = GetById.GetFlightById(rs.getInt("jarat_id"));
                var payment = GetById.GetPaymentById(rs.getInt("fizetesi_mod_id"));

                return new Booking(
                        id,
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
