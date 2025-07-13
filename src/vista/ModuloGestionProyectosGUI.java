package vista;

import controlador.ProyectoEnergiaControlador;
import modelo.ProyectoEnergia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import modelo.Command;
import modelo.CommandManager;
import modelo.CrearProyectoCommand;

public class ModuloGestionProyectosGUI extends JPanel {

    private ProyectoEnergiaControlador controlador;
    private CommandManager commandManager;

    // Componentes para Crear Proyecto
    private JTextField txtNombreProyecto;
    private JComboBox<String> cmbTipoFuente;
    private JTextField txtCapacidadMW;
    private JButton btnCrearProyecto;
    private JButton btnDeshacer;

    // Componentes para Ver/Actualizar/Eliminar
    private JTextField txtProyectoID;
    private JButton btnBuscarProyecto;
    private JButton btnActualizarProyecto;
    private JButton btnEliminarProyecto;

    private JTable tablaProyectos;
    private DefaultTableModel modeloTabla;
    private JButton btnRecargarProyectos;

    public ModuloGestionProyectosGUI() {
        this.controlador = new ProyectoEnergiaControlador(new ProyectoEnergiaVista(null));
        this.commandManager = new CommandManager();
        setLayout(new BorderLayout(10, 10));
        initComponents();
        addListeners();
        cargarTodosLosProyectos();
    }

    private void initComponents() {
        JPanel pnlCrear = new JPanel(new GridBagLayout());
        pnlCrear.setBorder(BorderFactory.createTitledBorder("Crear Nuevo Proyecto de Energía"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; pnlCrear.add(new JLabel("Nombre Proyecto:"), gbc);
        gbc.gridx = 1; txtNombreProyecto = new JTextField(20); pnlCrear.add(txtNombreProyecto, gbc);

        gbc.gridx = 0; gbc.gridy = 1; pnlCrear.add(new JLabel("Tipo de Fuente:"), gbc);
        gbc.gridx = 1; cmbTipoFuente = new JComboBox<>(new String[]{"Solar", "Eólica", "Hidroeléctrica", "Geotérmica", "Biomasa", "Nuclear", "Térmica"}); pnlCrear.add(cmbTipoFuente, gbc);

        gbc.gridx = 0; gbc.gridy = 2; pnlCrear.add(new JLabel("Capacidad (MW):"), gbc);
        gbc.gridx = 1; txtCapacidadMW = new JTextField(20); pnlCrear.add(txtCapacidadMW, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnCrearProyecto = new JButton("Crear Proyecto");
        pnlCrear.add(btnCrearProyecto, gbc);

        add(pnlCrear, BorderLayout.NORTH);

        JPanel pnlGestion = new JPanel(new BorderLayout());
        pnlGestion.setBorder(BorderFactory.createTitledBorder("Gestión de Proyectos Existentes"));

        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlAcciones.add(new JLabel("ID Proyecto:"));
        txtProyectoID = new JTextField(10);
        pnlAcciones.add(txtProyectoID);
        btnBuscarProyecto = new JButton("Buscar");
        pnlAcciones.add(btnBuscarProyecto);
        btnActualizarProyecto = new JButton("Actualizar");
        pnlAcciones.add(btnActualizarProyecto);
        btnEliminarProyecto = new JButton("Eliminar");
        pnlAcciones.add(btnEliminarProyecto);

        pnlGestion.add(pnlAcciones, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre", "Tipo Fuente", "Capacidad (MW)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProyectos = new JTable(modeloTabla);
        tablaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaProyectos);
        pnlGestion.add(scrollPane, BorderLayout.CENTER);

        btnRecargarProyectos = new JButton("Recargar Lista de Proyectos");
        btnDeshacer = new JButton("Deshacer");
        JPanel pnlBtnRecargar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnRecargar.add(btnRecargarProyectos);
        pnlBtnRecargar.add(btnDeshacer);
        pnlGestion.add(pnlBtnRecargar, BorderLayout.SOUTH);

        add(pnlGestion, BorderLayout.CENTER);

        tablaProyectos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProyectos.getSelectedRow() != -1) {
                int selectedRow = tablaProyectos.getSelectedRow();
                txtProyectoID.setText(modeloTabla.getValueAt(selectedRow, 0).toString());
            }
        });
        estiloTabla(tablaProyectos);
    }

    private void addListeners() {
        btnCrearProyecto.addActionListener(e -> crearProyecto());
        btnBuscarProyecto.addActionListener(e -> buscarProyecto());
        btnActualizarProyecto.addActionListener(e -> mostrarDialogoActualizarProyecto());
        btnEliminarProyecto.addActionListener(e -> eliminarProyecto());
        btnRecargarProyectos.addActionListener(e -> cargarTodosLosProyectos());
        btnDeshacer.addActionListener(e -> deshacerAccion());
    }

