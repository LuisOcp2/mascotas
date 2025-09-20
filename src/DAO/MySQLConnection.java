package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnection {
    private Connection con;
    public static final String puerto = "3306";
    public static final String nomservidor = "localhost";
    public static final String db = "labpets";
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Cambiado para MySQL 8+
    private static final String user = "root";
    private static final String pass = "1002778599";
    private static final String url = "jdbc:mysql://" + nomservidor + ":" + puerto + "/" + db + 
                                    "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    public Connection conectar() {
        con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            if (con != null) {
                System.out.println("Conexion establecida..");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar " + e.getMessage());
        }
        return con;
    }

    public void desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexion terminada..");
            }
        } catch (SQLException e) {
            System.out.println("Error al desconectar: " + e.getMessage());
        }
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (con == null || con.isClosed()) {
            conectar(); // Reconectar si es necesario
        }
        return con.prepareStatement(sql);
    }
}