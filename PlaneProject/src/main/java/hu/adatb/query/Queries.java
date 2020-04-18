package hu.adatb.query;

public class Queries {
    public static final String SELECT_CITY = "SELECT * FROM VAROS";
    public static final String SELECT_CITY_BY_ID = "SELECT * FROM VAROS WHERE ID = ?";
    public static final String SELECT_AIRPORT = "SELECT * FROM REPULOTER";
    public static final String SELECT_FLIGHT = "SELECT JARAT.ID, JARAT.FELSZALLAS_DATUM, p1.NEV as from_airport, p2.NEV as to_airport, repulogep_id, JARAT.SZABAD_HELYEK FROM JARAT LEFT JOIN REPULOTER p1 ON JARAT.REPULOTER_ID_FEL = p1.ID LEFT JOIN REPULOTER p2 ON JARAT.REPULOTER_ID_LE = p2.ID";
    public static final String SELECT_FLIGHT_BY_ID = "SELECT JARAT.ID, JARAT.FELSZALLAS_DATUM, p1.NEV as from_airport, p2.NEV as to_airport, repulogep_id, JARAT.SZABAD_HELYEK FROM JARAT LEFT JOIN REPULOTER p1 ON JARAT.REPULOTER_ID_FEL = p1.ID LEFT JOIN REPULOTER p2 ON JARAT.REPULOTER_ID_LE = p2.ID WHERE JARAT.ID = ?";
    public static final String SELECT_USER = "SELECT * FROM FELHASZNALO";
    public static final String SELECT_USER_BY_ID = "SELECT * FROM FELHASZNALO WHERE ID = ?";
    public static final String SELECT_PLANE = "SELECT * FROM REPULOGEP";
    public static final String SELECT_PLANE_BY_ID = "SELECT * FROM REPULOGEP WHERE ID = ?";
    public static final String SELECT_COUNTRY = "SELECT * FROM ORSZAG";
    public static final String SELECT_HOTEL = "SELECT * FROM SZALLODA";
    public static final String SELECT_PAYMENT = "SELECT * FROM FIZETESIMOD";
    public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM FIZETESIMOD WHERE ID = ?";
    public static final String SELECT_CATEGORY = "SELECT * FROM KATEGORIA";
    public static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM KATEGORIA WHERE ID = ?";
    public static final String SELECT_ALERT = "SELECT * FROM FIGYELMEZTETES";
    public static final String SELECT_TRAVEL_CLASS = "SELECT * FROM UTAZASIOSZTALY";
    public static final String SELECT_TRAVEL_CLASS_BY_ID = "SELECT * FROM UTAZASIOSZTALY WHERE ID = ?";
    public static final String SELECT_BOOKING = "SELECT * FROM FOGLALAS";
    public static final String SELECT_BOOKING_BY_ID = "SELECT * FROM FOGLALAS WHERE ID = ";
    public static final String SELECT_TICKET = "SELECT * FROM JEGY";

    public static final String DELETE_USER = "DELETE FROM FELHASZNALO WHERE ID = ?";

    public static final String INSERT_USER = "INSERT INTO FELHASZNALO (felh_nev, jelszo, isAdmin, email) values(?, ?, ?, ?)";

    public static final String INSERT_PLANE = "INSERT INTO REPULOGEP (nev, sebesseg, ferohely) values(?, ?, ?)";

    public static final String INSERT_HOTEL = "INSERT INTO SZALLODA (nev, csillagok_szama, varos_id) values(?, ?, ?)";

    public static final String INSERT_AIRPORT = "INSERT INTO REPULOTER (nev, szelesseg, hosszusag, varos_id) values(?, ?, ?, ?)";

    public static final String INSERT_ALERT = "INSERT INTO FIGYELMEZTETES (uzenet) values(?)";

    public static final String INSERT_FLIGHT = "INSERT INTO JARAT (felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values(?, ?, ?, ?, ?)";

    public static final String INSERT_BOOKING = "INSERT INTO FOGLALAS (felh_id, jarat_id, fizetesi_mod_id) values(?, ?, ?)";

    public static final String INSERT_TICKET = "INSERT INTO JEGY (kategoria_id, utazasi_osztaly_id, foglalasi_id) values(?, ?, ?)";

}
