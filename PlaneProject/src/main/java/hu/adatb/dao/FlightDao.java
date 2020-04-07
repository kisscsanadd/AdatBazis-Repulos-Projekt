package hu.adatb.dao;

import hu.adatb.model.Flight;

import java.util.List;

public interface FlightDao {

    public boolean add (Flight flight);

    public List<Flight> getAll();
}
