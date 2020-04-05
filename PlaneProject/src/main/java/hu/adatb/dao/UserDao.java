package hu.adatb.dao;

import hu.adatb.model.User;

import java.util.List;

public interface UserDao {

    public boolean add (User user);

    public List<User> getAll();
}
