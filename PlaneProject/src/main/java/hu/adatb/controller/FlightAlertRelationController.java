package hu.adatb.controller;

import hu.adatb.dao.FlightAlertRelationDao;
import hu.adatb.dao.FlightAlertRelationDaoImpl;
import hu.adatb.model.FlightAlertRelation;

import java.util.List;

public class FlightAlertRelationController {

    private FlightAlertRelationDao dao = new FlightAlertRelationDaoImpl();
    private static FlightAlertRelationController single_instance = null;


    public static FlightAlertRelationController getInstance(){
        if(single_instance == null){
            single_instance = new FlightAlertRelationController();
        }
        return single_instance;
    }

    public boolean add(FlightAlertRelation relation) {
        return dao.add(relation);
    }

    public boolean delete(FlightAlertRelation relation) {
        return dao.delete(relation);
    }

    public List<FlightAlertRelation> getAll() {
        return dao.getAll();
    }
}
