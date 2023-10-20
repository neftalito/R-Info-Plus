
package arbol.expresion.operador.booleano;

import javax.swing.JOptionPane;
import arbol.expresion.operador.Operador;

public class Not extends Operador {
    boolean a;
    boolean c;

    @Override
    public String resultado(final String Op1) throws Exception {
        if ("V".equals(Op1)) {
            this.c = true;
        } else {
            if (!"F".equals(Op1)) {
                this.parseError("Se esperaba un valor booleano para la operaci\u00f3n negaci\u00f3n");
                throw new Exception("Se esperaba un valor booleano para la operaci\u00f3n negaci\u00f3n");
            }
            this.c = false;
        }
        this.a = !this.c;
        return this.a ? "V" : "F";
    }

    public void parseError(final String msg) {
        JOptionPane.showMessageDialog(null, "Run Time Error: " + msg, "error", 0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Not();
    }
}
