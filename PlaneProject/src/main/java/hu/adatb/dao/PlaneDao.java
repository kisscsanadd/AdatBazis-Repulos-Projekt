package hu.adatb.dao;

import hu.adatb.model.Plane;

import java.util.List;

public interface PlaneDao {

    public boolean add(Plane plane);

    public List<Plane> getAll();
}
