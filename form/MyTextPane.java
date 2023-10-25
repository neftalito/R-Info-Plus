
package form;

import javax.swing.text.Element;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.text.AbstractDocument;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;
import javax.swing.JScrollPane;

// Clase con el panel de texto y número de  lineas de código
class MyTextPane extends JScrollPane implements DocumentListener {
    public myTextBox text;
    private JTextArea lines;
    private int linesCount;
    Ciudad city;
    MyLinePainter painter;
    private int tam;
    CodePanel p;

    public CodePanel getP() {
        return this.p;
    }

    public void setP(final CodePanel p) {
        this.p = p;
    }

    public MyTextPane(final Ciudad city) {
        this.tam = 14;
        this.city = city;
        this.text = new myTextBox(city, this);
        this.text.getDocument().addDocumentListener(this);
        this.text.addKeyListener(this.text);
        this.painter = new MyLinePainter(this.text, new Color(233, 239, 248, 255));
        ((AbstractDocument) this.text.getDocument()).setDocumentFilter(new DocumentTabFilter());
        this.lines = new JTextArea("  1.");
        this.linesCount = 1;
        this.lines.setBackground(new Color(233, 232, 226, 255));
        this.lines.setEditable(false);
        this.lines.setFont(new Font("Monospaced", 0, this.tam));
        this.lines.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 1, Color.LIGHT_GRAY));
        this.text.setFont(new Font("Monospaced", 0, this.tam));
        this.getViewport().add(this.text);
        this.setRowHeaderView(this.lines);
    }

    public void reset() {
        this.text.reset();
    }

    void setText(final String t) {
        this.text.setText(t);
        this.setLineNumbers();
    }

    String getText() {
        return this.text.getText();
    }

    String getText(final int offs, final int len) throws BadLocationException {
        return this.text.getText(offs, len);
    }

    int getCaretPosition() {
        return this.text.getCaretPosition();
    }

    public void setCaretPosition(final int position) {
        this.text.setCaretPosition(position);
    }

    @Override
    public void changedUpdate(final DocumentEvent de) {
    }

    @Override
    public void insertUpdate(final DocumentEvent de) {
        this.setLineNumbers();
        this.updateSyntaxHighlighting();
    }

    @Override
    public void removeUpdate(final DocumentEvent de) {
        this.setLineNumbers();
        this.updateSyntaxHighlighting();
    }

    public void setLineNumbers() {
        final Element root = this.text.getDocument().getDefaultRootElement();
        final int untilLine = root.getElementCount();
        final int chars = (untilLine > 99) ? ("" + untilLine).length() : 3;
        String t = "         1".substring(10 - chars) + ".";
        if (untilLine != this.linesCount) {
            this.linesCount = untilLine;
            for (int i = 2; i <= untilLine; ++i) {
                final String tmp = "          " + i;
                t = t + "\n" + tmp.substring(tmp.length() - chars) + ".";
            }
            this.lines.setText(t);
            this.lines.setCaretPosition(0);
        }
    }

    // Incrementar fuente o disminuir la fuente de los números de linea
    public void incLinesFontSize() {
        ++this.tam;
        this.lines.setFont(new Font("Monospaced", 0, this.tam));
    }

    public void decLinesFontSize() {
        if (this.tam > 2) {
            --this.tam;
            this.lines.setFont(new Font("Monospaced", 0, this.tam));
        }
    }

    public void updateSyntaxHighlighting() {
        final Element root = this.text.getDocument().getDefaultRootElement();
        final int paragraph = root.getElementIndex(this.text.getCaretPosition());
        final int from = root.getElement(paragraph).getStartOffset();
        final int length = root.getElement(paragraph).getEndOffset() - from;
        this.text.updateStyles(from, length);
    }

    public void resetSyntax() {
        this.text.updateStyles(0, this.text.getLengthText());
    }
}
