package hu.adatb.dao;

import hu.adatb.model.TravelClass;
import hu.adatb.utils.DataHelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public interface TravelClassDao {

    public List<TravelClass> getAll();

    public ArrayList<DataHelper> getCountOfTicketGroupByTravelClass();
}
