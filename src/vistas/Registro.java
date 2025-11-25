package vistas;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro extends JFrame implements ActionListener {

    // Declaración de variables
    private final JTextField txtNombre, txtApellido, txtUsuario, txtTelefono, txtEmail;
    private final JPasswordField txtPassword, txtConfirmarPassword;
    private final JButton btnRegistrar, btnVolver;
    private final UsuarioDAO dao;

    // CONSTRUCTOR (no recibe argumentos)
    public Registro() {
        // Inicialización de DAO
        dao = new UsuarioDAO();

        //Configuración de la Ventana
        setTitle("REGISTRO DE USUARIO");
        setSize(450, 500); // Aumentar un poco el tamaño para el botón Volver
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana

        //Componentes
        int y = 30;

        //Nombre
        add(new JLabel("Nombre:")).setBounds(50, y, 100, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(180, y, 200, 25);
        add(txtNombre);
        y += 40;

        //Apellido
        add(new JLabel("Apellido:")).setBounds(50, y, 100, 25);
        txtApellido = new JTextField();
        txtApellido.setBounds(180, y, 200, 25);
        add(txtApellido);
        y += 40;

        //Nombre de Usuario
        add(new JLabel("Nombre de Usuario:")).setBounds(50, y, 120, 25);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(180, y, 200, 25);
        add(txtUsuario);
        y += 40;

        //Telefono
        add(new JLabel("Teléfono:")).setBounds(50, y, 100, 25);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(180, y, 200, 25);
        add(txtTelefono);
        y += 40;

        //Email
        add(new JLabel("Email:")).setBounds(50, y, 100, 25);
        txtEmail = new JTextField();
        txtEmail.setBounds(180, y, 200, 25);
        add(txtEmail);
        y += 40;

        //Contraseña
        add(new JLabel("Contraseña:")).setBounds(50, y, 120, 25);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, y, 200, 25);
        add(txtPassword);
        y += 40;

        //Confirmar Contraseña
        add(new JLabel("Confirmar Contraseña:")).setBounds(50, y, 150, 25);
        txtConfirmarPassword = new JPasswordField();
        txtConfirmarPassword.setBounds(180, y, 200, 25);
        add(txtConfirmarPassword);
        y += 40;

        //Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(180, y + 20, 200, 30);
        btnRegistrar.addActionListener(this);
        add(btnRegistrar);

        //Botón Volver
        btnVolver = new JButton("Volver al Login");
        btnVolver.setBounds(50, y + 20, 120, 30);
        btnVolver.addActionListener(this);
        add(btnVolver);

        setVisible(true);
    }

    // --- LÓGICA DE REGISTRO ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            registrarUsuario(); // Llamamos al método de registro
        } else if (e.getSource() == btnVolver) {
            // REQUISITO: Al volver, abre la ventana de Login
            this.dispose();
            new Login().setVisible(true);
        }
    }

    private void registrarUsuario() {
        //Capturar datos y convertir contraseñas a String
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String usuario = txtUsuario.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());

        //VALIDACIÓN DE CAMPOS OBLIGATORIOS.............................
        if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || telefono.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {

            // Requisito: Mostrar el campo faltante.
            String campoFaltante = "";
            if (nombre.isEmpty()) campoFaltante = "Nombre";
            else if (apellido.isEmpty()) campoFaltante = "Apellido";
            else if (usuario.isEmpty()) campoFaltante = "Nombre de Usuario";
            else if (telefono.isEmpty()) campoFaltante = "Número de Teléfono";
            else if (email.isEmpty()) campoFaltante = "Correo Electrónico";
            else if (password.isEmpty()) campoFaltante = "Contraseña";
            else if (confirmarPassword.isEmpty()) campoFaltante = "Confirmar Contraseña";

            JOptionPane.showMessageDialog(this,
                    "El campo '" + campoFaltante + "' es obligatorio.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //VALIDACIÓN DE COINCIDENCIA DE CONTRASEÑA......................
        if (!password.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña y la confirmación no coinciden.",
                    "Error de Contraseña", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Crear Objeto Usuario y Registrar
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setNombre_Usuario(usuario);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);

        if (dao.registrar(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

            //Para volver automáticamente al login después del registro
            this.dispose();
            new Login().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario. El nombre de usuario puede estar ya en uso.", "Error de DB", JOptionPane.ERROR_MESSAGE);
        }
    }
}