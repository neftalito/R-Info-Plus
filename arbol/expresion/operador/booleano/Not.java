
package arbol.expresion.operador.booleano;

import javax.swing.JOptionPane;
import arbol.expresion.operador.Operador;

public class Not extends Operador {
    @Override
    public String resultado(final String Op1) throws Exception {
        if ("V".equals(Op1)) {
            return "F";
        } else if ("F".equals(Op1)) {
            return "V";
        } else {
            this.parseError("Se esperaba un valor booleano para la operación negación");
            throw new Exception("Se esperaba un valor booleano para la operación negación");
        }
    }

    public void parseError(final String msg) {
        JOptionPane.showMessageDialog(null, "Run Time Error: " + msg, "error", 0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Not();
    }
}
