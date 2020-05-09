package hu.adatb.query;

public class Queries {
    public static final String SELECT_CITY = "SELECT * FROM VAROS";
    public static final String SELECT_CITY_BY_ID = "SELECT * FROM VAROS WHERE ID = ?";
    public static final String SELECT_AIRPORT = "SELECT * FROM REPULOTER";
    public static final String SELECT_AIRPORT_BY_ID = "SELECT * FROM REPULOTER WHERE ID = ?";
    public static final String SELECT_FLIGHT = "SELECT * FROM JARAT ORDER BY felszallas_datum";
    public static final String SELECT_FLIGHT_BY_ID = "SELECT * FROM JARAT WHERE ID = ?";
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
    public static final String SELECT_ALERT_BY_ID = "SELECT * FROM FIGYELMEZTETES WHERE ID = ?";
    public static final String SELECT_TRAVEL_CLASS = "SELECT * FROM UTAZASIOSZTALY";
    public static final String SELECT_TRAVEL_CLASS_BY_ID = "SELECT * FROM UTAZASIOSZTALY WHERE ID = ?";
    public static final String SELECT_BOOKING = "SELECT * FROM FOGLALAS";
    public static final String SELECT_BOOKING_BY_ID = "SELECT * FROM FOGLALAS WHERE ID = ?";
    public static final String SELECT_TICKET = "SELECT * FROM JEGY";
    public static final String SELECT_FLIGHT_ALERT_RELATION = "SELECT * FROM JARATFIGYREL";

    public static final String SELECT_TICKET_COUNT_BY_BOOKING_ID = "SELECT COUNT(*) AS jegyek_szama FROM JEGY WHERE FOGLALASI_ID = ?";
    public static final String SELECT_BOOKING_COUNT_BY_USER_ID = "SELECT COUNT(*) AS foglalasok_szama FROM FOGLALAS WHERE FELH_ID = ?";
    public static final String SELECT_SOLD_TICKET_NUMBER_IN_MONTH = "SELECT COUNT(*) AS eladott_jegyek_szama FROM JEGY LEFT JOIN FOGLALAS ON foglalasi_id = foglalas.id LEFT JOIN JARAT ON jarat_id = jarat.id WHERE EXTRACT(MONTH FROM felszallas_datum) = ? AND EXTRACT (YEAR FROM felszallas_datum) = (SELECT EXTRACT(YEAR FROM sysdate) FROM DUAL)";
    public static final String SELECT_SUM_TICKET_NUMBER_IN_MONTH = "SELECT SUM(ferohely) AS osszes_jegy FROM REPULOGEP LEFT JOIN JARAT ON repulogep.id = jarat.repulogep_id WHERE EXTRACT(MONTH FROM felszallas_datum) = ? AND EXTRACT (YEAR FROM felszallas_datum) = (SELECT EXTRACT(YEAR FROM sysdate) FROM DUAL)";
    public static final String SELECT_TICKET_COUNT_GROUP_BY_TRAVEL_CLASS = "SELECT utazasi_osztaly_id, nev, COUNT(*) AS darabszam, EXTRACT(MONTH FROM felszallas_datum) AS honap FROM utazasiosztaly LEFT JOIN JEGY ON utazasiosztaly.id = utazasi_osztaly_id LEFT JOIN FOGLALAS ON foglalas.id = jegy.foglalasi_id LEFT JOIN JARAT ON jarat.id = foglalas.jarat_id GROUP BY utazasi_osztaly_id, felszallas_datum, nev ORDER BY utazasi_osztaly_id";
    public static final String SELECT_COUNT_TO_AIRPORT = "SELECT repuloter.nev, COUNT(*) AS darabszam FROM jarat LEFT JOIN repuloter ON repuloter_id_le = repuloter.id GROUP BY repuloter.nev";

    public static final String INSERT_USER = "INSERT INTO FELHASZNALO (felh_nev, jelszo, isAdmin, email) values(?, ?, ?, ?)";
    public static final String INSERT_PLANE = "INSERT INTO REPULOGEP (nev, sebesseg, ferohely) values(?, ?, ?)";
    public static final String INSERT_HOTEL = "INSERT INTO SZALLODA (nev, csillagok_szama, varos_id) values(?, ?, ?)";
    public static final String INSERT_AIRPORT = "INSERT INTO REPULOTER (nev, szelesseg, hosszusag, varos_id) values(?, ?, ?, ?)";
    public static final String INSERT_ALERT = "INSERT INTO FIGYELMEZTETES (uzenet) values(?)";
    public static final String INSERT_FLIGHT = "INSERT INTO JARAT (felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values(?, ?, ?, ?, ?)";
    public static final String INSERT_BOOKING = "INSERT INTO FOGLALAS (felh_id, jarat_id, fizetesi_mod_id) values(?, ?, ?)";
    public static final String INSERT_TICKET = "INSERT INTO JEGY (kategoria_id, utazasi_osztaly_id, foglalasi_id) values(?, ?, ?)";
    public static final String INSERT_FLIGHT_ALERT_RELATION = "INSERT INTO JARATFIGYREL (jarat_id, figyelmeztetes_id) values(?, ?)";

    public static final String UPDATE_PLANE = "UPDATE REPULOGEP SET nev = ?, sebesseg = ?, ferohely = ? WHERE id = ?";
    public static final String UPDATE_AIRPORT = "UPDATE REPULOTER SET nev = ?, szelesseg = ?, hosszusag = ?, varos_id = ?  WHERE id = ?";
    public static final String UPDATE_ALERT = "UPDATE FIGYELMEZTETES SET uzenet = ? WHERE id = ?";
    public static final String UPDATE_FLIGHT = "UPDATE JARAT SET felszallas_datum = ?, repuloter_id_fel = ?, repuloter_id_le = ?, repulogep_id = ?, szabad_helyek = ?,  nepszeruseg = ? WHERE id = ?";
    public static final String UPDATE_HOTEL = "UPDATE SZALLODA SET nev = ?, csillagok_szama = ? WHERE id = ?";

    public static final String DELETE_USER = "DELETE FROM FELHASZNALO WHERE ID = ?";
    public static final String DELETE_BOOKING = "DELETE FROM FOGLALAS WHERE ID = ?";
    public static final String DELETE_FLIGHT_ALERT_RELATION = "DELETE FROM JARATFIGYREL WHERE JARAT_ID = ? AND FIGYELMEZTETES_ID = ?";
    public static final String DELETE_HOTEL = "DELETE FROM SZALLODA WHERE ID = ?";
    public static final String DELETE_FLIGHT = "DELETE FROM JARAT WHERE ID = ?";
    public static final String DELETE_AIRPORT = "DELETE FROM REPULOTER WHERE ID = ?";
    public static final String DELETE_ALERT = "DELETE FROM FIGYELMEZTETES WHERE ID = ?";
    public static final String DELETE_PLANE = "DELETE FROM REPULOGEP WHERE ID = ?";


}
