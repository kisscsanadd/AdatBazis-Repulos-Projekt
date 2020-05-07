package hu.adatb.dao;

import hu.adatb.model.TravelClass;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public interface TravelClassDao {

    public List<TravelClass> getAll();

    public HashMap<String, Integer> getCountOfTicketGroupByTravelClass();
}
