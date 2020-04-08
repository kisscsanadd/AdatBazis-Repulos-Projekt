package hu.adatb.controller;

import hu.adatb.dao.AirportDao;
import hu.adatb.dao.AirportDaoImpl;
import hu.adatb.model.Airport;

import java.util.List;

public class AirportController {
    private AirportDao dao = new AirportDaoImpl();
    private static AirportController single_instance = null;


    public static AirportController getInstance(){
        if(single_instance == null){
            single_instance = new AirportController();
        }
        return single_instance;
    }

    public boolean add(Airport airport) {
        return dao.add(airport);
    }

    public List<Airport> getAll() {
        return dao.getAll();
    }
}
