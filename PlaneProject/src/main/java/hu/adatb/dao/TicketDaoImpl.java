package hu.adatb.dao;

import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.model.Ticket;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_TICKET;

public class TicketDaoImpl implements TicketDao {
    @Override
    public boolean add(Ticket ticket) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> result = new ArrayList<>();

        try {
            Statement stmt = Database.ConnectionToDatabaseWithStatement();

            ResultSet rs = stmt.executeQuery(SELECT_TICKET);

            while (rs.next()) {
                var categoryId = rs.getInt("kategoria_id");
                var travelClassId = rs.getInt("utazasi_osztaly_id");
                var bookingId = rs.getInt("foglalasi_id");

                var category = GetById.GetCategoryById(categoryId);
                var travelClass = GetById.GetTravelClassById(travelClassId);
                var booking = GetById.GetBookingById(bookingId);

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

        System.out.println("Ticket count: " + result.size());

        return result;
    }
}
