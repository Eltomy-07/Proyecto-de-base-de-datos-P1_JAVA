package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Instancia privada (Singleton)
    private static Conexion instance = null;
    // La conexión real a la base de datos
    private Connection conexion = null;

    // Configuración de conexión con SQL Server
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "sistema_usuarios";
    private static final String USER = "usr_TM7"; //Mi usuario
    private static final String PASS = "pwd_TM7"; //Mi contraceña

    // Cadena de conexión para SQL Server
    private final String URL = "jdbc:sqlserver://" + SERVER + ":" + PORT + ";databaseName=" + DATABASE + ";user=" + USER + ";password=" + PASS + ";encrypt=false;trustServerCertificate=true;";

    // 2. Constructor privado para evitar que se creen instancias con 'new'
    private Conexion() {
        try {
            // CORRECCIÓN: Usar DriverManager.getConnection() y pasar la variable URL
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa a SQL Server.");
        } catch (SQLException e) {
            System.err.println("Error de Conexión a SQL Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 3. Método estático público para obtener la única instancia
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    // 4. Método público para obtener la conexión (usado en dao.UsuarioDAO)
    public Connection getConnection() {
        // CORRECCIÓN: Solo retornamos el objeto de conexión
        return conexion;
    }

    // Método para cerrar la conexion
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                // CORRECCIÓN: Usamos el método close() del objeto Connection
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}