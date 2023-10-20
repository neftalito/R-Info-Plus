
package arbol;

import javax.swing.JOptionPane;

public abstract class AST implements Cloneable {
    protected Programa programa;

    public Programa getPrograma() {
        return this.programa;
    }

    public void setPrograma(final Programa programa) {
        this.programa = programa;
    }

    public Object clone() throws CloneNotSupportedException {
        Object obj = null;
        try {
            System.out.println("Clone no definido");
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

    public void reportError(final String str) {
        JOptionPane.showMessageDialog(null, str, "ERROR", 0);
    }
}
