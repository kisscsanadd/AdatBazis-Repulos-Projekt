package hu.adatb.query;

public class Queries {
    public static final String SELECT_CITY = "SELECT * FROM VAROS";
    public static final String SELECT_CITY_BY_ID = "SELECT * FROM VAROS WHERE ID = ";
    public static final String SELECT_AIRPORT = "SELECT * FROM REPULOTER";
    public static final String SELECT_FLIGHT = "SELECT JARAT.ID, JARAT.FELSZALLAS_DATUM, p1.NEV as from_airport, p2.NEV as to_airport, JARAT.REPULOGEP_ID, JARAT.SZABAD_HELYEK FROM JARAT LEFT JOIN REPULOTER p1 on JARAT.REPULOTER_ID_FEL = p1.ID LEFT JOIN REPULOTER p2 ON JARAT.REPULOTER_ID_LE = p2.ID";
    public static final String SELECT_USER = "SELECT * FROM FELHASZNALO";
    public static final String SELECT_PLANE = "SELECT * FROM REPULOGEP";
    public static final String SELECT_COUNTRY = "SELECT * FROM ORSZAG";
    public static final String SELECT_HOTEL = "SELECT * FROM SZALLODA";
    public static final String SELECT_PAYMENT = "SELECT * FROM FIZETESIMOD";
    public static final String SELECT_CATEGORY = "SELECT * FROM KATEGORIA";
    public static final String SELECT_ALERT = "SELECT * FROM FIGYELMEZTETES";
    public static final String INSERT_USER = "INSERT INTO FELHASZNALO (felh_nev, jelszo, isAdmin, email) values(?, ?, ?, ?)";

}
