package hu.adatb.dao;

import hu.adatb.model.Category;
import hu.adatb.model.Payment;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class CategoryDaoImpl implements  CategoryDao{
    @Override
    public List<Category> getAll() {
        List<Category> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_CATEGORY);

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("kedvezmeny")
                );

                result.add(category);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Utils.showWarning("Nem sikerült lekérni a jegy kategóriákat");
        }

        return result;
    }

    @Override
    public HashMap<String, Integer> getCountOfCategory() {
        var dictionary = new HashMap<String, Integer>();

        try(Connection conn = Database.ConnectionToDatabase();
            Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_COUNT_CATEGORY);

            while (rs.next()) {
                dictionary.put(
                        rs.getString("nev"),
                        rs.getInt("darabszam")
                );
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a kedvezmény kategóriák gyakoriságát");
        }

        return dictionary;
    }
}
