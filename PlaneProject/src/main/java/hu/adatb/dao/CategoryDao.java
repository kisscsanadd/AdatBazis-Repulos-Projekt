package hu.adatb.dao;

import hu.adatb.model.Category;

import java.util.HashMap;
import java.util.List;

public interface CategoryDao {

    public List<Category> getAll();

    public HashMap<String, Integer> getCountOfCategory();
}
