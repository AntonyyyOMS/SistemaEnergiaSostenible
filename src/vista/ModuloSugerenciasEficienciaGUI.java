package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModuloSugerenciasEficienciaGUI extends JPanel {

    private JTextArea taSugerenciasGeneradas;
    private JList<String> listTipsGenerales;
    private DefaultListModel<String> modelTipsGenerales;
    private JList<String> listSugerenciasFavoritas;
    private DefaultListModel<String> modelSugerenciasFavoritas;
    private JButton btnGenerarSugerencia;
    private JButton btnGuardarComoFavorita;
    private JButton btnEliminarFavorita;

    // Datos de ejemplo para tips generales
    private String[] tipsGenerales = {
        "Apaga las luces al salir de una habitación.",
        "Desconecta los aparatos electrónicos que no uses (efecto 'vampiro').",
        "Usa bombillas LED de bajo consumo.",
        "Aprovecha la luz natural al máximo.",
        "Aísla bien tu hogar para mantener la temperatura.",
        "Lava la ropa con agua fría cuando sea posible.",
        "Usa electrodomésticos con certificación de eficiencia energética.",
        "Revisa y sella fugas de aire en ventanas y puertas.",
        "Limpia regularmente los filtros de tu aire acondicionado/calefacción.",
        "Planta árboles para proporcionar sombra en verano."
    };

    public ModuloSugerenciasEficienciaGUI() {
        setLayout(new BorderLayout(10, 10)); // Margen entre componentes
        initComponents();
        addListeners();
        cargarTipsGenerales();
    }

    private void initComponents() {
        // Panel Superior: Generar Sugerencias Personalizadas
        JPanel pnlGenerar = new JPanel(new BorderLayout(5, 5));
        pnlGenerar.setBorder(BorderFactory.createTitledBorder("Generar Sugerencias Personalizadas"));
        taSugerenciasGeneradas = new JTextArea(5, 30);
        taSugerenciasGeneradas.setEditable(false);
        taSugerenciasGeneradas.setLineWrap(true);
        taSugerenciasGeneradas.setWrapStyleWord(true);
        JScrollPane scrollGeneradas = new JScrollPane(taSugerenciasGeneradas);
        pnlGenerar.add(scrollGeneradas, BorderLayout.CENTER);

        btnGenerarSugerencia = new JButton("Generar Nueva Sugerencia");
        JPanel pnlBtnGenerar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnGenerar.add(btnGenerarSugerencia);
        pnlGenerar.add(pnlBtnGenerar, BorderLayout.SOUTH);

        add(pnlGenerar, BorderLayout.NORTH);

        // Panel Central: Tips Generales y Sugerencias Favoritas
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Divide el espacio a la mitad

        // Panel Izquierdo: Tips Generales
        JPanel pnlTipsGenerales = new JPanel(new BorderLayout(5, 5));
        pnlTipsGenerales.setBorder(BorderFactory.createTitledBorder("Tips Generales de Eficiencia"));
        modelTipsGenerales = new DefaultListModel<>();
        listTipsGenerales = new JList<>(modelTipsGenerales);
        listTipsGenerales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTips = new JScrollPane(listTipsGenerales);
        pnlTipsGenerales.add(scrollTips, BorderLayout.CENTER);

        btnGuardarComoFavorita = new JButton("Guardar como Favorita");
        JPanel pnlBtnGuardar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnGuardar.add(btnGuardarComoFavorita);
        pnlTipsGenerales.add(pnlBtnGuardar, BorderLayout.SOUTH);

        splitPane.setLeftComponent(pnlTipsGenerales);

        // Panel Derecho: Sugerencias Favoritas
        JPanel pnlFavoritas = new JPanel(new BorderLayout(5, 5));
        pnlFavoritas.setBorder(BorderFactory.createTitledBorder("Mis Sugerencias Favoritas"));
        modelSugerenciasFavoritas = new DefaultListModel<>();
        listSugerenciasFavoritas = new JList<>(modelSugerenciasFavoritas);
        listSugerenciasFavoritas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollFavoritas = new JScrollPane(listSugerenciasFavoritas);
        pnlFavoritas.add(scrollFavoritas, BorderLayout.CENTER);

        btnEliminarFavorita = new JButton("Eliminar Favorita");
        JPanel pnlBtnEliminar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtnEliminar.add(btnEliminarFavorita);
        pnlFavoritas.add(pnlBtnEliminar, BorderLayout.SOUTH);

        splitPane.setRightComponent(pnlFavoritas);

        add(splitPane, BorderLayout.CENTER);
        aplicarEstilosJList(listTipsGenerales);
        aplicarEstilosJList(listSugerenciasFavoritas);
    }

    private void addListeners() {
        btnGenerarSugerencia.addActionListener(e -> generarSugerencia());
        btnGuardarComoFavorita.addActionListener(e -> guardarSugerenciaFavorita());
        btnEliminarFavorita.addActionListener(e -> eliminarSugerenciaFavorita());
    }

    private void generarSugerencia() {

        if (tipsGenerales.length > 0) {
            int randomIndex = (int) (Math.random() * tipsGenerales.length);
            taSugerenciasGeneradas.setText("Sugerencia para ti: " + tipsGenerales[randomIndex]);
        } else {
            taSugerenciasGeneradas.setText("No hay sugerencias disponibles.");
        }
    }

    private void cargarTipsGenerales() {
        modelTipsGenerales.clear();
        for (String tip : tipsGenerales) {
            modelTipsGenerales.addElement(tip);
        }
    }

    private void guardarSugerenciaFavorita() {
        String selectedTip = listTipsGenerales.getSelectedValue();
        if (selectedTip != null) {
            if (!modelSugerenciasFavoritas.contains(selectedTip)) { // Evitar duplicados
                modelSugerenciasFavoritas.addElement(selectedTip);
                JOptionPane.showMessageDialog(this, "Sugerencia guardada como favorita.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Esta sugerencia ya está en tus favoritas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un tip general para guardar como favorita.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void aplicarEstilosJList(JList<String> list) {
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        list.setBackground(PaletaUI.BLANCO_NUBE);
        list.setForeground(PaletaUI.GRIS_OSCURO);
        list.setSelectionBackground(PaletaUI.AMARILLO_SOLAR);
        list.setSelectionForeground(Color.BLACK);
}

    private void eliminarSugerenciaFavorita() {
        String selectedFavorite = listSugerenciasFavoritas.getSelectedValue();
        if (selectedFavorite != null) {
            modelSugerenciasFavoritas.removeElement(selectedFavorite);
            JOptionPane.showMessageDialog(this, "Sugerencia eliminada de favoritos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sugerencia favorita para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}