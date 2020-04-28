package hu.adatb.dao;

import hu.adatb.model.Alert;
import hu.adatb.model.Plane;

import java.util.List;

public interface AlertDao {

    public boolean add(Alert alert);

    public boolean update (Alert alert);

    public boolean delete(int id);

    public List<Alert> getAll();
}
