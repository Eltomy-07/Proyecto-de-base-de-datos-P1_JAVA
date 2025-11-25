package vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import dao.UsuarioDAO;

public class FormUpdate extends JFrame implements ActionListener { // Nombre de clase cambiado a FormUpdate

    private final Usuario usuarioOriginal;
    private final PantallaPrincipal principal; // Referencia a la ventana principal
    private final UsuarioDAO dao;

    // Componentes de la interfaz
    private final JTextField txtNombre, txtApellido, txtTelefono, txtEmail;
    private final JPasswordField txtPassword, txtConfirmarPassword;
    private final JButton btnGuardar, btnCancelar;
    private final JLabel lblUsuarioFijo;


    public FormUpdate(Usuario u, PantallaPrincipal p) {
        this.usuarioOriginal = u;
        this.principal = p;
        this.dao = new UsuarioDAO();

        //Configuración de la Ventana
        setTitle("ACTUALIZAR USUARIO: " + u.getNombre_Usuario());
        setSize(450, 450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Componentes y Llenado de Campos
        int y = 30;

        //Nombre de Usuario (Fijo - NO EDITABLE)
        add(new JLabel("Usuario:")).setBounds(50, y, 100, 25);
        lblUsuarioFijo = new JLabel(u.getNombre_Usuario());
        lblUsuarioFijo.setBounds(180, y, 200, 25);
        add(lblUsuarioFijo);
        y += 40;

        //Nombre
        add(new JLabel("Nombre:")).setBounds(50, y, 100, 25);
        txtNombre = new JTextField(u.getNombre());
        txtNombre.setBounds(180, y, 200, 25);
        add(txtNombre);
        y += 40;

        //Apellido
        add(new JLabel("Apellido:")).setBounds(50, y, 100, 25);
        txtApellido = new JTextField(u.getApellido());
        txtApellido.setBounds(180, y, 200, 25);
        add(txtApellido);
        y += 40;

        //Telefono
        add(new JLabel("Telefono:")).setBounds(50, y, 100, 25);
        txtTelefono = new JTextField(u.getTelefono());
        txtTelefono.setBounds(180, y, 200, 25);
        add(txtTelefono);
        y += 40;

        //Email
        add(new JLabel( "Email:")).setBounds(50, y, 100, 25);
        txtEmail = new JTextField(u.getEmail());
        txtEmail.setBounds(180, y, 200, 25);
        add(txtEmail);
        y += 40;

        // Contraseña
        add(new JLabel("Contraseña:")).setBounds(50, y, 120, 25);
        txtPassword = new JPasswordField(u.getPassword());
        txtPassword.setBounds(180, y, 200, 25);
        add(txtPassword);
        y += 40;

        // Confirmar Contraseña
        add(new JLabel("Confirmar Contraseña:")).setBounds(50, y, 150, 25);
        txtConfirmarPassword = new JPasswordField(u.getPassword());
        txtConfirmarPassword.setBounds(180, y, 200, 25);
        add(txtConfirmarPassword);
        y += 40;

        // Botones
        btnGuardar = new JButton("GUARDAR CAMBIOS");
        btnGuardar.setBounds(50, y, 180, 30);
        btnGuardar.addActionListener(this);
        add(btnGuardar);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(240, y, 140, 30);
        btnCancelar.addActionListener(this);
        add(btnCancelar);

        setVisible(true);
    }

    // --- MANEJO DE EVENTOS ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            actualizarUsuario();
        } else if (e.getSource() == btnCancelar) {
            this.dispose();
        }
    }

    private void actualizarUsuario() {
        // Capturar datos y convertir contraseñas
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());

        // 1. VALIDACIÓN DE CAMPOS OBLIGATORIOS (Debes completar la validación aquí)
        if (nombre.isEmpty() || apellido.isEmpty() /*... etc. */) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. VALIDACIÓN DE COINCIDENCIA DE CONTRASEÑA
        if (!password.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(this, "La contraseña y la confirmación no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Crear Objeto Usuario con los nuevos datos
        Usuario usuarioModificado = new Usuario();
        usuarioModificado.setNombre(nombre);
        usuarioModificado.setApellido(apellido);
        usuarioModificado.setTelefono(telefono);
        usuarioModificado.setEmail(email);
        usuarioModificado.setPassword(password);
        usuarioModificado.setNombre_Usuario(usuarioOriginal.getNombre_Usuario());

        if (dao.actualizar(usuarioModificado)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // REQUISITO: Refrescar la tabla principal
            principal.cargarUsuarios();
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario.", "Error de DB", JOptionPane.ERROR_MESSAGE);
        }
    }
}