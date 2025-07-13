package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SistemaGestionEnergiaDB;encrypt=true;trustServerCertificate=true;";
    private static final String USUARIO = "sa";
    private static final String CONTRASENA = "admin";

    private static ConexionBD instance;
    private Connection connection;

    private ConexionBD() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver JDBC de SQL Server cargado.");
        } catch (ClassNotFoundException ex) {
            System.err.println("Error: No se pudo cargar el driver JDBC de SQL Server.");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de Driver de BD: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static synchronized ConexionBD getInstance() {
        if (instance == null) {
            instance = new ConexionBD();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión a la base de datos establecida.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al conectar con la base de datos: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos:\n" + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            connection = null;
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}