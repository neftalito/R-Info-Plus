
package form;

import java.beans.PropertyChangeEvent;
import java.awt.Image;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

public class Datos extends JPanel implements PropertyChangeListener {
    Ciudad city;
    JTextField lab5;
    JTextField lab6;
    JLabel lab2;
    JLabel lab1;
    JLabel lab11;
    JLabel lab12;
    JLabel lab13;
    JLabel lab14;
    JLabel lab15;
    JLabel lab16;
    JLabel lab17;
    JLabel lab18;
    JLabel estado;
    ImageIcon icon;
    JLabel lab0;
    PanelImg pa;
    Robot x;
    Color C;
    Color colorInicial;

    Datos(final Ciudad city, final Robot x) {
        this.lab5 = new JTextField("Av");
        this.lab6 = new JTextField("Ca");
        this.lab0 = new JLabel();
        this.C = Color.red;
        final Dimension dim = new Dimension(200, 100);
        this.setMaximumSize(dim);
        this.city = city;
        this.x = x;
        this.lab5.setText(Integer.toString(x.Av));
        this.lab6.setText(String.valueOf(x.Ca));
        final GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        this.setBorder(new TitledBorder(x.getNombre()));
        this.lab1 = new JLabel("Pos:");
        this.lab2 = new JLabel("(0" + x.PosAv() + " , 0" + x.PosCa() + ")");
        final JLabel lab3 = new JLabel("Bolsa   ");
        final JLabel lab4 = new JLabel("Esquina");
        (this.lab11 = new JLabel()).setText("F  ");
        this.lab11.setOpaque(true);
        this.lab12 = new JLabel("P ");
        (this.lab13 = new JLabel()).setText("F  ");
        this.lab14 = new JLabel("P ");
        (this.lab15 = new JLabel()).setText("0");
        this.lab16 = new JLabel("0");
        (this.lab17 = new JLabel()).setText("0");
        this.lab18 = new JLabel("0");
        (this.estado = new JLabel()).setForeground(this.estado.getForeground());
        this.colorInicial = this.estado.getForeground();
        this.estado.setText("Nuevo  ");
        final int dir = x.getDireccion();
        switch (dir) {
            case 90: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotArriba.png"));
                break;
            }
            case 270: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotAbajo.png"));
                break;
            }
            case 0: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotDerecha.png"));
                break;
            }
            default: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotIzquierda.png"));
                break;
            }
        }
        final Image img = this.icon.getImage();
        final Image newimg = img.getScaledInstance(16, 16, 4);
        final ImageIcon newIcon = new ImageIcon(newimg);
        (this.pa = new PanelImg(newimg)).setMaximumSize(new Dimension(16, 16));
        this.pa.setMinimumSize(new Dimension(16, 16));
        this.pa.setPreferredSize(new Dimension(16, 16));
        this.pa.setBackground(x.color);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        this.add(this.pa, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        this.add(this.lab1, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 4;
        this.add(this.lab2, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        this.add(lab3, gbc);
        gbc.gridx = 2;
        this.add(lab4, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(this.lab11, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(this.lab12, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(this.lab13, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        this.add(this.lab14, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(this.lab15, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(this.lab16, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        this.add(this.lab17, gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        this.add(this.lab18, gbc);
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(this.estado, gbc);
    }

    public void setAv(String str) {
        this.lab5.setText(str);
        this.lab2.setText("(" + this.lab5.getText() + " , " + this.lab6.getText() + ")");
        this.city.form.repaint();
    }

    public void setFloresEsq(String num) {
        this.lab17.setText(num);
        this.city.form.repaint();
    }

    public void setPapelesEsq(String num) {
        this.lab18.setText(num);
        this.city.form.repaint();
    }

    public void setFloresBolsa(String num) {
        this.lab15.setText(num);
        this.city.form.repaint();
    }

    public void setPapelesBolsa(String num) {
        this.lab16.setText(num);
        this.city.form.repaint();
    }

    public void setCa(String str) {
        this.lab6.setText(str);
        this.lab2.setText("(" + this.lab5.getText() + " , " + this.lab6.getText() + ")");
        this.city.form.repaint();
    }

    public void setIcon(final int dir) {
        switch (dir) {
            case 90: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotArriba.png"));
                break;
            }
            case 270: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotAbajo.png"));
                break;
            }
            case 0: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotDerecha.png"));
                break;
            }
            default: {
                this.icon = new ImageIcon(this.getClass().getResource("/images/robotIzquierda.png"));
                break;
            }
        }
        final Image img = this.icon.getImage();
        final Image newimg = img.getScaledInstance(16, 16, 4);
        final ImageIcon newIcon = new ImageIcon(newimg);
        this.lab0.setIcon(newIcon);
        this.lab0.setIcon(newIcon);
        this.pa.setImage(newimg);
        this.city.form.repaint();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();
        final String propertyValue = evt.getNewValue().toString();
        if (propertyName.equals("av")) {
            this.setAv(propertyValue);
            this.setCa(Integer.toString(this.x.Ca));
            final int flores = this.city.getFlores(this.x.PosAv(), this.x.PosCa());
            this.setFloresEsq(Integer.toString(flores));
            final int papeles = this.city.getPapeles(this.x.PosAv(), this.x.PosCa());
            this.setPapelesEsq(Integer.toString(papeles));
        } else if (propertyName.equals("ca")) {
            this.setCa(propertyValue);
            this.setAv(Integer.toString(this.x.Av));
            final int flores = this.city.getFlores(this.x.PosAv(), this.x.PosCa());
            this.setFloresEsq(Integer.toString(flores));
            final int papeles = this.city.getPapeles(this.x.PosAv(), this.x.PosCa());
            this.setPapelesEsq(Integer.toString(papeles));
        } else if (propertyName.equals("direccion")) {
            this.setIcon(Integer.parseInt(propertyValue));
        } else if (propertyName.equals("flores")) {
            final int flores = this.city.getFlores(this.x.PosAv(), this.x.PosCa());
            this.setFloresEsq(Integer.toString(flores));
            this.setFloresBolsa(propertyValue);
        } else if (propertyName.equals("papeles")) {
            this.setPapelesBolsa(propertyValue);
            final int papeles2 = this.city.getPapeles(this.x.PosAv(), this.x.PosCa());
            this.setPapelesEsq(Integer.toString(papeles2));
        } else if (propertyName.equals("EsquinaPapeles")) {
            final int papeles2 = this.city.getPapeles(this.x.PosAv(), this.x.PosCa());
            this.setPapelesEsq(Integer.toString(papeles2));
        } else if (propertyName.equals("EsquinaFlores")) {
            final int flores = this.city.getFlores(this.x.PosAv(), this.x.PosCa());
            this.setFloresEsq(Integer.toString(flores));
        } else if (propertyName.equals("color")) {
            this.pa.setBackground((Color) evt.getNewValue());
            this.C = (Color) evt.getNewValue();
        } else if (propertyName.equals("estado")) {
            this.estado.setText((String) evt.getNewValue());
            if (this.estado.getText().equals("error")) {
                this.estado.setForeground(Color.red);
            } else {
                this.estado.setForeground(this.colorInicial);
            }
            this.repaint();
        }
    }
}
