package hu.adatb.dao;

import hu.adatb.model.*;
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

import static hu.adatb.query.Queries.SELECT_BOOKING;

public class BookingDaoImpl implements BookingDao {
    @Override
    public boolean add(Booking booking) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Booking> getAll() {
        List<Booking> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_BOOKING);

            while (rs.next()) {
                var userId = rs.getInt("felh_id");
                var flightId = rs.getInt("jarat_id");
                var paymentId = rs.getInt("fizetesi_mod_id");

                User user = GetById.GetUserById(userId);
                Flight flight = GetById.GetFlightById(flightId);
                Payment payment = GetById.GetPaymentById(paymentId);

                if (user == null || flight == null || payment == null) {
                    throw new GetByIdException("Cannot get booking (because of user, flight or payment listing by id)");
                }

                Booking booking = new Booking(
                        rs.getInt("id"),
                        user,
                        flight,
                        payment
                );

                result.add(booking);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a foglalásokat");
        }

        System.out.println("Booking count: " + result.size());

        return result;
    }
}
