package hu.adatb.dao;

import hu.adatb.model.Hotel;

import java.util.List;

public interface HotelDao {

    public boolean add(Hotel hotel);

    public List<Hotel> getAll();
}
