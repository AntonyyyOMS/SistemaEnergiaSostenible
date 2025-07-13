package vista;

import javax.swing.JTextArea;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingObserver implements Observer {
    private JTextArea logArea;
    private DateTimeFormatter formatter;

    public LoggingObserver(JTextArea logArea) {
        this.logArea = logArea;
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public void actualizar(String evento, Object datos) {
        String timestamp = LocalDateTime.now().format(formatter);
        String mensaje = String.format("[%s] LOG: %s - %s\n", timestamp, evento, datos.toString());
        if (logArea != null) {
            logArea.append(mensaje);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        } else {
            System.out.println(mensaje);
        }
    }
}