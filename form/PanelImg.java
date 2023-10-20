
package form;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JPanel;

public class PanelImg extends JPanel {
    private Image imagen;

    PanelImg(final Image img) {
        this.imagen = img;
        this.setMaximumSize(new Dimension(16, 16));
        this.setMinimumSize(new Dimension(16, 16));
    }

    public void setImage(final Image img) {
        this.imagen = img;
        this.repaint();
    }

    @Override
    public void paint(final Graphics g) {
        g.drawImage(this.imagen, 0, 0, this.getWidth(), this.getHeight(), this);
        this.setOpaque(true);
        super.paint(g);
        g.drawImage(this.imagen, 0, 0, this.getWidth(), this.getHeight(), this);
        this.setOpaque(false);
        super.paint(g);
    }
}
