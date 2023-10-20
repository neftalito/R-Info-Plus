
package random;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ventana extends JPanel {
    public Ventana() {
        final JPanel principal = new JPanel();
        final JLabel label1 = new JLabel("Esto es el panel principal");
        principal.add(label1);
        final JPanel avanzado = new JPanel();
        final JLabel label2 = new JLabel("Esto es el panel azanzado");
        principal.add(label2);
        final JPanel privado = new JPanel();
        final JLabel label3 = new JLabel("Esto es el panel privado");
        principal.add(label3);
        final JTabbedPane Ventana = new JTabbedPane();
        final ImageIcon icon = new ImageIcon("flor.png");
        principal.setPreferredSize(new Dimension(410, 500));
        Ventana.addTab("Avanzado", icon, avanzado);
        Ventana.addTab("Privado", icon, privado);
        this.add(Ventana);
        Ventana.setTabLayoutPolicy(1);
    }

    public static void Crear() {
        final JFrame marco = new JFrame("Ejemploo solapass");
        marco.setDefaultCloseOperation(3);
        marco.add(new Ventana(), "Center");
        marco.pack();
        marco.setVisible(true);
    }

    public static void main(final String[] args) {
        Crear();
    }
}
