
package form;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Configuraciones extends JPanel {
    Ciudad city;
    JButton bot1;
    JButton bot2;
    JButton bot3;
    JButton bot4;
    JComboBox com;
    JComboBox com1;
    JComboBox com2;
    JTextField jf1;
    JTextField jf2;
    public JTextField jf3;
    JLabel lab1;
    JLabel lab2;
    JLabel lab3;
    JLabel lab4;
    JLabel lab5;
    JLabel lab6;
    GridBagConstraints gbc;
    JPanel JP;
    private int limite;

    Configuraciones(final Ciudad city) {
        this.bot1 = new JButton("Agregar");
        this.bot2 = new JButton("OK");
        this.bot3 = new JButton("OK");
        this.bot4 = new JButton("Agregar");
        this.com = new JComboBox();
        this.com1 = new JComboBox();
        this.com2 = new JComboBox();
        this.jf1 = new JTextField(3);
        this.jf2 = new JTextField(3);
        this.jf3 = new JTextField(3);
        this.lab1 = new JLabel("Avenida: ");
        this.lab2 = new JLabel("Calle:   ");
        this.lab3 = new JLabel("Elemento:      ");
        this.lab4 = new JLabel("Bolsa de Flores ");
        this.lab5 = new JLabel("Bolsa de Papeles  ");
        this.lab6 = new JLabel("Cantidad:    ");
        this.gbc = new GridBagConstraints();
        this.limite = 5;
        this.jf3.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (Configuraciones.this.jf3.getText().length() == Configuraciones.this.limite) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(final KeyEvent arg0) {
            }

            @Override
            public void keyReleased(final KeyEvent arg0) {
            }
        });
        final TitledBorder ti = new TitledBorder("Configuraciones");
        this.city = city;
        this.jf1.setText("0");
        this.jf2.setText("0");
        this.jf3.setText("0");
        this.jf3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                final char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b' && c != '\u007f') {
                    Configuraciones.this.getToolkit().beep();
                    e.consume();
                }
            }
        });
        this.setLayout(new GridBagLayout());
        this.com.setModel(new DefaultComboBoxModel<String>(new String[] { "Flores", "Papeles", "Obstaculos" }));
        this.com2.setModel(new DefaultComboBoxModel<String>(new String[] { "*", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
                "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76",
                "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93",
                "94", "95", "96", "97", "98", "99", "100" }));
        this.com1.setModel(new DefaultComboBoxModel<String>(new String[] { "*", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
                "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76",
                "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93",
                "94", "95", "96", "97", "98", "99", "100" }));
        this.gbc.anchor = 17;
        this.gbc.insets = new Insets(2, 10, 0, 0);
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.add(this.lab3, this.gbc);
        this.gbc.gridx = 1;
        this.gbc.gridy = 0;
        this.add(this.lab1, this.gbc);
        this.gbc.gridx = 2;
        this.gbc.gridy = 0;
        this.add(this.lab2, this.gbc);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.add(this.com, this.gbc);
        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        this.add(this.com1, this.gbc);
        this.gbc.gridx = 2;
        this.gbc.gridy = 1;
        this.add(this.com2, this.gbc);
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.add(this.lab6, this.gbc);
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 1;
        this.gbc.fill = 1;
        this.add(this.jf3, this.gbc);
        this.gbc.gridwidth = 0;
        this.gbc.fill = 0;
        this.gbc.gridx = 1;
        this.gbc.gridy = 3;
        this.gbc.weighty = 1.0;
        this.gbc.gridheight = 1;
        this.gbc.weightx = 1.0;
        this.add(this.bot4, this.gbc);
        this.gbc.fill = 2;
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        (this.JP = new TablaRobot(this.city)).setBorder(new TitledBorder("ROBOTS"));
        this.add(this.JP, this.gbc);
        this.revalidate();
        this.bot4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                Configuraciones.this.jButton4MouseClicked(evt);
            }
        });
        this.bot2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                Configuraciones.this.jtest(evt);
            }
        });
    }

    private void jtest(final MouseEvent evt) {
    }

    private void jButton4MouseClicked(final MouseEvent evt) {
        final int x1 = this.com1.getSelectedIndex();
        final int x2 = this.com2.getSelectedIndex();
        final int aux = Integer.parseInt(this.jf3.getText());
        if (aux > 0) {
            for (int i = 0; i < aux; ++i) {
                int aux2;
                if (x1 == 0) {
                    aux2 = (int) (Math.random() * 100.0) + 1;
                } else {
                    aux2 = this.com1.getSelectedIndex();
                }
                int aux3;
                if (x2 == 0) {
                    aux3 = (int) (Math.random() * 100.0) + 1;
                } else {
                    aux3 = this.com2.getSelectedIndex();
                }
                if (this.com.getSelectedItem().toString().equals("Flores") && x1 == 0 && x2 == 0) {
                    this.city.ciudad[aux2][aux3].setFlores(this.city.ciudad[aux2][aux3].getFlores() + 1);
                } else if (this.com.getSelectedItem().toString().equals("Flores") && x1 != 0 && x2 != 0) {
                    this.city.ciudad[aux2][aux3].setFlores(aux);
                } else if (this.com.getSelectedItem().toString().equals("Flores")) {
                    this.city.ciudad[aux2][aux3].setFlores(this.city.ciudad[aux2][aux3].getFlores() + 1);
                }
                if (this.com.getSelectedItem().toString().equals("Papeles") && x1 == 0 && x2 == 0) {
                    this.city.ciudad[aux2][aux3].setPapeles(this.city.ciudad[aux2][aux3].getPapeles() + 1);
                } else if (this.com.getSelectedItem().toString().equals("Papeles") && x1 != 0 && x2 != 0) {
                    this.city.ciudad[aux2][aux3].setPapeles(aux);
                } else if (this.com.getSelectedItem().toString().equals("Papeles")) {
                    this.city.ciudad[aux2][aux3].setPapeles(this.city.ciudad[aux2][aux3].getPapeles() + 1);
                }
                if (this.com.getSelectedItem().toString().equals("Obstaculos")) {
                    this.city.ciudad[aux2][aux3].setObstaculo(true);
                }
            }
        }
        this.city.form.jsp.refresh();
    }
}
