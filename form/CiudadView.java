
package form;

import javax.swing.JScrollBar;
import java.awt.Rectangle;
import java.awt.Container;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class CiudadView extends JComponent implements MouseListener, MouseWheelListener {
    MonitorActualizarVentana esperarRefresco;
    private Color colorManzana;
    private int cityWidth;
    private int cityHeight;
    private int blockSize;
    private ImageIcon flor;
    private ImageIcon papel;
    private ImageIcon obstaculo;
    Ciudad city;
    Graphics lienzo;

    public CiudadView(final Ciudad city) {
        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.flor = new ImageIcon(this.getClass().getResource("/images/flor.png"));
        this.papel = new ImageIcon(this.getClass().getResource("/images/papel.png"));
        this.obstaculo = new ImageIcon(this.getClass().getResource("/images/obstaculo.png"));
        this.setDoubleBuffered(true);
        this.setOpaque(true);
        this.city = city;
        this.addMouseListener(this);
        this.colorManzana = Color.GRAY;
        this.setBlockSize(10);
        this.addMouseWheelListener(this);
    }

    public void setBlockSize(int blockSize) {
        if (blockSize < 1) {
            blockSize = 1;
        }
        if (blockSize > 30) {
            blockSize = 30;
        }
        this.blockSize = blockSize;
        this.cityHeight = this.city.getNumCa() * blockSize * 2;
        this.cityWidth = this.city.getNumAv() * blockSize * 2;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public Color getColorManzana() {
        return this.colorManzana;
    }

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.lienzo = g;
        try {
            this.dibujarCiudad();
        } catch (Exception e) {
            System.out.println("Error en Paint Component" + e.getMessage());
        }
        for (int i = 0; i < this.city.robots.size(); ++i) {
            try {
                this.esperarRefresco.pintarRuta(this.city.robots.get(i));
            } catch (Exception ex) {
                System.out.println("Error en Paint Component en la clase CiudadView");
                Logger.getLogger(CiudadView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int x2Av(final int x) {
        int pos = (x + 4) / (this.blockSize * 2) + 1;
        if (pos < 1) {
            pos = 1;
        }
        if (pos > this.city.getNumAv()) {
            pos = this.city.getNumAv();
        }
        return pos;
    }

    public int y2Ca(final int y) {
        int pos = this.city.getNumCa() - (y + 4) / (this.blockSize * 2);
        if (pos < 1) {
            pos = 1;
        }
        if (pos > this.city.getNumCa()) {
            pos = this.city.getNumCa();
        }
        return pos;
    }

    public int Av2x(final int Av) {
        return this.blockSize * 2 * (Av - 1) + this.blockSize / 2;
    }

    public int Ca2y(int Ca) {
        Ca = this.city.getNumCa() + 1 - Ca;
        return this.blockSize * 2 * (Ca - 1) + this.blockSize + this.blockSize / 2;
    }

    protected void dibujarCiudad() throws Exception {
        this.lienzo.setColor(Color.white); // * Fondo de manzanas
        this.lienzo.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.lienzo.setColor(Color.black); // ! No encontré de qué es este color
        this.lienzo.setColor(this.getColorManzana());
        for (int ca = 1; ca <= this.city.getNumCa(); ++ca) {
            for (int av = 1; av <= this.city.getNumAv(); ++av) {
                if (ca == 1 || av == 100) {
                    this.lienzo.setColor(Color.WHITE); // * Ultimas manzanas de la derecha del todo
                }
                this.dibujarManzana(av, ca);
                this.dibujarEsquina(av, ca);
                this.lienzo.setColor(this.colorManzana);
            }
        }
        this.dibujarArea();
    }

    protected void dibujarCiudadVentanita() throws Exception {
        this.lienzo.setColor(Color.white); // * Fondo ciudad miniatura "Ventanita"
        this.lienzo.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.lienzo.setColor(Color.black); // * Bordes ciudad miniatura "Ventanita"
        this.lienzo.drawRect(0, this.getBlockSize(),
                this.city.getNumAv() * this.getBlockSize() * 2 + this.getBlockSize(),
                this.city.getNumCa() * this.getBlockSize() * 2 + this.getBlockSize());
        this.lienzo.setColor(this.getColorManzana());
        for (int ca = 1; ca <= this.city.getNumCa(); ++ca) {
            for (int av = 1; av <= this.city.getNumAv(); ++av) {
                if (ca == 0 || av == 100) {
                    this.lienzo.setColor(Color.WHITE); // * Ultimas manzanas de la derecha del todo de la "Ventanita"
                }
                this.dibujarManzana(av, ca);
                this.dibujarEsquina(av, ca);
                this.lienzo.setColor(this.colorManzana);
            }
        }
    }

    protected void dibujarArea() throws Exception {
        final Graphics2D g2 = (Graphics2D) this.lienzo;
        final Stroke strokeBefore = g2.getStroke();
        int av = 1;
        int ca = 1;
        for (final Area area : this.city.areas) {
            final int x = this.Av2x(area.getAv1());
            final int y = this.Ca2y(area.getCa2());
            final int width = (area.getAv2() - area.getAv1()) * this.getBlockSize() * 2;
            final int height = (area.getCa2() - area.getCa1()) * this.getBlockSize() * 2;
            g2.setStroke(strokeBefore);
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
            g2.setColor(area.getColor());
            for (ca = area.getCa1() + 1; ca <= area.getCa2(); ++ca) {
                for (av = area.getAv1(); av < area.getAv2(); ++av) {
                    if (ca == 1 || av == 100) {
                        this.lienzo.setColor(Color.WHITE);
                    }
                    this.lienzo.setColor(area.getColor());
                    this.dibujarManzana(av, ca);
                    this.dibujarEsquina(av, ca);
                }
            }
            g2.setColor(area.getColor());
            g2.setStroke(new BasicStroke(3.0f));
            g2.drawRect(x, y, width, height);
            this.lienzo.setColor(this.colorManzana);
        }
        g2.setStroke(strokeBefore);
    }

    private void dibujarManzana(final int av, final int ca) {
        final int xCoord = this.Av2x(av) + this.blockSize / 2;
        final int yCoord = this.Ca2y(ca) + this.blockSize / 2;
        this.lienzo.fillRect(xCoord, yCoord, this.blockSize, this.blockSize);
    }

    private void dibujarEsquina(final int av, final int ca) throws Exception {
        ImageIcon image = null;
        final int xCoord = this.Av2x(av) - this.blockSize / 2;
        final int yCoord = this.Ca2y(ca) - this.blockSize / 2;
        if (!this.city.isFreePos(ca, av)) {
            image = this.obstaculo;
        } else {
            if (this.city.HayPapelEnLaEsquina(av, ca)) {
                image = this.papel;
            }
            if (this.city.HayFlorEnLaEsquina(av, ca)) {
                image = this.flor;
            }
        }
        if (image != null) {
            this.lienzo.drawImage(image.getImage(), xCoord, yCoord, this.blockSize, this.blockSize, null);
        }
    }

    protected void dibujarRuta(final Robot robot) throws Exception {
        synchronized (this) {
            try {
                int c = 0;
                final Graphics2D g2 = (Graphics2D) this.lienzo;
                final ArrayList<ArrayList<Coord>> items = robot.getRutas();
                for (final ArrayList<Coord> item : items) {
                    c = 0;
                    final int[] x = new int[item.size()];
                    final int[] y = new int[item.size()];
                    g2.setColor(robot.getColor());
                    g2.setStroke(new BasicStroke(2.0f));
                    for (final Coord punto : item) {
                        x[c] = this.Av2x(punto.getX());
                        y[c] = this.Ca2y(punto.getY());
                        ++c;
                    }
                    g2.drawPolyline(x, y, c);
                    final int xCoord = this.Av2x(robot.PosAv()) - this.blockSize / 2;
                    final int yCoord = this.Ca2y(robot.PosCa()) - this.blockSize / 2;
                    this.lienzo.drawImage(robot.getImage(), xCoord, yCoord, this.blockSize, this.blockSize, null);
                }
                c = 0;
                final ArrayList<Coord> itemx = robot.getRuta();
                final int[] x2 = new int[itemx.size()];
                final int[] y2 = new int[itemx.size()];
                g2.setColor(robot.getColor());
                g2.setStroke(new BasicStroke(2.0f));
                for (final Coord punto2 : itemx) {
                    x2[c] = this.Av2x(punto2.getX());
                    y2[c] = this.Ca2y(punto2.getY());
                    ++c;
                }
                g2.drawPolyline(x2, y2, c);
                final int xCoord2 = this.Av2x(robot.PosAv()) - this.blockSize / 2;
                final int yCoord2 = this.Ca2y(robot.PosCa()) - this.blockSize / 2;
                this.lienzo.fillRect(xCoord2, yCoord2, this.blockSize, this.blockSize);
                this.lienzo.drawImage(robot.getImage(), xCoord2, yCoord2, this.blockSize, this.blockSize, null);
            } catch (Exception ex) {
                throw new Exception("Algo anda mal en dibujarRuta  " + ex.getMessage());
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.cityHeight, this.cityWidth);
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return this.getPreferredSize();
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    public void mouseDragged(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    public int getHeight2(final int offset) {
        final int h = this.city.getNumCa() * (this.getBlockSize() + offset) * 2;
        return h;
    }

    public int getHeight2() {
        return this.getHeight2(0);
    }

    public int getWidth2(final int offset) {
        final int w = this.city.getNumAv() * (this.getBlockSize() + offset) * 2;
        return w;
    }

    public int getWidth2() {
        return this.getWidth2(0);
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        Container tmp = this.getParent();
        for (int c = 0; c < 100 && !(tmp instanceof CityScroll); tmp = tmp.getParent(), ++c) {
        }
        final CityScroll tmp2 = (CityScroll) tmp;
        if (e.isControlDown()) {
            final Rectangle rr = tmp2.getViewport().getBounds();
            if (this.getHeight2(e.getWheelRotation()) > rr.height && this.getWidth2(e.getWheelRotation()) > rr.width) {
                this.setBlockSize(this.getBlockSize() + e.getWheelRotation());
            }
        } else {
            final JScrollBar vbar = this.city.form.jsp.getVerticalScrollBar();
            final int newValue = vbar.getValue() + e.getWheelRotation() * this.getBlockSize() * 5;
            vbar.setValue(newValue);
        }
        tmp2.refresh();
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }
}
