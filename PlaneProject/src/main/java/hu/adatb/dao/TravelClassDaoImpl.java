package hu.adatb.dao;

import hu.adatb.model.TravelClass;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static hu.adatb.query.Queries.SELECT_TRAVEL_CLASS;
import static hu.adatb.query.Queries.SELECT_TICKET_COUNT_GROUP_BY_TRAVEL_CLASS;

public class TravelClassDaoImpl implements TravelClassDao {
    @Override
    public List<TravelClass> getAll() {
        List<TravelClass> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_TRAVEL_CLASS);

            while (rs.next()) {
                TravelClass travelClass = new TravelClass(
                        rs.getInt("id"),
                        rs.getString("nev")
                );

                result.add(travelClass);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni az utazási osztályokat");
        }

        return result;
    }

    @Override
    public HashMap<String, Integer> getCountOfTicketGroupByTravelClass() {
        var dictionary = new HashMap<String, Integer>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_TICKET_COUNT_GROUP_BY_TRAVEL_CLASS);

            while (rs.next()) {
                var id = rs.getInt("utazasi_osztaly_id");
                var travelClassName = rs.getString("nev");
                var countOfTicket = rs.getInt("darabszam");

                if(id == 0) {
                    countOfTicket = 0;
                }

                dictionary.put(
                        travelClassName,
                        countOfTicket
                );
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni az utazási osztályokat");
        }

        return dictionary;
    }
}
