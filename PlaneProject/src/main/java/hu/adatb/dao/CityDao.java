package hu.adatb.dao;

import hu.adatb.model.City;

import java.util.List;

public interface CityDao {
    public boolean add (City city);

    public List<City> getAll();
}
