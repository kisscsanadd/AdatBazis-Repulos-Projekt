package hu.adatb.dao;

import hu.adatb.model.Booking;

import java.util.List;

public interface BookingDao {

    public boolean add(Booking booking);

    public int delete(Booking booking);

    public List<Booking> getAll();
}
