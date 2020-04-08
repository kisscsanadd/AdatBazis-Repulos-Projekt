package hu.adatb.dao;

import hu.adatb.model.Airport;

import java.util.List;

public interface AirportDao {
    public boolean add(Airport airport);

    public List<Airport> getAll();
}
