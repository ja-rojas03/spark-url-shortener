package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {

    private static DB db;
    private String URL = "jdbc:h2:tcp://localhost/~/urlShort";

    private DB(){
        registerDriver();
    }

    public static DB getInstance(){
        return db == null ? new DB() : db;
    }

    /**
     * Metodo para el registro de driver de conexión.
     */
    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}

