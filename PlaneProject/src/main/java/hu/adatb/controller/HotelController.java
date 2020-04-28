package hu.adatb.controller;

import hu.adatb.dao.HotelDao;
import hu.adatb.dao.HotelDaoImpl;
import hu.adatb.model.Hotel;
import hu.adatb.model.Plane;

import java.util.List;

public class HotelController {

    private HotelDao dao = new HotelDaoImpl();
    private static HotelController single_instance = null;


    public static HotelController getInstance(){
        if(single_instance == null){
            single_instance = new HotelController();
        }
        return single_instance;
    }

    public boolean add(Hotel hotel) {
        return dao.add(hotel);
    }

    public boolean update(Hotel hotel) {
        return dao.update( hotel);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Hotel> getAll() {
        return dao.getAll();
    }
}
