
package form;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class myTextBox extends JTextPane implements KeyListener {
    StyledDocument doc;
    StyledScan scanner;
    Ciudad city;
    MyTextPane mtp;
    MonitorActualizarVentana esperarRefresco;
    int tam;

    // Para el CTRL+Z y CTRL+Y
    UndoManager undoManager;
    StyledDocument undoManagerDocument;

    @Override
    public void setText(final String t) {
        super.setText(t);
        this.mtp.setLineNumbers();
        this.updateStyles(0, t.length());
    }

    public void incFontSize() {
        ++this.tam;
        this.setFont(new Font("Monospaced", 0, this.tam));
    }

    public void decFontSize() {
        if (this.tam > 2) {
            --this.tam;
            this.setFont(new Font("Monospaced", 0, this.tam));
        }
    }

    public int getLengthText() {
        return this.getText().length();
    }

    public myTextBox(final Ciudad city, final MyTextPane mtp) {
        // Para el CTRL+Z y CTRL+Y
        undoManager = new UndoManager();
        undoManagerDocument = this.getStyledDocument();
        undoManagerDocument.addUndoableEditListener(undoManager);

        this.esperarRefresco = MonitorActualizarVentana.getInstance();
        this.city = city;
        this.mtp = mtp;
        this.tam = 12;
    }

    public void reset() {
    }

    void appendAtCaretPosition(final String str) {
        final StyledDocument sdoc = this.getStyledDocument();
        try {
            sdoc.insertString(this.getCaretPosition(), str, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(myTextBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            try {
                String mySpaces = "";
                int c;
                String txt;
                for (c = this.getCaretPosition(), txt = this.getText(0, c), c -= 2; c >= 0
                        && txt.charAt(c) != '\n'; --c) {
                }
                for (++c; txt.charAt(c) == ' '; ++c) {
                    mySpaces += " ";
                }
                this.appendAtCaretPosition(mySpaces);
            } catch (BadLocationException ex) {
                System.out.println("BadLocationException del keyTyped");
                Logger.getLogger(MyTextPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        String texto = "";
        if (e.getKeyCode() == 118) {
            this.esperarRefresco.despertar();
        }
        if (e.isControlDown() && e.getKeyCode() == 90) { // CTRL + Z
            try {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            } catch (CannotUndoException ex) {
                ex.printStackTrace(); // Manejo de errores si no se puede deshacer
            }
        }
        if (e.isControlDown() && e.getKeyCode() == 89) { // CTRL + Y
            try {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            } catch (CannotRedoException ex) {
                ex.printStackTrace(); // Manejo de errores si no se puede rehacer
            }
        }
        if (e.isControlDown() && (e.getKeyCode() == 521 || e.getKeyCode() == 107)) { // CTRL + +
            this.incFontSize();
        }
        if (e.isControlDown() && (e.getKeyCode() == 45 || e.getKeyCode() == 109)) {// CTRL + -
            this.decFontSize();
        }
        if (e.isControlDown() && e.getKeyCode() == 86) { // CTRL + V
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable t = cb.getContents(this);
            try {
                final DataFlavor dataFlavorStringJava = new DataFlavor("text/plain;class=java.lang.String");
                if (t.isDataFlavorSupported(dataFlavorStringJava)) {
                    try {
                        String str = (String) t.getTransferData(dataFlavorStringJava);
                        final String fin = System.getProperty("line.separator");
                        str = str.replace(fin, "\n");
                        final String[] ss = str.split("\n");
                        System.out.println(ss.length);
                        System.out.println(ss[0].length());

                        // Transferable dataOriginal = cb.getContents(null); // Guardamos los datos
                        // copiados

                        if (ss.length == 1) {
                            System.out.println(" paste : una");
                            final StringSelection strSel = new StringSelection(str);
                            cb.setContents(strSel, null);
                            new DefaultEditorKit.PasteAction().actionPerformed(null);
                        } else {
                            System.out.println("paste : varias");
                            new DefaultEditorKit.PasteAction().actionPerformed(null);
                            String act = this.mtp.getText();
                            act = act.replace(fin, "\n");
                            this.setText(act);
                        }
                        // this.mtp.updateSyntaxHighlighting(); POR QUÉ ESTO HACE QUE NO FUNCIONEN LOS
                        // COLORES SI DEBERÍA HACER LO CONTRARIO
                        e.consume();
                        System.out.println("Consume");
                    } catch (UnsupportedFlavorException ex) {
                        System.out.println("error CadePanel L 107x");
                        Logger.getLogger(myTextBox.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex2) {
                        System.out.println("error CadePanel L 107x");
                        Logger.getLogger(myTextBox.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                } else {
                    System.out.println("No es String");
                    cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    t = cb.getContents(this);
                    if (t != null) {
                        try {
                            final String a = (String) t.getTransferData(DataFlavor.stringFlavor);
                            new DefaultEditorKit.PasteAction().actionPerformed(null);
                        } catch (UnsupportedFlavorException ex) {
                            System.out.println("NO1");
                        } catch (IOException ex2) {
                            System.out.println("NO2");
                        }
                    }
                    this.mtp.updateSyntaxHighlighting();
                    e.consume();
                }
            } catch (ClassNotFoundException ex3) {
                System.out.println("error CadePanel L 107x");
                Logger.getLogger(myTextBox.class.getName()).log(Level.SEVERE, null, ex3);
            }
        }
    }

    public void controlV(final String str) {
        JOptionPane.showMessageDialog(this, str);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Document sdoc = this.getDocument();
        String txt = "";
        final ArrayList<Integer> spaces = new ArrayList<Integer>();
        final int len = sdoc.getLength();
        try {
            txt = sdoc.getText(0, len);
        } catch (BadLocationException ble) {
            System.out.println("BadLocationException del paintComponent en CODEPANEL");
        }
        boolean contarEspacios = false;
        int esp = 0;
        for (int c = 0; c < txt.length(); ++c) {
            switch (txt.charAt(c)) {
                case '\n': {
                    if (contarEspacios) {
                        while (esp > 0) {
                            if (esp % 2 == 0) {
                                spaces.remove(spaces.size() - 1);
                            }
                            --esp;
                        }
                    }
                    contarEspacios = true;
                    esp = 0;
                    break;
                }
                case ' ': {
                    if (contarEspacios && ++esp % 2 == 0) {
                        spaces.add(c);
                        break;
                    }
                    break;
                }
                case '\r': {
                    System.out.println("BARRA R");
                    break;
                }
                default: {
                    contarEspacios = false;
                    break;
                }
            }
        }
        if (contarEspacios) {
            for (int c = esp; c > 0; --c) {
                if (c % 2 == 0) {
                    spaces.remove(spaces.size() - 1);
                }
            }
        }
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(219, 219, 219, 255));
        g2.setStroke(new BasicStroke(1.0f));
        for (final Integer space : spaces) {
            try {
                final Rectangle r = this.modelToView(space);
                g2.setColor(new Color(255, 255, 255, 255));
                g2.draw(new Line2D.Double(r.x + 1, r.y, r.x + 6, r.y));
                g2.setColor(new Color(200, 200, 200, 255));
                g2.draw(new Line2D.Double(r.x + 1, r.y, r.x + 1, r.y + r.getHeight()));
                g2.draw(new Line2D.Double(r.x + 1, r.y + r.getHeight(), r.x + 6, r.y + r.getHeight()));
            } catch (BadLocationException ex) {
                Logger.getLogger(MyTextPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateStyles(final int from, final int length) {
        if (length > 0) {
            this.doc = this.getStyledDocument();
            this.scanner = null;
            final int lastFrom = from;
            try {
                final String xx = this.doc.getText(from, length);
                this.scanner = new StyledScan(xx, this.city);
            } catch (BadLocationException ex) {
                System.out.println("BadLocationException Exception");
                Logger.getLogger(MyTextPane.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (this.scanner != null) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        myTextBox.this.parseStyles(lastFrom);
                    }
                });
            }
        }
    }

    private void parseStyles(final int lastFrom) {
        try {
            StyledToken tkn;
            while ((tkn = this.scanner.scan()).kind != StyledToken.EOF) {
                this.doc.setCharacterAttributes(lastFrom + tkn.getFrom(), tkn.getLength(), tkn.getAttributes(), true);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyTextPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
