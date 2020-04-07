package hu.adatb.controller;

import hu.adatb.dao.PlaneDao;
import hu.adatb.dao.PlaneDaoImpl;
import hu.adatb.model.Plane;

import java.util.List;

public class PlaneController {
    private PlaneDao dao = new PlaneDaoImpl();
    private static PlaneController single_instance = null;


    public static PlaneController getInstance(){
        if(single_instance == null){
            single_instance = new PlaneController();
        }
        return single_instance;
    }

    public boolean add(Plane plane) {
        return dao.add(plane);
    }

    public List<Plane> getAll() {
        return dao.getAll();
    }
}
