package vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import controlador.ProyectoEnergiaControlador;
import modelo.CommandManager;

public class ProyectoEnergiaCardGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea logArea;
    private ProyectoEnergiaControlador controlador;
    private CommandManager commandManager;

    public ProyectoEnergiaCardGUI() {
        setTitle("Sistema de Gestión de Energía Sostenible");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        applyModernStyles();
        setupControlador();
        setVisible(true);
    }
    
     private void initComponents() {
        // Panel principal con diseño mejorado
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(PaletaUI.BLANCO_NUBE);

        // Crear pestañas con iconos
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Módulo 1: Registro de Consumo
        ModuloRegistroConsumoGUI moduloRegistro = new ModuloRegistroConsumoGUI();
        tabbedPane.addTab("1. Registro de Consumo", crearIcono("⚡", PaletaUI.AMARILLO_SOLAR), moduloRegistro);
        aplicarEstilosTabla(moduloRegistro.getTabla()); // Aplicar estilos a la tabla del moduloRegistro

        // Módulo 2: Sugerencias de Eficiencia
        ModuloSugerenciasEficienciaGUI moduloSugerencias = new ModuloSugerenciasEficienciaGUI();
        tabbedPane.addTab("2. Sugerencias de Eficiencia", crearIcono("💡", PaletaUI.VERDE_SUSTENTABLE), moduloSugerencias);
        // Este modulo no tiene tabla, se omite aplicarEstilosTabla

        // Módulo 3: Gestión de Proyectos
        ModuloGestionProyectosGUI moduloProyectos = new ModuloGestionProyectosGUI();
        tabbedPane.addTab("3. Gestión de Proyectos", crearIcono("🌱", PaletaUI.AZUL_ENERGETICO), moduloProyectos);
        aplicarEstilosTabla(moduloProyectos.getTabla()); // Aplicar estilos a la tabla del moduloProyectos


        // Área de log mejorada
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(createTitledBorder("Registro del Sistema"));
        logScroll.setPreferredSize(new Dimension(0, 150));

        // Añadir componentes al panel principal
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(logScroll, BorderLayout.SOUTH);

        add(mainPanel);
    }
     

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PaletaUI.AZUL_ENERGETICO);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel title = new JLabel("Sistema de Gestión de Energía Sostenible");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Versión con Proxy de Caché");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(220, 220, 220));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(PaletaUI.AZUL_ENERGETICO);
        textPanel.add(title);
        textPanel.add(subtitle);
        
        header.add(textPanel, BorderLayout.WEST);
        
        // Añadir logo si está disponible
        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/icons/energy-logo.png"));
            JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
            header.add(logoLabel, BorderLayout.EAST);
        } catch (Exception e) {
            // Si no hay logo, continuar sin él
        }
        
        return header;
    }

    private TitledBorder createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PaletaUI.BORDE_SUAVE, 1),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            PaletaUI.GRIS_OSCURO
        );
    }

    private Icon crearIcono(String emoji, Color bgColor) {
        JLabel label = new JLabel(emoji);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                label.setBounds(x, y, getIconWidth(), getIconHeight());
                label.paint(g);
            }
            
            @Override
            public int getIconWidth() {
                return label.getPreferredSize().width;
            }
            
            @Override
            public int getIconHeight() {
                return label.getPreferredSize().height;
            }
        };
    }
    
     private void aplicarEstilosTabla(JTable tabla) {
        if (tabla != null) { // Verificar si la tabla existe
            tabla.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
            tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabla.getTableHeader().setBackground(PaletaUI.AZUL_ENERGETICO);
            tabla.getTableHeader().setForeground(Color.WHITE);
            tabla.setGridColor(PaletaUI.BORDE_SUAVE);
            tabla.setRowHeight(25);
            tabla.setShowGrid(true);
            tabla.setSelectionBackground(PaletaUI.AMARILLO_SOLAR);
            tabla.setSelectionForeground(Color.BLACK);
        }
    }

    private void applyModernStyles() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Estilos globales (mejorados para mayor consistencia)
            UIManager.put("Panel.background", PaletaUI.BLANCO_NUBE); // Fondo principal
            UIManager.put("TabbedPane.background", PaletaUI.BLANCO_NUBE); // Fondo pestañas
            UIManager.put("TabbedPane.foreground", PaletaUI.GRIS_OSCURO); // Texto pestañas
            UIManager.put("TabbedPane.selected", PaletaUI.AZUL_ENERGETICO); // Pestaña seleccionada
            UIManager.put("TabbedPane.borderHighlightColor", PaletaUI.AMARILLO_SOLAR); // Resaltado pestaña
            UIManager.put("Label.foreground", PaletaUI.GRIS_OSCURO); // Color del texto de las etiquetas
            UIManager.put("TextField.background", PaletaUI.BLANCO_NUBE); // Fondo de los campos de texto
            UIManager.put("TextField.foreground", PaletaUI.GRIS_OSCURO); // Texto de los campos de texto
            UIManager.put("ComboBox.background", PaletaUI.BLANCO_NUBE); // Fondo de los ComboBox
            UIManager.put("ComboBox.foreground", PaletaUI.GRIS_OSCURO); // Texto de los ComboBox
            UIManager.put("Button.background", PaletaUI.BOTON_PRIMARIO); // Fondo de los botones
            UIManager.put("Button.foreground", Color.WHITE); // Texto de los botones
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12)); // Fuente de los botones
            UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(PaletaUI.BORDE_ACTIVO, 1), BorderFactory.createEmptyBorder(8, 20, 8, 20))); // Borde de los botones
            UIManager.put("Button.focus", Color.WHITE); // Color de enfoque de los botones


            // Estilo para JList (añadido)
            UIManager.put("List.background", PaletaUI.BLANCO_NUBE); // Fondo de la lista
            UIManager.put("List.foreground", PaletaUI.GRIS_OSCURO); // Texto de la lista


            // Estilo para JTextArea (logArea)
            logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            logArea.setBackground(PaletaUI.BLANCO_NUBE);
            logArea.setForeground(PaletaUI.GRIS_OSCURO);
            logArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Estilo para JOptionPane 
            UIManager.put("OptionPane.background", PaletaUI.BLANCO_NUBE);
            UIManager.put("OptionPane.messageForeground", PaletaUI.GRIS_OSCURO);
            UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupControlador() {
    this.controlador = new ProyectoEnergiaControlador(null);
    this.commandManager = new CommandManager(); // Inicializa el CommandManager
    logMessage("Sistema iniciado con Proxy de Caché para Proyectos de Energía");
    logMessage("Primeras consultas irán a BD, siguientes usará caché");
}

    public void logMessage(String message) {
    SwingUtilities.invokeLater(() -> {
        logArea.append("[Sistema] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    });
}

    public static void mostrarMensajeExito(Component parent, String mensaje) {
    mostrarMensajePersonalizado(parent, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE, PaletaUI.VERDE_SUSTENTABLE);
}

    public static void mostrarMensajeError(Component parent, String mensaje) {
    mostrarMensajePersonalizado(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE, PaletaUI.BOTON_PELIGRO);
}

    public static void mostrarMensajeAdvertencia(Component parent, String mensaje) {
        mostrarMensajePersonalizado(parent, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE, PaletaUI.NARANJA_ALERTA);
    }

    public static void mostrarMensajeInformacion(Component parent, String mensaje) {
        mostrarMensajePersonalizado(parent, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE, PaletaUI.AZUL_ENERGETICO);
    }

    private static void mostrarMensajePersonalizado(Component parent, String mensaje, String titulo, int tipoMensaje, Color color) {
        JOptionPane pane = new JOptionPane(mensaje, tipoMensaje);
        JDialog dialog = pane.createDialog(parent, titulo);
        
        // Personalización del diálogo
        dialog.getContentPane().setBackground(PaletaUI.BLANCO_NUBE);
        
        // Personalización de botones
        for (Component comp : pane.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(color);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
                button.setFocusPainted(false);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProyectoEnergiaCardGUI();
        });
    }
}
