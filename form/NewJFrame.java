
package form;

import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JColorChooser;
import javax.swing.JButton;
import javax.swing.JFrame;

public class NewJFrame extends JFrame {
    private JButton jButton1;
    private JColorChooser jColorChooser1;
    private JOptionPane jOptionPane1;
    private JPanel jPanel1;
    private JToggleButton jToggleButton1;

    public NewJFrame() {
        this.initComponents();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jButton1 = new JButton();
        this.jColorChooser1 = new JColorChooser();
        this.jToggleButton1 = new JToggleButton();
        this.jOptionPane1 = new JOptionPane();
        this.setDefaultCloseOperation(3);
        this.jButton1.setText("jButton1");
        final GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(-1, 32767)
                        .addComponent(this.jColorChooser1, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jButton1)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                        jPanel1Layout.createSequentialGroup().addContainerGap(161, 32767).addComponent(this.jButton1)
                                .addGap(482, 482, 482))
                .addGroup(jPanel1Layout.createSequentialGroup().addGap(49, 49, 49)
                        .addComponent(this.jColorChooser1, -2, -1, -2).addContainerGap(280, 32767)));
        this.jToggleButton1.setText("jToggleButton1");
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup().addContainerGap(288, 32767).addComponent(this.jOptionPane1, -2,
                                -1, -2))
                .addGroup(layout.createSequentialGroup().addGap(382, 382, 382)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(this.jToggleButton1).addComponent(this.jPanel1, -2, -1, -2))
                        .addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addComponent(this.jToggleButton1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.jPanel1, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
                        .addComponent(this.jOptionPane1, -2, -1, -2)));
        this.pack();
    }

    public static void main(final String[] args) {
        try {
            for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex2) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex2);
        } catch (IllegalAccessException ex3) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex3);
        } catch (UnsupportedLookAndFeelException ex4) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex4);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
}
