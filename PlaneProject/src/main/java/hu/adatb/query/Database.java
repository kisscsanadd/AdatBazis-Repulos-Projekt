package hu.adatb.query;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static Statement ConnectionToDatabaseWithStatement() throws ClassNotFoundException, SQLException {
        OracleDataSource ods = new OracleDataSource();
        Class.forName ("oracle.jdbc.OracleDriver");
        ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
        Connection conn = ods.getConnection("SYSTEM","system");
        Statement stmt = conn.createStatement();

        return stmt;
    }


    public static PreparedStatement ConnectionToDatabaseWithPreparedStatement(String query) throws ClassNotFoundException, SQLException {
        OracleDataSource ods = new OracleDataSource();
        Class.forName ("oracle.jdbc.OracleDriver");
        ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
        Connection conn = ods.getConnection("SYSTEM","system");
        PreparedStatement stmt = conn.prepareStatement(query);

        return stmt;
    }
}
