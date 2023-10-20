
package form;

import javax.swing.JScrollBar;
import java.awt.Rectangle;
import javax.swing.JScrollPane;

public class CityScroll extends JScrollPane {
    public Ciudad city;
    public CiudadView jc;
    MonitorActualizarVentana esperarRefresco;

    public CityScroll(final Ciudad city) {
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.city = city;
        this.setViewportView(this.jc = new CiudadView(this.city));
        this.repaint();
    }

    public void refresh() {
        this.jc.repaint();
        this.city.form.compedos.iv.vent.repaint();
    }

    public void view(final double px, final double py) {
        int y = (int) (py * this.jc.getHeight2());
        int x = (int) (px * this.jc.getWidth2());
        final Rectangle b = this.getViewport().getBounds();
        if (y + b.height > this.jc.getHeight2()) {
            y = this.jc.getHeight2() - b.height;
        }
        if (x + b.width > this.jc.getWidth2()) {
            x = this.jc.getWidth2() - b.width;
        }
        final JScrollBar vbar = this.getVerticalScrollBar();
        final JScrollBar hbar = this.getHorizontalScrollBar();
        vbar.setValue(y);
        hbar.setValue(x);
        this.city.form.compedos.iv.vent.repaint();
    }
}
