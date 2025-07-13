package vista;

import controlador.RegistroConsumoControlador;
import modelo.RegistroConsumo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;

public class ModuloRegistroConsumoGUI extends JPanel {

    private RegistroConsumoControlador controlador;

    private JTable tablaHistorialConsumo;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtFecha;
    private JTextField txtTipo;
    private JTextField txtConsumo;
    private JTextField txtUnidad;
    private JTextField txtCosto;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    public ModuloRegistroConsumoGUI() {
        this.controlador = new RegistroConsumoControlador();
        initComponents();
        addListeners();
        cargarHistorialConsumo();
        estiloTabla(tablaHistorialConsumo);
    }

    public JTable getTabla() {
        return tablaHistorialConsumo;
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Datos del Registro de Consumo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtId = new JTextField(15);
        txtId.setEditable(false);
        inputPanel.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtFecha = new JTextField(15);
        txtFecha.setToolTipText("Formato: YYYY-MM-DD");
        inputPanel.add(txtFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtTipo = new JTextField(15);
        inputPanel.add(txtTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Consumo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        txtConsumo = new JTextField(15);
        inputPanel.add(txtConsumo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Unidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        txtUnidad = new JTextField(15);
        inputPanel.add(txtUnidad, gbc);

        gbc.gridx = 0; gbc.gridy = 5; inputPanel.add(new JLabel("Costo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        txtCosto = new JTextField(15);
        inputPanel.add(txtCosto, gbc);

        add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Fecha", "Tipo", "Consumo", "Unidad", "Costo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHistorialConsumo = new JTable(tableModel);
        tablaHistorialConsumo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaHistorialConsumo);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnRegistrar = new JButton("Registrar Consumo");
        btnActualizar = new JButton("Actualizar Consumo");
        btnEliminar = new JButton("Eliminar Consumo");
        btnLimpiar = new JButton("Limpiar Campos");

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        btnRegistrar.addActionListener(this::registrarConsumo);
        btnActualizar.addActionListener(this::actualizarConsumo);
        btnEliminar.addActionListener(this::eliminarConsumo);
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaHistorialConsumo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaHistorialConsumo.getSelectedRow() != -1) {
                int selectedRow = tablaHistorialConsumo.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtFecha.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTipo.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtConsumo.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtUnidad.setText(tableModel.getValueAt(selectedRow, 4).toString());
                txtCosto.setText(tableModel.getValueAt(selectedRow, 5).toString());
            }
        });
    }

    public void cargarHistorialConsumo() {
        tableModel.setRowCount(0);
        java.util.List<RegistroConsumo> historial = controlador.obtenerHistorialConsumo();
        if (historial != null) {
            for (RegistroConsumo r : historial) {
                tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getFecha().toString(),
                    r.getTipo(),
                    r.getConsumo(),
                    r.getUnidad(),
                    r.getCosto()
                });
            }
        }
    }

    private void registrarConsumo(ActionEvent e) {
        LocalDate fecha = null;
        BigDecimal costo = null;
        double consumo = 0.0;

        try {
            fecha = LocalDate.parse(txtFecha.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = txtTipo.getText().trim();
        String unidad = txtUnidad.getText().trim();

        try {
            consumo = Double.parseDouble(txtConsumo.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Consumo debe ser un número válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            costo = new BigDecimal(txtCosto.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Costo debe ser un número decimal válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipo.isEmpty() || unidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos (excepto ID) son obligatorios.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RegistroConsumo nuevoRegistro = new RegistroConsumo(fecha, tipo, consumo, unidad, costo);

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea registrar este consumo de energía?",
            "Confirmar Registro", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int idGenerado = controlador.registrarConsumo(nuevoRegistro);

        if (idGenerado != -1) {
            JOptionPane.showMessageDialog(this, "Registro de consumo creado con ID: " + idGenerado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarHistorialConsumo();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear el registro de consumo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarConsumo(ActionEvent e) {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        LocalDate fecha = null;
        BigDecimal costo = null;
        double consumo = 0.0;

        try {
            fecha = LocalDate.parse(txtFecha.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = txtTipo.getText().trim();
        String unidad = txtUnidad.getText().trim();

        try {
            consumo = Double.parseDouble(txtConsumo.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Consumo debe ser un número válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            costo = new BigDecimal(txtCosto.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Costo debe ser un número decimal válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipo.isEmpty() || unidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos (excepto ID) son obligatorios.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RegistroConsumo registroActualizado = new RegistroConsumo(id, fecha, tipo, consumo, unidad, costo);
        boolean exito = controlador.actualizarConsumo(registroActualizado);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Registro de consumo actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarHistorialConsumo();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el registro de consumo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarConsumo(ActionEvent e) {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            boolean exito = controlador.eliminarConsumo(id);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Registro de consumo eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarHistorialConsumo();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el registro de consumo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

    private void limpiarCampos() {
        txtId.setText("");
        txtFecha.setText("");
        txtTipo.setText("");
        txtConsumo.setText("");
        txtUnidad.setText("");
        txtCosto.setText("");
        tablaHistorialConsumo.clearSelection();
    }
}