    private void deshacerAccion() {
        commandManager.undoLastCommand();
        cargarTodosLosProyectos();
        JOptionPane.showMessageDialog(this, "Última acción deshecha", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void crearProyecto() {
        try {
            String nombre = txtNombreProyecto.getText();
            String tipoFuente = (String) cmbTipoFuente.getSelectedItem();
            double capacidad = Double.parseDouble(txtCapacidadMW.getText());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del proyecto no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea crear este proyecto?", "Confirmar Creación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Command command = new CrearProyectoCommand(controlador, nombre, tipoFuente, capacidad);
                commandManager.executeCommand(command);
                limpiarCamposCreacion();
                cargarTodosLosProyectos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidad (MW) debe ser un número válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProyecto() {
        try {
            int id = Integer.parseInt(txtProyectoID.getText());
            ProyectoEnergia proyecto = controlador.leerProyectoEnergia(id);

            if (proyecto != null) {
                JOptionPane.showMessageDialog(this,
                        "Detalles del Proyecto:\n" +
                        "ID: " + proyecto.getId() + "\n" +
                        "Nombre: " + proyecto.getNombreProyecto() + "\n" +
                        "Tipo Fuente: " + proyecto.getTipoFuente() + "\n" +
                        "Capacidad: " + proyecto.getCapacidadMW() + " MW",
                        "Proyecto Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Proyecto no encontrado con ID: " + id, "No Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Proyecto debe ser un número entero.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al buscar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void mostrarDialogoActualizarProyecto() {
        try {
            int id = Integer.parseInt(txtProyectoID.getText());
            ProyectoEnergia proyectoExistente = controlador.leerProyectoEnergia(id);

            if (proyectoExistente == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el proyecto con ID: " + id, "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField fieldNombre = new JTextField(proyectoExistente.getNombreProyecto());
            JComboBox<String> fieldTipoFuente = new JComboBox<>(new String[]{"Solar", "Eólica", "Hidroeléctrica", "Geotérmica", "Biomasa", "Nuclear", "Térmica"});
            fieldTipoFuente.setSelectedItem(proyectoExistente.getTipoFuente());
            JTextField fieldCapacidad = new JTextField(String.valueOf(proyectoExistente.getCapacidadMW()));

            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            panel.add(new JLabel("Nombre Proyecto:"));
            panel.add(fieldNombre);
            panel.add(new JLabel("Tipo de Fuente:"));
            panel.add(fieldTipoFuente);
            panel.add(new JLabel("Capacidad (MW):"));
            panel.add(fieldCapacidad);

            int result = JOptionPane.showConfirmDialog(this, panel, "Actualizar Proyecto ID: " + id,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nuevoNombre = fieldNombre.getText();
                String nuevoTipoFuente = (String) fieldTipoFuente.getSelectedItem();
                double nuevaCapacidad = Double.parseDouble(fieldCapacidad.getText());

                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre del proyecto no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de que desea actualizar este proyecto?",
                        "Confirmar Actualización", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                if (controlador.actualizarProyectoEnergia(id, nuevoNombre, nuevoTipoFuente, nuevaCapacidad)) {
                    JOptionPane.showMessageDialog(this, "Proyecto actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTodosLosProyectos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Proyecto o Capacidad (MW) debe ser un número válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminarProyecto() {
        try {
            int id = Integer.parseInt(txtProyectoID.getText());
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar el proyecto con ID: " + id + "?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controlador.eliminarProyectoEnergia(id)) {
                    JOptionPane.showMessageDialog(this, "Proyecto eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCamposGestion();
                    cargarTodosLosProyectos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar proyecto. Asegúrese de que el ID exista.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Proyecto debe ser un número entero para eliminar.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cargarTodosLosProyectos() {
        modeloTabla.setRowCount(0);
        List<ProyectoEnergia> proyectos = controlador.leerTodosProyectosEnergia();

        if (proyectos != null) {
            for (ProyectoEnergia p : proyectos) {
                modeloTabla.addRow(new Object[]{p.getId(), p.getNombreProyecto(), p.getTipoFuente(), p.getCapacidadMW()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los proyectos. Verifique la conexión a la base de datos.", "Error de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void estiloTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(PaletaUI.AZUL_ENERGETICO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setGridColor(PaletaUI.BORDE_SUAVE);
        tabla.setRowHeight(25);
        tabla.setShowGrid(true);
        tabla.setSelectionBackground(PaletaUI.AMARILLO_SOLAR);
        tabla.setSelectionForeground(Color.BLACK);
    }

    private void limpiarCamposCreacion() {
        txtNombreProyecto.setText("");
        cmbTipoFuente.setSelectedIndex(0);
        txtCapacidadMW.setText("");
    }

    private void limpiarCamposGestion() {
        txtProyectoID.setText("");
    }

    public JTable getTabla() {
        return tablaProyectos;
    }
}