package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

public class UsuarioDAO {

    //Variables de conexion y consulta
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    //REGISTRAR USUARIO
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_Usuario, telefono, email, password) VALUES (?,?,?,?,?,?)";

        try {
            con = Conexion.getInstance().getConnection(); //Usando el Singleton
            ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getNombre_Usuario());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPassword());

            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e);
            return false;
        }
    }

    //LOGIN
    public Usuario login(String usuario, String password) {
        Usuario u = new Usuario();
        String sql = "SELECT * FROM usuarios WHERE nombre_Usuario = ? AND password = ?";

        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Si existe, llenamos el objeto u con los datos que necesites para la sesión
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setNombre_Usuario(rs.getString("nombre_Usuario"));
                u.setEmail(rs.getString("email"));
                u.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.out.println("Error en Vistas.Login: " + e);
        }
        return u;
    }

    //LISTAR USUARIOS
    public List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT nombre, apellido, telefono, email, nombre_Usuario FROM usuarios";

        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setEmail(rs.getString("email"));
                u.setNombre_Usuario(rs.getString("nombre_Usuario"));
                listaUsuarios.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e);
        }
        return listaUsuarios;
    }

    //ACTUALIZAR USUARIO
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, telefono=?, email=?, password=? WHERE nombre_Usuario=?";

        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getNombre_Usuario()); // Condición WHERE

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e);
            return false;
        }
    }


//Para obtener datos de un usuario (por su nombre de usuario)
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        Usuario u = null;
        String sql = "SELECT * FROM usuarios WHERE nombre_Usuario = ?";

        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                // Cargar todos los campos, incluyendo la contraseña para mostrarla en el campo de texto.
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setNombre_Usuario(rs.getString("nombre_Usuario"));
                u.setTelefono(rs.getString("telefono"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario: " + e);
        }
        return u;
    }


        //Para eliminar usuarios
    public boolean eliminar(String nombreUsuario) {
        // Usamos DELETE FROM y especificamos la condición (WHERE)
        String sql = "DELETE FROM usuarios WHERE nombre_Usuario = ?";

        try {
            //Para obtener la conexión Singleton
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            //Establecer el valor de la condición
            ps.setString(1, nombreUsuario);

            //Ejecutar la actualización
            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0; //Retorna true si se eliminó una fila

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e);
            return false;
        }
        //No cerramos la conexión aquí, porque lo va a gestionar el Singleton
    }

}
