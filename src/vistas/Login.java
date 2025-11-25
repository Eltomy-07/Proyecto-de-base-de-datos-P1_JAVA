package vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color; // Para definir los colores del tema oscuro

// Importamos las clases de nuestra lógica de negocio
import modelo.Usuario;
import dao.UsuarioDAO;

public class Login extends JFrame implements ActionListener {

    private final JTextField txtUsuario;
    private final JPasswordField txtPassword;
    private final JButton btnEntrar;
    private final JButton btnRegistrar;
    private final UsuarioDAO dao;

    public Login() {
        // Inicializar el DAO
        dao = new UsuarioDAO();

        // 1. Configuración de la Ventana (JFrame)
        setTitle("LOGIN - Tarea 4");
        setSize(400, 300);
        setLayout(null); // Usaremos un layout nulo para un control de posición simple
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // 2. Componentes (ajusta las coordenadas según tu diseño)

        // Campo modelo.Usuario
        JLabel lblUsuario = new JLabel("modelo.Usuario:");
        lblUsuario.setBounds(50, 50, 100, 25);
        add(lblUsuario);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 50, 180, 25);
        add(txtUsuario);

        // Campo Contraseña (Oculta la entrada, según el requisito)
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 90, 100, 25);
        add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 90, 180, 25);
        add(txtPassword);

        // Botón Entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(150, 150, 180, 30);
        btnEntrar.addActionListener(this); // Registrar el listener
        add(btnEntrar);

        // Botón Registrarse
        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(150, 200, 180, 30);
        btnRegistrar.addActionListener(this);
        add(btnRegistrar);

        setVisible(true);
    }

    // Método para manejar las acciones de los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEntrar) {
            iniciarSesion();
        } else if (e.getSource() == btnRegistrar) {
            abrirRegistro();
        }
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText();
        // Obtener la contraseña como String (cuidado con esto, solo es para el login)
        String password = new String(txtPassword.getPassword());

        // REQUISITO: Validación de campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse.",
                    "Error de Vistas.Login", JOptionPane.ERROR_MESSAGE); // Mensaje de error, según requisito.
            return;
        }

        // Llamar al DAO
        Usuario usuarioLogeado = dao.login(usuario, password);

        if (usuarioLogeado != null && usuarioLogeado.getNombre() != null) {
            // Vistas.Login exitoso
            JOptionPane.showMessageDialog(this, "¡Bienvenido, " + usuarioLogeado.getNombre() + "!");

            // REQUISITO: Cerrar ventana de login y mostrar la ventana principal
            this.dispose();
            // Llama a la ventana principal
            new PantallaPrincipal();

        } else {
            // Credenciales incorrectas
            JOptionPane.showMessageDialog(this,
                    "modelo.Usuario o contraseña incorrectos.",
                    "Error de Credenciales", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        this.dispose(); // Cierra el Vistas. Login
        new Registro(); // Crea la ventana de Vistas. Registro
    }

    // --- PUNTO DE ENTRADA CON TEMA OSCURO ---
    public static void main(String[] args) {
        try {
            // 1. Buscar e intentar aplicar el Look and Feel "Nimbus"
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    // --- 2. CONFIGURACIÓN DE COLORES OSCUROS (Nimbus) ---
                    // Fondo de paneles y contenedores (Negro/Gris Oscuro)
                    UIManager.put("control", new Color(40, 40, 40));
                    // Color principal del texto (Blanco)
                    UIManager.put("textForeground", new Color(240, 240, 240));
                    // Fondo de áreas de texto
                    UIManager.put("nimbusLightBackground", new Color(60, 60, 60));
                    // Color base para botones
                    UIManager.put("nimbusBase", new Color(16, 25, 103));
                    // Color de los bordes
                    UIManager.put("nimbusBorder", new Color(80, 80, 80));
                    // Color de la selección de texto
                    UIManager.put("nimbusSelectionBackground", new Color(10, 27, 180));
                    // --------------------------------------------------

                    break;
                }
            }
        } catch (Exception e) {
            // Manejo de errores si Nimbus no carga. La aplicación usará el tema por defecto.
            System.err.println("Error al aplicar el tema Nimbus. Usando tema por defecto.");
            e.printStackTrace();
        }

        // 3. Iniciar la aplicación (crear la primera ventana)
        new Login(); // Punto de entrada
    }
}