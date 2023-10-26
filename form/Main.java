
package form;

import javax.swing.SwingUtilities;
import java.beans.PropertyChangeEvent;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.util.ArrayList;
import javax.swing.JSplitPane;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;

public class Main extends JFrame implements PropertyChangeListener {
    Ciudad cdad;
    Compedos compedos;
    public CityScroll jsp;
    MonitorActualizarVentana monitorCola;
    MonitorEsquinas esquinas;
    private JSplitPane splitPane;
    public Compe c;
    JFrame cod2;
    ArrayList<JTextArea> jt;

    public Main() {
        super(" R-Info+ | MOD NO OFICIAL V0.0.2 (update BETA 2.9.5)");
        this.monitorCola = MonitorActualizarVentana.crearMonitorActualizarVentana(4);
        this.esquinas = MonitorEsquinas.crearMonitorEsquinas();
        this.cod2 = new JFrame();
        this.jt = new ArrayList<JTextArea>();
    }

    public void mostrarMensaje(final String args, final String titulo, final int tipo) {
        JOptionPane.showMessageDialog(null, args, titulo, tipo);
    }

    public void createAndShowGUI() throws Exception {
        this.setDefaultCloseOperation(3);
        this.setExtendedState(6);
        try {
            this.cdad = new Ciudad(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cdad.addPropertyChangeListener(this);
        this.compedos = new Compedos(this.cdad);
        this.jsp = new CityScroll(this.cdad);
        this.monitorCola.setCityScroll(this.jsp);
        final FlowLayout exLay = new FlowLayout();
        this.cod2.setLayout(exLay);
        this.cod2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.cod2.pack();
        final JPanel contentPane = (JPanel) this.getContentPane();
        final JPanel contentPaneLeft = new JPanel(new BorderLayout(1, 2));
        contentPaneLeft.add(this.c = new Compe(this.cdad), "North");
        contentPaneLeft.add(this.compedos, "Center");
        contentPane.add(contentPaneLeft, "West");
        final JSplitPane splitPane2 = new JSplitPane(0, new CodePanel(this.cdad), this.jsp);
        splitPane2.setOneTouchExpandable(true);
        splitPane2.setDividerLocation(300);
        final JPanel contentPaneDerecho = new JPanel();
        contentPaneDerecho.add(this.compedos.iv.tempPanelRobots);
        (this.splitPane = new JSplitPane(1, splitPane2, contentPaneDerecho)).setOneTouchExpandable(true);
        this.splitPane.setDividerLocation(750);
        this.splitPane.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent pce) {
                Main.this.jsp.refresh();
            }
        });
        contentPane.add(this.splitPane, "Center");
        this.pack();
        this.setVisible(true);
    }

    public static void main(final String[] args) {
        // Mostrar alerta de que es un mod e imprimirlo en consola
        System.out.println(
                "¡Gracias por usar R-Info+! Recuerda que esto es un mod del original, puede haber fallos y hay instrucciones y operaciones que no son permitidas por la cátedra.\nMás información: https://github.com/neftalito/R-Info-Plus.");
        JOptionPane.showMessageDialog(null,
                "¡Gracias por usar R-Info+! Recuerda que esto es un mod del original, puede haber fallos y hay instrucciones y operaciones que no son permitidas por la cátedra.\nMás información: https://github.com/neftalito/R-Info-Plus.",
                "¡Importante!",
                JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Main().createAndShowGUI();
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
    }
}
