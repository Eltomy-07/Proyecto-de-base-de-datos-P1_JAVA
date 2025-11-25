package vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modelo.Usuario;
import dao.UsuarioDAO;


public class PantallaPrincipal extends JFrame implements ActionListener {


    //Declaracion de atributos
    private final JTable tablaUsuarios;
    private final DefaultTableModel modeloTabla;
    private final JButton btnNuevo, btnActualizar, btnEliminar, btnCerrarSesion;
    private final UsuarioDAO dao;

    public PantallaPrincipal() {
        dao = new UsuarioDAO();

        //Configuración de la Ventana
        setTitle("Clientes Registrados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Usaremos BorderLayout para la estructura

        //Creación del Modelo de la Tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new Object[]{"Nombre", "Apellido", "Teléfono", "Correo Electrónico", "Usuario"});

        tablaUsuarios = new JTable(modeloTabla);

        //Área de la Tabla (Superior/Central)
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        add(scrollPane, BorderLayout.CENTER); // La tabla ocupa el centro de la ventana

        //Panel de Botones (Inferior)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Layout para los botones

        btnNuevo = new JButton("NUEVO");
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR");
        btnCerrarSesion = new JButton("CERRAR SECCION"); // Requisito

        btnNuevo.addActionListener(this);
        btnActualizar.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnCerrarSesion.addActionListener(this);

        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.SOUTH); // El panel de botones va en la parte inferior

        //Cargar los datos al iniciar
        cargarUsuarios();

        setVisible(true);
    }

    // --- METODO CRUCIAL: CARGAR DATOS EN LA TABLA ---
    public void cargarUsuarios() {

        // Limpiar filas existentes
        modeloTabla.setRowCount(0);

        //Obtener la lista del DAO
        List<Usuario> lista = dao.listarUsuarios();

        //Iterar y añadir cada usuario como una fila
        for (Usuario u : lista) {
            modeloTabla.addRow(new Object[]{
                    u.getNombre(),
                    u.getApellido(),
                    u.getTelefono(),
                    u.getEmail(),
                    u.getNombre_Usuario()
            });
        }
    }




    // --- MANEJO DE EVENTOS ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCerrarSesion) {
            // REQUISITO: Al cerrar sesión, debe mostrarse de nuevo el login.
            this.dispose();
            new Login().setVisible(true);

        } else if (e.getSource() == btnNuevo) {
            //Esto reabre la ventana de Registro
            new Registro();
            //Aquí no llamamos a setVisible() porque Registro lo hace internamente.

        } else if (e.getSource() == btnEliminar) {
            eliminarUsuarioSeleccionado();

        } else if (e.getSource() == btnActualizar) {

            int filaSeleccionada = tablaUsuarios.getSelectedRow();

            //Validar que se haya seleccionado una fila
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Detiene la ejecución si no hay fila seleccionada
            }

            //Obtener el Nombre de Usuario (Columna 4, índice 4)
            String usuarioAEditar = modeloTabla.getValueAt(filaSeleccionada, 4).toString();

            //Obtener el objeto Usuario completo del DAO
            Usuario usuarioObtenido = dao.obtenerUsuarioPorNombre(usuarioAEditar);

            if (usuarioObtenido != null) {
                //Abrir la ventana FormUpdate (Pasando el usuario y la referencia a esta ventana)
                new FormUpdate(usuarioObtenido, this);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró el usuario en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarUsuarioSeleccionado() {
        //Implementación del método ELIMINAR
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Obtener el Nombre de Usuario (columna 4, índice 4) de la fila seleccionada
        String usuarioAeliminar = modeloTabla.getValueAt(filaSeleccionada, 4).toString();

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al usuario " + usuarioAeliminar + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            //Llamar al DAO para ejecutar la eliminación
            if (dao.eliminar(usuarioAeliminar)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                //REQUISITO: Actualizar la tabla automáticamente.
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}