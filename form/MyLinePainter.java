
package form;

import java.awt.event.MouseEvent;
import javax.swing.event.CaretEvent;
import javax.swing.SwingUtilities;
import java.awt.Shape;
import java.awt.Graphics;
import javax.swing.text.BadLocationException;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.text.JTextComponent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import javax.swing.event.CaretListener;
import javax.swing.text.Highlighter;

class MyLinePainter implements Highlighter.HighlightPainter, CaretListener, MouseListener, MouseMotionListener {
    private JTextComponent component;
    private Color color;
    private Rectangle lastView;

    public MyLinePainter(final JTextComponent component) {
        this(component, null);
        this.setLighter(component.getSelectionColor());
    }

    public MyLinePainter(final JTextComponent component, final Color color) {
        this.component = component;
        this.setColor(color);
        component.addCaretListener(this);
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        try {
            component.getHighlighter().addHighlight(0, 0, this);
        } catch (BadLocationException ex) {
        }
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setLighter(final Color color) {
        final int red = Math.min(255, (int) (color.getRed() * 1.2));
        final int green = Math.min(255, (int) (color.getGreen() * 1.2));
        final int blue = Math.min(255, (int) (color.getBlue() * 1.2));
        this.setColor(new Color(red, green, blue));
    }

    @Override
    public void paint(final Graphics g, final int p0, final int p1, final Shape bounds, final JTextComponent c) {
        try {
            final Rectangle r = c.modelToView(c.getCaretPosition());
            g.setColor(this.color);
            g.fillRect(0, r.y, c.getWidth(), r.height);
            if (this.lastView == null) {
                this.lastView = r;
            }
        } catch (BadLocationException ble) {
            System.out.println(ble);
        }
    }

    private void resetHighlight() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final int offset = MyLinePainter.this.component.getCaretPosition();
                    final Rectangle currentView = MyLinePainter.this.component.modelToView(offset);
                    if (MyLinePainter.this.lastView != null && MyLinePainter.this.lastView.y != currentView.y) {
                        MyLinePainter.this.component.repaint(0, MyLinePainter.this.lastView.y,
                                MyLinePainter.this.component.getWidth(), MyLinePainter.this.lastView.height);
                        MyLinePainter.this.lastView = currentView;
                    }
                } catch (BadLocationException ex) {
                }
            }
        });
    }

    @Override
    public void caretUpdate(final CaretEvent e) {
        this.resetHighlight();
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        this.resetHighlight();
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        this.resetHighlight();
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }
}
