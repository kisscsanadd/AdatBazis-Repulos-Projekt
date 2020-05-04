package hu.adatb.utils;

import hu.adatb.model.*;
import hu.adatb.query.Database;

import java.sql.*;
import java.time.LocalDateTime;

import static hu.adatb.query.Queries.*;

public class GetById {

    public static City GetCityById(Connection conn, int id) {
        try(PreparedStatement st = conn.prepareStatement(SELECT_CITY_BY_ID);) {

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new City (
                    rs.getInt("id"),
                    rs.getString("nev")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User GetUserById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_USER_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("felh_nev"),
                    rs.getString("jelszo"),
                    rs.getInt("isAdmin") == 1,
                    rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Plane GetPlaneById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_PLANE_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new Plane(
                    rs.getInt("id"),
                    rs.getString("nev"),
                    rs.getInt("sebesseg"),
                    rs.getInt("ferohely")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Airport GetAirportById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_AIRPORT_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                var cityId = rs.getInt("varos_id");
                var city = GetById.GetCityById(conn, cityId);

                return new Airport (
                    rs.getInt("id"),
                    rs.getString("nev"),
                    rs.getDouble("szelesseg"),
                    rs.getDouble("hosszusag"),
                    city
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Flight GetFlightById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_FLIGHT_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                var date = rs.getDate("felszallas_datum").toLocalDate();
                var time = rs.getTime("felszallas_datum").toLocalTime();

                LocalDateTime dateTime = LocalDateTime.of(date, time);

                var planeId = rs.getInt("repulogep_id");
                var plane = GetById.GetPlaneById(conn, planeId);

                var fromAirportId = rs.getInt("repuloter_id_fel");
                var fromAirport = GetById.GetAirportById(conn, fromAirportId);

                var toAirportId = rs.getInt("repuloter_id_le");
                var toAirport = GetById.GetAirportById(conn, toAirportId);

                return new Flight(
                    rs.getInt("id"),
                    dateTime,
                    fromAirport,
                    toAirport,
                    plane,
                    rs.getInt("nepszeruseg"),
                    rs.getInt("szabad_helyek")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Alert GetAlertById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_ALERT_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new Alert(
                    rs.getInt("id"),
                    rs.getString("uzenet")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Payment GetPaymentById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_PAYMENT_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new Payment(
                    rs.getInt("id"),
                    rs.getString("nev")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Category GetCategoryById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_CATEGORY_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new Category(
                    rs.getInt("id"),
                    rs.getString("nev"),
                    rs.getInt("kedvezmeny")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TravelClass GetTravelClassById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_TRAVEL_CLASS_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return new TravelClass(
                    rs.getInt("id"),
                    rs.getString("nev")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Booking GetBookingById(Connection conn, int id) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_BOOKING_BY_ID)){

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                var user = GetById.GetUserById(conn, rs.getInt("felh_id"));
                var flight = GetById.GetFlightById(conn, rs.getInt("jarat_id"));
                var payment = GetById.GetPaymentById(conn, rs.getInt("fizetesi_mod_id"));

                return new Booking(
                    id,
                    user,
                    flight,
                    payment
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int GetTicketNumberByBookingId(int booking_id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_TICKET_COUNT_BY_BOOKING_ID)){

            st.setInt(1, booking_id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return rs.getInt("jegyek_szama");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int GetBookingNumberByUserId(int user_id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(SELECT_BOOKING_COUNT_BY_USER_ID)){

            st.setInt(1, user_id);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return rs.getInt("foglalasok_szama");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
