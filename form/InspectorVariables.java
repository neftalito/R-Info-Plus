
package form;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.beans.PropertyChangeEvent;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

public class InspectorVariables extends JPanel implements PropertyChangeListener {
    public Ciudad city;
    Ventanita vent;
    JScrollPane ttt;
    public ArrayList<Datos> datos_robots;
    JPanel tempPanel;
    JScrollPane robotsData;
    RobotsEnEjecucion robotsEnEjecutando;
    public JPanel tempPanelRobots;

    public InspectorVariables(final Ciudad city) {
        this.datos_robots = new ArrayList<Datos>();
        this.robotsEnEjecutando = new RobotsEnEjecucion(this.datos_robots);
        (this.city = city).addPropertyChangeListener(this);
        for (final Robot rr : city.robots) {
            System.out.println("Inicio Inspector Variables");
            final Datos tmp = new Datos(this.city, rr);
            rr.addPropertyChangeListener(tmp);
            this.city.addPropertyChangeListener(tmp);
            this.datos_robots.add(tmp);
        }
        this.add(this.tempPanel = this.form());
        this.add(this.tempPanelRobots = this.form2());
    }

    public JPanel form() {
        final JPanel panel = new JPanel();
        final GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridy = 0;
        gbc.fill = 3;
        panel.add(this.ttt = this.ventanita(), gbc);
        for (int i = 0; i < this.datos_robots.size(); ++i) {
            gbc.gridy = i + 2;
            gbc.fill = 1;
            panel.add(this.datos_robots.get(i), gbc);
        }
        return panel;
    }

    public JScrollPane ventanita() {
        this.vent = new Ventanita(this.city);
        final JScrollPane panel = new JScrollPane(this.vent);
        panel.setOpaque(false);
        panel.setBorder(new TitledBorder("Miniatura..."));
        panel.setPreferredSize(new Dimension(212, 228));
        panel.setViewportView(this.vent);
        panel.setHorizontalScrollBarPolicy(31);
        panel.setVerticalScrollBarPolicy(21);
        panel.setVisible(true);
        panel.repaint();
        return panel;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();
        final String propertyValue = evt.getNewValue().toString();
        if (propertyName.equals("numRobots")) {
            final int i = Integer.parseInt(propertyValue) - 1;
            final GridBagConstraints gbc = new GridBagConstraints();
            final Robot rr = this.city.robots.get(i);
            final Datos tmp = new Datos(this.city, rr);
            rr.addPropertyChangeListener(tmp);
            this.city.addPropertyChangeListener(tmp);
            this.datos_robots.add(tmp);
            gbc.gridx = 0;
            gbc.fill = 3;
            gbc.gridy = i + 2;
            gbc.fill = 1;
            this.tempPanel.add(this.datos_robots.get(i), gbc);
            this.tempPanel.revalidate();
            ((RobotsEnEjecucion) this.tempPanelRobots).panel.add(this.datos_robots.get(i), gbc);
        }
    }

    public JPanel form2() {
        this.robotsEnEjecutando.initComponents(this.datos_robots);
        return this.robotsEnEjecutando;
    }

    public static class RobotsEnEjecucion extends JPanel {
        public JPanel panel;
        public JScrollPane scrollPane;

        public RobotsEnEjecucion(final ArrayList<Datos> datos_robots) {
            final Dimension dim = super.getToolkit().getScreenSize();
            dim.width = 230;
            dim.height -= 100;
            this.setPreferredSize(dim);
            this.setLayout(new BoxLayout(this, 1));
            this.initComponents(datos_robots);
            this.setBorder(new TitledBorder("ROBOTS EN EJECUCION"));
        }

        private void initComponents(final ArrayList<Datos> datos_robots) {
            (this.panel = new JPanel()).setLayout(new BoxLayout(this.panel, 1));
            (this.scrollPane = new JScrollPane(this.panel)).setBorder(BorderFactory.createEmptyBorder());
            this.add(this.scrollPane, "Center");
            for (int i = 0; i < datos_robots.size(); ++i) {
                this.panel.add(datos_robots.get(i));
            }
            this.panel.revalidate();
        }
    }
}
