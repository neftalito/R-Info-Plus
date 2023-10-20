
package form;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JViewport;

public class Ventanita extends CiudadView {
    private int rectanguloAncho;
    private int rectanguloAlto;
    int xCoord;
    int yCoord;

    public Ventanita(final Ciudad city) {
        super(city);
        this.setBlockSize(1);
    }

    public int getXrect() {
        final JViewport vp = this.city.form.jsp.getViewport();
        final Point p = vp.getViewPosition();
        final CiudadView cv = (CiudadView) vp.getComponent(0);
        final int nx = (int) (p.x / (double) cv.getWidth2() * this.getWidth2());
        return nx;
    }

    public int getYrect() {
        final JViewport vp = this.city.form.jsp.getViewport();
        final Point p = vp.getViewPosition();
        final CiudadView cv = (CiudadView) vp.getComponent(0);
        final int ny = (int) (p.y / (double) cv.getHeight2() * this.getHeight2());
        return ny;
    }

    public int getRectWidth() {
        return this.rectanguloAncho;
    }

    public int getRectHeight() {
        return this.rectanguloAlto;
    }

    public void updateRectWidth() {
        final double blockVisible = this.city.form.jsp.jc.getVisibleRect().getWidth()
                / this.city.form.jsp.jc.getWidth2();
        this.rectanguloAncho = (int) (this.getWidth2() * blockVisible);
    }

    public void updateRectHeight() {
        final double blockVisible = this.city.form.jsp.jc.getVisibleRect().getHeight()
                / this.city.form.jsp.jc.getHeight2();
        this.rectanguloAlto = (int) (this.getHeight2() * blockVisible);
    }

    @Override
    protected void dibujarCiudad() throws Exception {
        super.dibujarCiudadVentanita();
        this.updateRectHeight();
        this.updateRectWidth();
        this.lienzo.setColor(Color.black);
        this.lienzo.drawRect(this.getXrect(), this.getYrect(), this.getRectWidth(), this.getRectHeight());
    }

    @Override
    protected void dibujarRuta(final Robot robot) {
        this.xCoord = this.Av2x(robot.PosAv()) - this.getBlockSize() / 2;
        this.yCoord = this.Ca2y(robot.PosCa()) - this.getBlockSize() / 2;
        this.lienzo.setColor(robot.getColor());
        if (this.yCoord != 201) {
            this.lienzo.fillOval(this.xCoord, this.yCoord, 4, 4);
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        final double x = (e.getX() - this.getRectWidth() / 2) / (double) this.getWidth();
        final double y = (e.getY() - this.getRectHeight() / 2) / (double) this.getHeight();
        this.city.form.jsp.view(x, y);
    }

    public void Camarita() {
        final double x = (this.xCoord - this.getRectWidth() / 2) / (double) this.getWidth();
        final double y = (this.yCoord - this.getRectHeight() / 2) / (double) this.getHeight();
        this.city.form.jsp.view(x, y);
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
    }
}
