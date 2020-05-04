package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.model.Ticket;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class TicketDaoImpl implements TicketDao {
    @Override
    public boolean add(Ticket ticket) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_TICKET)){

            st.setInt(1, ticket.getCategory().getId());
            st.setInt(2, ticket.getTravelClass().getId());
            st.setInt(3, ticket.getBooking().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful ticket saving");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed ticket saving");
            e.printStackTrace();
        }

        Utils.showWarning("Nem sikerült a jegyfoglalás");
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_TICKET);

            while (rs.next()) {
                var categoryId = rs.getInt("kategoria_id");
                var travelClassId = rs.getInt("utazasi_osztaly_id");
                var bookingId = rs.getInt("foglalasi_id");

                var category = GetById.GetCategoryById(conn, categoryId);
                var travelClass = GetById.GetTravelClassById(conn, travelClassId);
                var booking = GetById.GetBookingById(conn, bookingId);

                if (category == null || travelClass == null || booking == null) {
                    throw new GetByIdException("Cannot get ticket (because of category, travelClass or booking listing failed)");
                }

                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        category,
                        travelClass,
                        booking
                );

                result.add(ticket);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a jegyeket");
        }

        return result;
    }

    @Override
    public int getCountOfTicketInMonth(int month) {
        try(Connection conn = Database.ConnectionToDatabase();
            PreparedStatement st = conn.prepareStatement(SELECT_SOLD_TICKET_NUMBER_IN_MONTH)) {

            st.setInt(1, month);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return rs.getInt("eladott_jegyek_szama");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
