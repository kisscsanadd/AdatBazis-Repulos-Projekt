package hu.adatb.query;

public class Queries {
    public static final String SELECT_CITY = "SELECT * FROM VAROS";
    public static final String SELECT_USER = "SELECT * FROM FELHASZNALO";
    public static final String SELECT_PLANE = "SELECT * FROM REPULOGEP";
    public static final String INSERT_USER = "INSERT INTO FELHASZNALO (felh_nev, jelszo, isAdmin, email) values(?, ?, ?, ?)";

}
