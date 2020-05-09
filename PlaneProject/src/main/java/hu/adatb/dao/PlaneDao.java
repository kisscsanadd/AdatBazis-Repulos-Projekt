package hu.adatb.dao;

import hu.adatb.model.Plane;

import java.util.HashMap;
import java.util.List;

public interface PlaneDao {

    public boolean add (Plane plane);

    public boolean update (Plane plane);

    public boolean delete (int id);

    public List<Plane> getAll();

    public HashMap<String, Integer> getCountOfPlane();

}
